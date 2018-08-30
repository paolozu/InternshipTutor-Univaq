/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Framework.result.FailureResult;
import Framework.result.TemplateManagerException;
import Framework.result.TemplateResult;
import Framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lorenzo
 */
public class HomePage extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
      private List getHeaderList(HttpServletRequest request) {
        List<Pair> headers = new ArrayList();
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headers.add(new Pair<String, String>(name, (String) request.getHeader(name)));
        }
        return headers;
    }
      
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
            Map data = new HashMap();
            data.put("headers", getHeaderList(request));
            data.put("page_title", "Homepage");
        
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("index.ftl.html", data, response);

        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
        
    }
    
    private void action_azienda(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {
      
            data.put("headers", getHeaderList(request));
            data.put("page_title", "Homepage - Studente");
        
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("homeStudente.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
        
    }
    
        private void action_admin(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {
      
            
            
            
            data.put("headers", getHeaderList(request));
            data.put("page_title", "Homepage - Studente");
        
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("homeAmministratore.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
        
    }
    
    private void action_studente(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {
      
            data.put("headers", getHeaderList(request));
            data.put("page_title", "Homepage - Studente");
        
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("homeStudente.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
        
    }
    
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession s = SecurityLayer.checkSession(request);
            if (s == null) {
                action_anonymous(request, response);
            } else {
                Map data = new HashMap();
                data.put("utente_username",s.getAttribute("username"));
                data.put("utente_tipo",s.getAttribute("tipo"));
                
                switch((String)s.getAttribute("tipo")){
                
                    case "AM":
                        action_admin(data, request, response);
                    break;
                    case "ST":
                        action_studente(data, request, response);
                    break;
                    case "AZ":
                        action_azienda(data, request, response);
                    
                        
                }
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
