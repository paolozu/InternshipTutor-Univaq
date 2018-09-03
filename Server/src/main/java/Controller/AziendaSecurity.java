/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Framework.data.DataLayerException;
import Framework.result.FailureResult;
import Framework.security.SecurityLayer;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Interface.AziendaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 *
 * @author lorenzo
 */
public abstract class AziendaSecurity extends InternshipBaseController {

    protected HttpSession s;
    
    @Override
    protected void autenticazione(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        s = SecurityLayer.checkSession(request);
        if (s == null || !(s.getAttribute("tipo").equals("AZ"))) {
            action_loginredirect(request, response);
        } else {
            try {
                AziendaDAO queryA = new AziendaDAOImpl();
                if (queryA.isConvenzionata((long) s.getAttribute("userid"))) {
                    request.setAttribute("id", s.getAttribute("userid"));
                    processRequest(request, response);
                } else {
                    response.sendRedirect("login");
                }
            } catch (DataLayerException ex) {
                (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
            }
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
        autenticazione(request, response);
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
        autenticazione(request, response);
    }
}
