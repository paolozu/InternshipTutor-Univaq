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
import Model.Bean.Azienda;
import Model.Bean.Resoconto;
import Model.Bean.Studente;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Impl.StudenteDAOImpl;
import Model.DAO.Impl.TirocinanteDAOImpl;
import Model.DAO.Interface.AziendaDAO;
import Model.DAO.Interface.StudenteDAO;
import Model.DAO.Interface.TirocinanteDAO;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;
import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lorenzo
 */
public class Download extends HttpServlet {

    private TirocinanteDAO tirocinioDAO;
    private AziendaDAO aziendaDAO;

    public Download() {
        aziendaDAO = new AziendaDAOImpl();
        tirocinioDAO = new TirocinanteDAOImpl();
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void download_resoconto(HttpServletRequest request, HttpServletResponse response, Studente studente, long idResoconto) throws IOException, TemplateManagerException, DataLayerException {

        Resoconto resoconto = new Resoconto(idResoconto);
        int fileLength;
        try {
            InputStream fileInputStream = tirocinioDAO.downloadResoconto(resoconto);

            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=convenzione.pdf");
            fileLength = fileInputStream.available();
            response.setContentLength(fileLength);

            OutputStream responseOutputStream = response.getOutputStream();

            int bytes;
            while ((bytes = fileInputStream.read()) != -1) {
                responseOutputStream.write(bytes);
            }
            fileInputStream.close();
        } catch (DataLayerException e) {
            request.setAttribute("message", "Data access exception: " + e.getMessage());
            action_error(request, response);
        }

    }


    private void download_convenzione(HttpServletRequest request, HttpServletResponse response, Azienda azienda)
            throws ServletException, IOException, DataLayerException, DocumentException {

        response.addHeader("Content-Disposition", "inline; filename=convenzione.pdf");
        response.setContentType("application/pdf");

        azienda = aziendaDAO.getAzienda(azienda);

        try {
            // We get a resource from our web app
            InputStream is = aziendaDAO.getModuloConvenzione();
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
            fields.setField("azienda", azienda.getRagioneSociale());
            fields.setField("indirizzo", azienda.getIndirizzoSede());
            fields.setField("piva", azienda.getPartitaIva());
            fields.setField("nomeRap", azienda.getCognomeRappresentante() + " " + azienda.getNomeRappresentante());

            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
            // We write the PDF bytes to the OutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
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

        HttpSession s = SecurityLayer.checkSession(request);

        try {
            if (s != null) {
                if (request.getParameter("resoconto") != null) {
                    Studente studente = new Studente((long) s.getAttribute("userid"));
                    long idResoconto = SecurityLayer.checkNumeric(request.getParameter("resoconto"));

                    download_resoconto(request, response, studente, idResoconto);

                } else if (request.getParameter("convenzione") != null) {
                    Azienda azienda = new Azienda((long) s.getAttribute("userid"));
                    download_convenzione(request, response, azienda);
                } else {
                    request.setAttribute("message", "Parametri insufficienti");
                    action_error(request, response);
                }
            } else {
                request.setAttribute("message", "Area riservata");
                action_error(request, response);
            }

        } catch (TemplateManagerException | DataLayerException ex) {
            request.setAttribute("exception", "ex");
            action_error(request, response);
        } catch (DocumentException ex) {
            request.setAttribute("exception", "ex");
            action_error(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
