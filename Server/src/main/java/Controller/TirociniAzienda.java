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
import Model.DAO.Impl.AnnuncioDAOImpl;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Interface.AnnuncioDAO;
import Model.DAO.Interface.AziendaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
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

    private void action_tirocinanti(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {

        data.put("titolo", "Annunci attivi");

        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("homeAzienda.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }

    }

    private void action_richieste(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {

        data.put("titolo", "Annunci attivi");

        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("homeAzienda.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
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
        response.setContentType("text/html;charset=UTF-8");

        if (request.getParameter("page") != null) {
            Map data = new HashMap();
            data.put("utente_username", s.getAttribute("username"));
            data.put("utente_tipo", s.getAttribute("tipo"));

            switch (request.getParameter("page")) {
                case "listaTirocinanti":
                    action_tirocinanti(data, request, response);
                    break;
                case "richieste":
                    action_richieste(data, request, response);
                    break;
                    
                default:
                    response.sendRedirect("homepage");
            }

        } else {
            response.sendRedirect("homepage");
        }
    }
}


