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
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lorenzo
 */
public class infoAzienda extends AmministratoreSecurity {

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
    

    
    private void action_registrata( HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map data = new HashMap();
        data.put("outline_tpl", "");//rimozione outline
            
        
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("approvazioneAzienda.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
        
    }
    
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
               
               if(request.getParameter("tipo") != null){
                
                 switch(request.getParameter("tipo")){
                     case "registrata":
                       action_registrata(request,response);
                      break;
                      
                      case "approvata":
                       //;
                      break;
                      
                      case "convenzionata":
                       //;
                      break;
                         
                     
                } 
              
                }else{
                request.setAttribute("message", "AZIENDA ID MANCANTE");
                action_error(request, response);
                }
                    
                        
                
            
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
