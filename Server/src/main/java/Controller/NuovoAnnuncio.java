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
import Model.Bean.Annuncio;
import Model.Bean.Azienda;
import Model.Bean.Docente;
import Model.Bean.Referente;
import Model.DAO.Impl.AnnuncioDAOImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lorenzo
 */
public class NuovoAnnuncio extends AziendaSecurity {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private boolean verificaCampi(HttpServletRequest request, HttpServletResponse response, Annuncio annuncio) throws IOException, DataLayerException {
         boolean campi_errati = false;
        
            if( annuncio.getDataAvvio().isAfter(annuncio.getDataTermine()) ){
                request.setAttribute("message", "Data inizio maggiore della data termine.");
                action_error(request, response);
                campi_errati = true;
            } 
            
            return campi_errati;  
    }
    
    private void action_registra(HttpServletRequest request, HttpServletResponse response) throws IOException, DataLayerException {
       
        Annuncio annuncio = new Annuncio();
        Azienda azienda = new Azienda((long) s.getAttribute("userid"));
        Referente referente = new Referente(
                request.getParameter("nomeRef"),
                request.getParameter("cognRef"), 
                request.getParameter("mailRef"),
                request.getParameter("telRef"));
        Docente docente = new Docente(
                request.getParameter("nomeDoc"),
                request.getParameter("cognDoc"),
                request.getParameter("mailDoc"));
   
        annuncio.setAzienda(azienda);
        annuncio.setTitolo(request.getParameter("titolo"));
        annuncio.setSettore(request.getParameter("settore"));
        annuncio.setModalita(request.getParameter("modalita"));
        annuncio.setSussidio(request.getParameter("sussidio"));
        annuncio.setDataAvvio(SecurityLayer.checkDate(request.getParameter("dataInizio")));
        annuncio.setDataTermine(SecurityLayer.checkDate(request.getParameter("dataTermine")));
        annuncio.setCorpo(request.getParameter("corpo"));
        annuncio.setReferente(referente);
        annuncio.setDocente(docente);
        
        boolean campi_non_validi =  verificaCampi(request, response, annuncio);
        
        if (!campi_non_validi) {
            try {
                new AnnuncioDAOImpl().saveAnnuncio(annuncio);
                
                Map data = new HashMap();
                data.put("outline_tpl", "");
                data.put("annuncio_done", "Annuncio pubblicato con successo!");
                TemplateResult res = new TemplateResult(getServletContext());
        try {
            res.activate("SingUpDone.ftl.html", data, response);
        } catch (TemplateManagerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        }
            } catch (DataLayerException e) {
            }
        }
              
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map data = new HashMap();
        data.put("utente_username", s.getAttribute("username"));
        data.put("utente_tipo", s.getAttribute("tipo"));
        
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        try {
            res.activate("pubblicaTirocinio.ftl.html", data, response);
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
        try {
            if (request.getParameter("pubblica") == null) {
                action_default(request, response);
            } else {
                try {
                    action_registra(request, response);
                } catch (DataLayerException ex) {
                    Logger.getLogger(NuovoAnnuncio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
       
        } catch (IOException e) {
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
