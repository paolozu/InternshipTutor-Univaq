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
import Model.Bean.Studente;
import Model.DAO.Impl.StudenteDAOImpl;
import Model.DAO.Interface.StudenteDAO;
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
 * @author Paolo
 */
public class SignUp extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_showStudente (HttpServletRequest request, HttpServletResponse response) {
        Map data = new HashMap();
        data.put("outline_tpl", "");//rimozione outline
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("signin-Studente.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
        
    }
    
    private void action_showAzienda (HttpServletRequest request, HttpServletResponse response) {
          Map data = new HashMap();
        data.put("outline_tpl", "");//rimozione outline
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("signin-Azienda.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
    }
    
    
    private void action_signinStudente (HttpServletRequest request, HttpServletResponse response) {
        try {
            Studente studente = new Studente(
              request.getParameter("usernameStudente"),
              request.getParameter("password"),
              request.getParameter("email"),
              request.getParameter("password"),
              request.getParameter("nomeStudente"),
              request.getParameter("cognomeStudente"),
              request.getParameter("password"),
              request.getParameter("CFStudemte"), 
              request.getParameter("telefono"),
              request.getParameter("indirizzoResidenza"),
              request.getParameter("corso"),
              request.getParameter("diploma"),
              request.getParameter("dottorato"),
              request.getParameter("capNascita"),
              request.getParameter("capResidenza"),
              request.getParameter("cittaResidenzaStudente"),
              request.getParameter("provinciaResidenza"),
              request.getParameter("provinciaNascita"),
              SecurityLayer.checkNumeric(request.getParameter("crediti")),
              SecurityLayer.checkDate(request.getParameter("dataNascita")),
              Boolean.valueOf(request.getParameter("handicap"))
            );
            
              StudenteDAO queryInsert = new StudenteDAOImpl();
              queryInsert.setRegistrazioneStudente(studente);
              
              action_showStudente(request,response);
              
        } catch (DataLayerException | IllegalArgumentException e) {
        }
    }
    
    
    private void action_signinAzienda (HttpServletRequest request, HttpServletResponse response) {
        
    }
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if(request.getParameter("signup") == null) {
              if ("ST".equals(request.getParameter("registra"))) {
                action_showStudente(request, response);
              } else {
                action_showAzienda(request, response);
              } 
            } else {
                if (request.getParameter("signup").equals("ST")) {
                   action_signinStudente(request, response);
                } else {
                   action_signinAzienda(request, response);
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
