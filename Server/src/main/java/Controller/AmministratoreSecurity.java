/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lorenzo
 */
public abstract class AmministratoreSecurity extends InternshipBaseController {
    
    @Override
    protected void autenticazione(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession s = SecurityLayer.checkSession(request);
            if (s == null || !(s.getAttribute("tipo").equals("AM"))) {
                action_loginredirect(request,response);
            }else {
                 processRequest(request,response);
            }
    }
 
}   
