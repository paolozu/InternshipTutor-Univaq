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
import Model.DAO.Impl.AnnuncioDAOImpl;
import Model.DAO.Interface.AnnuncioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class AnnunciAzienda extends AziendaSecurity {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_annunci(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        List<String> header = new ArrayList();
        header.add("");
        
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("tabella.ftl.html", data, response);
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
        if (request.getParameter("page") != null) {
            Map data = new HashMap();
            data.put("utente_username", s.getAttribute("username"));
            data.put("utente_tipo", s.getAttribute("tipo"));
            
            AnnuncioDAO queryAnn = new AnnuncioDAOImpl();
            try {
                switch (request.getParameter("page")) {
                    case "annunciAttivi":
                        data.put("titolo","Annunci attivi");
                        data.put("annunci", queryAnn.getAnnunci((long) s.getAttribute("userid"), 0, "ATTIVO"));
                        break;
                    case "annunciSospesi":
                        data.put("titolo","Annunci sospesi");
                        data.put("annunci", queryAnn.getAnnunci((long) s.getAttribute("userid"), 0, "SOSPESO"));
                        break;
                    case "annunciDisattivati":
                        data.put("titolo","Annunci disattivati");
                        data.put("annunci", queryAnn.getAnnunci((long) s.getAttribute("userid"), 0, "DISATTIVATO"));
                        break;
                    default:
                        response.sendRedirect("homepage");
                }
                action_annunci(data, request, response);
            } catch (DataLayerException ex) {
                (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
            }
        } else {
            response.sendRedirect("homepage");
        }
    }
}




