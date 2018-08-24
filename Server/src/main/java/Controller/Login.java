/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Framework.TemplateManagerException;
import Controller.Framework.TemplateResult;
import Model.Bean.Annuncio;
import Model.DAO.Impl.AnnuncioDAOImpl;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Interface.AnnuncioDAO;
import Model.DAO.Interface.AziendaDAO;
import Model.DB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lorenzo paolo
 */
public class Login extends HttpServlet {

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

        //Query annuncio
//      AnnuncioDAO query = new AnnuncioDAOImpl();
//      Annuncio annuncio = query.getAnnuncioById(1);
//      System.out.println(annuncio.toString());
//      System.out.println(annuncio.getReferente().getCognome());
        //TEST SET ANNUNCIO
//        query.setAnnuncio("Titolo1", "Corpo1", LocalDate.of(2014,9,9), LocalDate.of(2015,9,9), "mod1", "sus1", "set1", 1, 1);
        AziendaDAO queryA = new AziendaDAOImpl();
//        System.out.println(queryA.getRichieste(1));
//        System.out.println(queryA.getAziende());
//        System.out.println(queryA.getTirocinanti(1));
//        System.out.println(queryA.getConvenzione(1));
//        System.out.println(queryA.getApprovazione(1).toString());
//        System.out.println(queryA.getApprovazione(1).getConvenzione().getDataConvezione());
//        

        //TEMPLATE
        try {
            Map data = new HashMap();
            data.put("outline_tpl", "");//rimozione outline
            TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
            res.activate("login.html", data, response);
        } catch (TemplateManagerException ex) {
            throw new ServletException(ex);
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
