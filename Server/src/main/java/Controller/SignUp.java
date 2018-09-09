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
import Model.Bean.Utente;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Impl.StudenteDAOImpl;
import Model.DAO.Impl.UtenteDAOImpl;
import Model.DAO.Interface.StudenteDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
    
    
    private boolean verificaCredenziali(HttpServletRequest request, HttpServletResponse response, Utente utente) throws DataLayerException {
        
        boolean credenziali_in_uso = false;
        
            if( new UtenteDAOImpl().getUsernameEsistente(utente.getUsername()) ){
                credenziali_in_uso = true;
                request.setAttribute("username_in_uso", true);
            } else {
                if ( new UtenteDAOImpl().getEmailEsistente(utente.getEmail()) ) {
                    credenziali_in_uso = false;
                    request.setAttribute("email_in_uso", true);      
                }
            }
            
            return credenziali_in_uso;
        }
 
    
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
   
    
    
    private void action_signupStudente (HttpServletRequest request, HttpServletResponse response) throws DataLayerException {
        try {
            
            Studente studente = new Studente();

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
            studente.setDataNascita(SecurityLayer.checkDate(request.getParameter("dataNascita")));
            studente.setHandicap(Boolean.valueOf(request.getParameter("handicap")));
            
            boolean credenziali_in_uso = verificaCredenziali(request, response, studente);
            
            if (!credenziali_in_uso) {
                new StudenteDAOImpl().setRegistrazioneStudente(studente);
            } else {
                // redirect
            }
         
              
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
            azienda.setCap(request.getParameter("cap"));
            azienda.setPartitaIva(request.getParameter("CF_PIva"));
            azienda.setForoCompetente(request.getParameter("foro"));
            azienda.setDataIscrione(SecurityLayer.checkDate(request.getParameter("inizioConvenzione")));
            azienda.setDataTermine(SecurityLayer.checkDate(request.getParameter("fineConvenzione")));
            azienda.setNomeRappresentante((request.getParameter("nomeRappresentante")));
            azienda.setCognomeRappresentante(request.getParameter("cognomeRappresentante"));
            azienda.setNomeResponsabile(request.getParameter("nomeResponsabile"));
            azienda.setCognomeResponsabile(request.getParameter("cognomeResponsabile"));
            azienda.setEmailResponsabile(request.getParameter("emailResponsabile"));
            azienda.setTelResponsabile(request.getParameter("telefonoResponsabile"));
            azienda.setUsername(request.getParameter("usernameAzienda"));
            azienda.setEmail(request.getParameter("emailAccessoAzienda"));
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
