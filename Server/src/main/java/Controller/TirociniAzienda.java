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
import Model.Bean.Resoconto;
import Model.Bean.Studente;
import Model.Bean.Tirocinio;
import Model.DAO.Impl.AnnuncioDAOImpl;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Interface.AnnuncioDAO;
import Model.DAO.Interface.AziendaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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
public class TirociniAzienda extends AziendaSecurity {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_gestioneTirocinio(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        switch (request.getParameter("action")) {
            case "visualizza":
                try {
                    data.put("titolo", "Resoconto tirocinio");
                    data.put("idTirocinante", request.getParameter("idT"));
                    data.put("idAnnuncio", request.getParameter("idA"));

                    TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
                    res.activate("resocontoTirocioni.ftl.html", data, response);

                } catch (TemplateManagerException ex) {
                    (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
                }
                break;
            case "concludi":
                if (request.getParameter("attivita") != null && request.getParameter("risultato") != null && request.getParameter("ore") != null) {

                    
                    
                    AziendaDAO queryA = new AziendaDAOImpl();

                    Studente stu = new Studente(SecurityLayer.checkNumeric(request.getParameter("idT")));
                    Resoconto res = new Resoconto(SecurityLayer.checkNumeric(request.getParameter("ore")), request.getParameter("attivita"), request.getParameter("risultato"));
                    Annuncio an = new Annuncio(SecurityLayer.checkNumeric(request.getParameter("idA")));

                    
                    try {
                    queryA.setConcludiTirocinio(new Tirocinio(stu, an, res));
                    } catch (DataLayerException ex) {
                        (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
                    }
                }

                //Notifica aggiornamento tirocinio
                data.put("alert","Tirocinio concluso");
                action_listaTirocinanti(data, request, response);
                
                break;
            case "elimina":
                action_listaTirocinanti(data, request, response);
                break;
            default:
                action_listaTirocinanti(data, request, response);
        }
    }

    private void action_listaTirocinanti(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            AziendaDAO queryA = new AziendaDAOImpl();

            data.put("tirocini", queryA.getTirocini((long) s.getAttribute("userid")));
            data.put("titolo", "Lista tirocinanti");

            TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
            res.activate("listaTirocinanti.ftl.html", data, response);

        } catch (TemplateManagerException | DataLayerException ex) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);

        }
    }

//    private void action_richieste(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        try {
//            AziendaDAO queryA = new AziendaDAOImpl();
//
//            data.put("studenti", queryA.getRichieste((long) s.getAttribute("userid")));
//            data.put("titolo", "Lista richieste");
//
//            TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
//            res.activate("listaRichieste.ftl.html", data, response);
//
//        } catch (TemplateManagerException | DataLayerException ex) {
//            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
//        }
//
//    }
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
        response.setContentType("text/html;charset=UTF-8");

        Map data = new HashMap();
        data.put("utente_username", s.getAttribute("username"));
        data.put("utente_tipo", s.getAttribute("tipo"));

        if (request.getParameter("action") != null) {

            SecurityLayer.checkString(request.getParameter("idT"));
            SecurityLayer.checkString(request.getParameter("idA"));

            action_gestioneTirocinio(data, request, response);
        } else {
            // visualizza lista tirocinanti
            action_listaTirocinanti(data, request, response);
        }
    }
}
