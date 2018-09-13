/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Framework.data.DataLayerException;
import Framework.pdf.GeneratePDF;
import Framework.result.FailureResult;
import Framework.result.TemplateManagerException;
import Framework.result.TemplateResult;
import Framework.security.SecurityLayer;
import Framework.security.SecurityLayerException;
import Model.Bean.Annuncio;
import Model.Bean.Resoconto;
import Model.Bean.Richiesta;
import Model.Bean.Studente;
import Model.Bean.Tirocinio;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Impl.RichiestaDAOImpl;
import Model.DAO.Impl.StudenteDAOImpl;
import Model.DAO.Interface.AziendaDAO;
import Model.DAO.Interface.RichiestaDAO;
import Model.DAO.Interface.StudenteDAO;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
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
                StudenteDAO stuDAO = new StudenteDAOImpl();
                
                Studente stu = stuDAO.getStudente(idStudente);
                Resoconto resoconto = new Resoconto(ore, attivita, risultato);
                Annuncio an = new Annuncio(idAnnuncio);

                //nuovo resoconto con id aggiornato
                resoconto = queryA.setConcludiTirocinio(new Tirocinio(stu, an, resoconto));

                
                int result = GeneratePDF.resocontoTirocinio(resoconto,stu);
                
                if(result==1){
                //Notifica aggiornamento tirocinio
                data.put("alert", "Tirocinio concluso");
                action_listaTirocinanti(data, request, response);
                }else{
                
                //TODO rimuovere ultimo inserimento resoconto
                
                request.setAttribute("message", "Errore inserimento pdf convenzione");
                action_error(request, response);
                }
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
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        Map data = new HashMap();
        data.put("utente_username", s.getAttribute("username"));
        data.put("utente_tipo", s.getAttribute("tipo"));

        
        try {
            if (request.getParameter("action") != null) {

                long idStudente = SecurityLayer.issetInt(request.getParameter("idT"));
                long idAnnuncio = SecurityLayer.issetInt(request.getParameter("idA"));

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
