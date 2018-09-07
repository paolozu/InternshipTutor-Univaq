/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Framework.result.FailureResult;
import Framework.security.SecurityLayer;
import Model.Bean.Azienda;
import Model.Bean.Convenzione;
import Model.DAO.Impl.AmministratoreDAOImpl;
import Model.DAO.Interface.AmministratoreDAO;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author lorenzo
 */
public class Upload extends HttpServlet {

    private AmministratoreDAO ammDAO;

    public Upload() {
        super();
        ammDAO = new AmministratoreDAOImpl();
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_upload_convenzione(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, NamingException, NoSuchAlgorithmException, Exception {
        //Convenzione
        Part file = request.getPart("filetoupload");
        String nome = file.getSubmittedFileName();
        long peso = file.getSize();
        String estensione = file.getContentType();

        Convenzione c = new Convenzione(nome, file, estensione, peso);

        //Azienda
        long idAzienda = SecurityLayer.checkNumeric(request.getParameter("refA"));
        Azienda a = new Azienda(idAzienda);

        //Update convenzione
        ammDAO.setConvenzione(c, a);

        response.sendRedirect("homepage?message=Azienda approvata");

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if (request.getPart("filetoupload") != null) {
                Part file = request.getPart("filetoupload");
                if (file.getSize() > 0) {
                    if (request.getPart("refA") != null) {
                        action_upload_convenzione(request, response);
                    } else {
                        request.setAttribute("message", "Invalid reference company");
                        action_error(request, response);
                    }
                } else {
                request.setAttribute("message", "Nessun file selezionato");
                action_error(request, response);
                }

            } else {
                request.setAttribute("exception", new Exception("Nessun file selezionato"));
                action_error(request, response);
            }
        } catch (NamingException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (SQLException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (NoSuchAlgorithmException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (Exception ex) {
            request.setAttribute("exception", ex);
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
