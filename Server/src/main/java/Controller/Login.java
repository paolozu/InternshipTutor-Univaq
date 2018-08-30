/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Framework.result.TemplateManagerException;
import Framework.result.TemplateResult;
import Framework.data.DataLayerException;
import Framework.result.FailureResult;
import Framework.security.SecurityLayer;
import Model.Bean.Azienda;
import Model.Bean.Studente;
import Model.Bean.Utente;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Impl.StudenteDAOImpl;
import Model.DAO.Impl.UtenteDAOImpl;
import Model.DAO.Interface.AziendaDAO;
import Model.DAO.Interface.StudenteDAO;
import Model.DAO.Interface.UtenteDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lorenzo paolo
 */
public class Login extends HttpServlet {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map data = new HashMap();
        data.put("outline_tpl", "");//rimozione outline
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("login.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try { //... VALIDAZIONE IDENTITA'...

            UtenteDAO queryU = new UtenteDAOImpl();
            Utente utente = queryU.getCredenziali(username, password);

            if (utente != null) {
                SecurityLayer.createSession(request, utente.getUsername(), utente.getId(), utente.getTipo());
                
                if (request.getParameter("referrer") != null) {
                    response.sendRedirect(request.getParameter("referrer"));
                } else {
                    response.sendRedirect("homepage");
                }
            } else {
                //notifica errore credenziali
                request.setAttribute("message", "Errore credenziali");
                action_error(request, response);
            }

        } catch (DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if (request.getParameter("login") != null) {
                action_login(request, response);
            } else {
                action_default(request, response);
            }
        } catch (IOException ex) {
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
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>

}
