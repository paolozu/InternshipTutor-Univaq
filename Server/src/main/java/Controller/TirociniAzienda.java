/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Framework.data.DataLayerException;
import Framework.result.FailureResult;
import Framework.result.TemplateManagerException;
import Framework.result.TemplateResult;
import Framework.security.SecurityLayer;
import Framework.security.SecurityLayerException;
import Model.Bean.Annuncio;
import Model.Bean.Azienda;
import Model.Bean.Convenzione;
import Model.Bean.Resoconto;
import Model.Bean.Studente;
import Model.Bean.Tirocinio;
import Model.DAO.Impl.AnnuncioDAOImpl;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Impl.TirocinanteDAOImpl;
import Model.DAO.Interface.AnnuncioDAO;
import Model.DAO.Interface.AziendaDAO;
import Model.DAO.Interface.TirocinanteDAO;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lorenzo
 */
public class TirociniAzienda extends AziendaSecurity {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private int action_save_PDF(Resoconto resoconto) throws IOException, DataLayerException, DocumentException {

        TirocinanteDAO tirocinioDAO = new TirocinanteDAOImpl();

        // Carico modello base resoconto
        InputStream is = tirocinioDAO.downloadResoconto(new Resoconto(0));
        // We create a reader with the InputStream
        PdfReader reader = new PdfReader(is, null);
        // We create an OutputStream for the new PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Now we create the PDF
        PdfStamper stamper = new PdfStamper(reader, baos);
        // We alter the fields of the existing PDF

        AcroFields fields = stamper.getAcroFields();
        fields.setGenerateAppearances(true);

        Set<String> parameters = fields.getFields().keySet();

        // Fill field
//      fields.setField("azienda", azienda.getRagioneSociale());
        //Flatt form
        stamper.setFormFlattening(true);
        stamper.close();
        reader.close();

        is = new ByteArrayInputStream(baos.toByteArray());
        is.close();

        resoconto.setNome("resoconto");
        resoconto.setPeso(is.available());
        resoconto.setEstensione("application/pdf");
        resoconto.setFile(is);

        return tirocinioDAO.uploadResoconto(resoconto);
    }

    private void action_gestioneTirocinio(Map data, long idStudente, long idAnnuncio, HttpServletRequest request, HttpServletResponse response) throws IOException, DataLayerException, TemplateManagerException, DocumentException, SecurityLayerException {

        switch (request.getParameter("action")) {
            
            case "visualizza":
                TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
                
                data.put("titolo", "Resoconto tirocinio");
                data.put("idTirocinante", idStudente);
                data.put("idAnnuncio", idAnnuncio);

                res.activate("resocontoTirocinio.ftl.html", data, response);

                break;

            case "concludi":
                //Check input
                String attivita = SecurityLayer.issetString(request.getParameter("attivita"));
                String risultato = SecurityLayer.issetString(request.getParameter("risultato"));
                int ore = SecurityLayer.issetInt(request.getParameter("ore"));

                AziendaDAO queryA = new AziendaDAOImpl();

                Studente stu = new Studente(idStudente);
                Resoconto resoconto = new Resoconto(ore, attivita, risultato);
                Annuncio an = new Annuncio(idAnnuncio);

                //nuovo resoconto con id aggiornato
                resoconto = queryA.setConcludiTirocinio(new Tirocinio(stu, an, resoconto));

                action_save_PDF(resoconto);

                break;
            case "elimina":
                Studente studente = new Studente(SecurityLayer.checkNumeric(request.getParameter("idT")));
                Annuncio annuncio = new Annuncio(SecurityLayer.checkNumeric(request.getParameter("idA")));

                AziendaDAO queryB = new AziendaDAOImpl();

                try {
                    queryB.removeTirocinio(new Tirocinio(studente, annuncio));
                } catch (DataLayerException ex) {
                    (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
                }

                //Notifica aggiornamento tirocinio
                data.put("alert", "Tirocinio rimosso");
                action_listaTirocinanti(data, request, response);
                break;

            default:
                action_listaTirocinanti(data, request, response);
        }
    }

    private void action_listaTirocinanti(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException, DataLayerException, TemplateManagerException {

        AziendaDAO queryA = new AziendaDAOImpl();
        List<Tirocinio> tirocini = queryA.getTirocini((long) s.getAttribute("userid"));

        data.put("tirocini", tirocini);
        data.put("titolo", "Lista tirocinanti");

        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        res.activate("listaTirocinanti.ftl.html", data, response);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        Map data = new HashMap();
        data.put("utente_username", s.getAttribute("username"));
        data.put("utente_tipo", s.getAttribute("tipo"));

        
        try {

            if (request.getParameter("action") != null) {

                long idStudente = SecurityLayer.issetLong(request.getParameter("idT"));
                long idAnnuncio = SecurityLayer.issetLong(request.getParameter("idA"));

                //Gestione del tirocinio
                action_gestioneTirocinio(data, idStudente, idAnnuncio, request, response);

            } else {
                // Lista tirocinanti
                action_listaTirocinanti(data, request, response);
            }

        } catch (TemplateManagerException | DocumentException | DataLayerException | SecurityLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } 
    }
}
