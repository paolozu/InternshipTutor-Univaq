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
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Interface.AmministratoreDAO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author lorenzo
 */
public class Upload extends AmministratoreSecurity {

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
        InputStream filePDF = file.getInputStream();

        Convenzione convenzione = new Convenzione(nome, filePDF, estensione, peso);

        //Azienda
        long idAzienda = SecurityLayer.checkNumeric(request.getParameter("refA"));
        Azienda azienda = new Azienda(idAzienda);

        //Update convenzione
        ammDAO.setConvenzione(convenzione, azienda);
        s.setAttribute("message", "Convenzione inserita correttametne");
        response.sendRedirect("homepage");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            Part file = request.getPart("filetoupload");

            if (file != null) {
                if (request.getPart("refA") != null) {
                    if (file.getSize() > 0) {
                        if (file.getContentType().equals("application/pdf")) {

                            action_upload_convenzione(request, response);

                        } else {
                            request.setAttribute("message", "Formato file non valido");
                            action_error(request, response);
                        }
                    } else {
                        request.setAttribute("message", "File vuoto");
                        action_error(request, response);
                    }

                } else {
                    request.setAttribute("message", "Invalid reference company");
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

}
