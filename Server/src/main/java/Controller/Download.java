/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Framework.data.DataLayerException;
import Framework.result.FailureResult;
import Framework.security.SecurityLayer;
import Model.Bean.Azienda;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Interface.AziendaDAO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

/**
 *
 * @author lorenzo
 */
public class Download extends HttpServlet {
    
    private AziendaDAO aziendaDAO;
    public Download(){
        aziendaDAO= new AziendaDAOImpl();
    }

    
        private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_download_convenzione(HttpServletRequest request, HttpServletResponse response, long idAzienda) throws IOException{
    		
        Azienda azienda = new Azienda(idAzienda);
        int fileLength;
        try {			
			InputStream fileInputStream = aziendaDAO.getConvenzione(azienda);
                       
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
		}
		catch(DataLayerException e) {
			request.setAttribute("message", "Data access exception: " + e.getMessage());
            action_error(request, response);
		}
    }
    
    private void action_download_generic(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream fileInputStream= null;
        try {
             fileInputStream = aziendaDAO.getConvenzione(new Azienda(101));
        } catch (DataLayerException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PDDocument myDocument = PDDocument.load(fileInputStream);
            
        myDocument=ReplaceText._ReplaceText(myDocument,"Residente in","Non ci credo mai");
           
        
        myDocument.save(output);
        myDocument.close();

        response.setContentType("application/pdf");

        //response.addHeader("Content-Type", "application/force-download"); //--< Use this if you want the file to download rather than display
        response.addHeader("Content-Disposition", "inline; filename=\"yourFile.pdf\"");
        response.getOutputStream().write(output.toByteArray());
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
        
        if(request.getParameter("download")!=null){
            if(request.getParameter("refA")!=null){
                long idAzienda = SecurityLayer.checkNumeric(request.getParameter("refA"));
                action_download_convenzione(request, response,idAzienda);
            }else{
            request.setAttribute("message", "Riferimento azienda non valido");
            action_error(request, response);
            }
        }else{
        action_download_generic(request, response);
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
