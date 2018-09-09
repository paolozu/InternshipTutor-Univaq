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
import Model.Bean.Azienda;
import Model.Bean.Studente;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Impl.StudenteDAOImpl;
import Model.DAO.Interface.StudenteDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
    
    private void action_default (HttpServletRequest request, HttpServletResponse response) {
        if ("ST".equals(request.getParameter("registra"))) {
            Map data = new HashMap();
            data.put("outline_tpl", "");//rimozione outline
            TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
            try {
                res.activate("signup-Studente.ftl.html", data, response);
            } catch (TemplateManagerException ex) {
                (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
            }
        } else {
            Map data = new HashMap();
            data.put("outline_tpl", "");//rimozione outline
            TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
            try {
                res.activate("signup-Azienda.ftl.html", data, response);
            } catch (TemplateManagerException ex) {
                (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
            }
        }  
    }
    
    
  /*  private void action_showStudente (HttpServletRequest request, HttpServletResponse response) {
        if ("ST".equals(request.getParameter("signup"))) {
            action_signupStudente(request, response);
        } else {
            Map data = new HashMap();
            data.put("outline_tpl", "");//rimozione outline
            TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
            try {
                res.activate("signin-Studente.ftl.html", data, response);
            } catch (TemplateManagerException ex) {
                (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
            }
        }
       
    } */
    
 /*   private void action_showAzienda (HttpServletRequest request, HttpServletResponse response) {
        Map data = new HashMap();
        data.put("outline_tpl", "");//rimozione outline
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("signin-Azienda.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
    } */
    
    
    private void action_signupStudente (HttpServletRequest request, HttpServletResponse response) throws DataLayerException {
        try {
            Studente studente = new Studente();
            System.out.println("dentro");
            studente.setTipo("ST");
            studente.setNome(request.getParameter("nomeStudente"));
            studente.setEmail(request.getParameter("emailDatiDiAccesso"));
            studente.setUsername(request.getParameter("usernameStudente"));
            studente.setPassword(request.getParameter("password"));
            studente.setNome(request.getParameter("nomeStudente"));
            studente.setCognome(request.getParameter("cognomeStudente"));
            studente.setCodFiscale(request.getParameter("CFStudemte"));
            studente.setTelefono(request.getParameter("telefono")); 
            studente.setIndirizzoResidenza(request.getParameter("indirizzoResidenza"));
            studente.setLaurea(request.getParameter("laureato"));
            studente.setCorsoLaurea(request.getParameter("corso"));
            studente.setDiploma(request.getParameter("diploma"));
            studente.setDottorato(request.getParameter("dottorato"));
            studente.setCapNascita(request.getParameter("capNascita"));
            studente.setCittaNascita(request.getParameter("luogoNascita"));
            studente.setCapResidenza(request.getParameter("capResidenza"));
            studente.setCittaResidenza(request.getParameter("cittaResidenzaStudente"));
            studente.setProvinciaResidenza(request.getParameter("provinciaResidenza"));
            studente.setProvinciaNascita(request.getParameter("provinciaNascita"));
            studente.setCrediti(SecurityLayer.checkNumeric(request.getParameter("crediti")));
            studente.setDataNascita(SecurityLayer.checkDate(request.getParameter("dataNascita")));
            studente.setHandicap(Boolean.valueOf(request.getParameter("handicap")));
            
            
            System.out.println(studente);
            
            new StudenteDAOImpl().setRegistrazioneStudente(studente);
           // action_showStudente(request,response);
              
        } catch (IllegalArgumentException e) {
        }
    }
    
    
    private void action_signupAzienda (HttpServletRequest request, HttpServletResponse response) throws DataLayerException {
        try {
            Azienda azienda = new Azienda();
            
            azienda.setTipo("AZ");
            azienda.setRagioneSociale(request.getParameter("ragioneSociale"));
            azienda.setIndirizzoSede(request.getParameter("indirizzo"));
            azienda.setCitta(request.getParameter("citta"));
            azienda.setProvincia(request.getParameter("provincia"));
            azienda.setCap(request.getParameter("CAP"));
            azienda.setPartitaIva(request.getParameter("CF_PIva"));
            azienda.setForoCompetente(request.getParameter("foro")); 
            azienda.setNomeRappresentante(request.getParameter(request.getParameter("nomeRappresentante")));
            azienda.setCognomeRappresentante(request.getParameter("cognomeRappresentante"));
            azienda.setEmail(request.getParameter("emailRappresentante"));
            azienda.setNomeResponsabile(request.getParameter("nomeResponsabile"));
            azienda.setCognomeResponsabile(request.getParameter("cognomeResponsabile"));
            azienda.setEmailResponsabile(request.getParameter("emailResponsabile"));
            azienda.setTelResponsabile(request.getParameter("telefonoResponsabile"));
            azienda.setUsername(request.getParameter("usernameAzienda"));
            azienda.setEmail(request.getParameter("email"));
            azienda.setPassword(request.getParameter("password"));
            

            new AziendaDAOImpl().setRegistrazioneAzienda(azienda);
 
              
        } catch (IllegalArgumentException e) {
        }
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, DataLayerException {
        try {
            if (request.getParameter("signup") == null) {
                action_default(request, response);
            } else {
                if(request.getParameter("signup").equals("Iscriviti come Studente")){
                    action_signupStudente(request, response);
                } else {
                    action_signupAzienda(request, response);
                }
            }
        } catch (DataLayerException ex) {
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
        try {
            processRequest(request, response);
        } catch (DataLayerException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (DataLayerException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Registrazione Tirocinio";
    }// </editor-fold>

}
