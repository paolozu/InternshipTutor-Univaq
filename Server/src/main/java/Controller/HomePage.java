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
import Model.Bean.Tirocinio;
import Model.DAO.Impl.AmministratoreDAOImpl;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Impl.TirocinanteDAOImpl;
import Model.DAO.Interface.AmministratoreDAO;
import Model.DAO.Interface.AziendaDAO;
import Model.DAO.Interface.TirocinanteDAO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map data = new HashMap();
        data.put("activeHome", "active");
        data.put("page_title", "Homepage");

        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("index.ftl.html", data, response);

        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }

    }
    

    

    private void action_azienda(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {

        AziendaDAO queryA = new AziendaDAOImpl();
        String stato;
        try {
           stato = queryA.getStato((long) request.getAttribute("id"));
           
           if(stato==null){
               //azienda rimossa dal db
               response.sendRedirect("Logout");
           }
           
           data.put("stato", stato);
           
                
        } catch (DataLayerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
       }
        
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("homeAzienda.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }

    }

    private void action_admin(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession s = request.getSession();
        int homepage = 0;

        try {
            AmministratoreDAO queryAmm = new AmministratoreDAOImpl();

            data.put("aziendeRegistrate", queryAmm.getListaAziende("REGISTRATA", homepage));
            data.put("dim", queryAmm.getListaAziende("REGISTRATA", homepage).size());
            data.put("aziendeApprovate", queryAmm.getListaAziende("APPROVATA", homepage));
            data.put("aziendeConvenzionate", queryAmm.getListaAziende("CONVENZIONATA", homepage));

        } catch (DataLayerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }

        if(s.getAttribute("message")!=null){
            data.put("alert", s.getAttribute("message"));
            s.removeAttribute("message");
        }
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("homeAmministratore.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }

    }

    private void action_studente(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            TirocinanteDAO queryTirocini = new TirocinanteDAOImpl();
            List<Tirocinio> tirocini = queryTirocini.getTirocini((long) request.getAttribute("id"));

                
                //Tirocinante
                data.put("tirocini", tirocini);
                
                TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
                try {
                    res.activate("homeTirocinante.ftl.html", data, response);
                } catch (TemplateManagerException ex) {
                    (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
                
            }

        } catch (DataLayerException ex) {
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
                data.put("activeHome", "active");
                request.setAttribute("id", s.getAttribute("userid"));

                data.put("utente_username", s.getAttribute("username"));
                data.put("utente_tipo", s.getAttribute("tipo"));

                switch ((String) s.getAttribute("tipo")) {

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
