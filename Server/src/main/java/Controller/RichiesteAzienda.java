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
import Framework.security.SecurityLayerException;
import Model.Bean.Annuncio;
import Model.Bean.Azienda;
import Model.Bean.Resoconto;
import Model.Bean.Richiesta;
import Model.Bean.Studente;
import Model.Bean.Tirocinio;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Impl.StudenteDAOImpl;
import Model.DAO.Interface.AziendaDAO;
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
 * @author lorenzo
 */
public class RichiesteAzienda extends AziendaSecurity {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_gestioneRichieste(Map data,long idStudente, long idAnnuncio, HttpServletRequest request, HttpServletResponse response) throws IOException, SecurityLayerException {
        switch (request.getParameter("action")) {

            case "visualizza":
                try {

                    AziendaDAO queryA = new AziendaDAOImpl();

                    Richiesta richiestaStudente = new Richiesta(new Annuncio(idAnnuncio), new Studente(idStudente));

                    richiestaStudente = queryA.getRichiestaStudente(richiestaStudente);

                    TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
                    data.put("richiesta", richiestaStudente);
                    data.put("titolo", "Richiesta studente");
                    data.put("outline_tpl", "");//rimozione outline

                    res.activate("richiestaStudente.ftl.html", data, response);

                } catch (TemplateManagerException | DataLayerException ex) {
                    (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
                }
                break;

            case "accetta":

                LocalDate dataInizio = SecurityLayer.issetDate("DATA INIZIO",request.getParameter("dataInizio"));
                LocalDate dataFine = SecurityLayer.issetDate("DATA FINE",request.getParameter("dataFine"));
                

                Tirocinio nuovoTirocinio = new Tirocinio(new Studente(idStudente), new Annuncio(idAnnuncio), dataInizio, dataFine);

                AziendaDAO queryA = new AziendaDAOImpl();

                int result = 0;
                try {
                    result = queryA.setNuovoTirocinio(nuovoTirocinio);
                } catch (DataLayerException ex) {
                    (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
                }

                if (result != 0) {
                    Richiesta richiestaStudente = new Richiesta(new Annuncio(idAnnuncio), new Studente(idStudente));

                    try {
                        queryA.removeRichiesta(richiestaStudente);
                    } catch (DataLayerException ex) {
                        (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
                    }
                    //Notifica aggiornamento tirocinio
                    data.put("alert", "Richiesta approvata");
                }
                action_listaRichieste(data, request, response);
                break;

            case "rifiuta":
                AziendaDAO queryB = new AziendaDAOImpl();

                Richiesta richiestaStudenteR = new Richiesta(new Annuncio(idAnnuncio), new Studente(idAnnuncio));

                try {
                    queryB.removeRichiesta(richiestaStudenteR);
                } catch (DataLayerException ex) {
                    (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
                }

                //Notifica aggiornamento tirocinio
                data.put("alert", "Richiesta rifiutata");
                action_listaRichieste(data, request, response);
                break;
            default:
                action_listaRichieste(data, request, response);
        }
    }

    private void action_listaRichieste(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            AziendaDAO queryA = new AziendaDAOImpl();

            data.put("richieste", queryA.getRichieste((long) s.getAttribute("userid")));
            data.put("titolo", "Lista richieste");

            TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
            res.activate("listaRichieste.ftl.html", data, response);

        } catch (TemplateManagerException | DataLayerException ex) {
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
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map data = new HashMap();
        data.put("utente_username", s.getAttribute("username"));
        data.put("utente_tipo", s.getAttribute("tipo"));

        if (request.getParameter("action") != null) {
try {
            long idStudente = SecurityLayer.issetInt(request.getParameter("idStudente"));
            long idAnnuncio = SecurityLayer.issetInt(request.getParameter("idAnnuncio"));
                

            
                action_gestioneRichieste(data,idStudente,idAnnuncio, request, response);
            } catch (SecurityLayerException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }
        } else {
            // visualizza lista tirocinanti
            action_listaRichieste(data, request, response);
        }
    }
}
