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
import Model.Bean.Richiesta;
import Model.Bean.Studente;
import Model.Bean.Tirocinio;
import Model.DAO.Impl.AnnuncioDAOImpl;
import Model.DAO.Impl.RichiestaDAOImpl;
import Model.DAO.Impl.TirocinanteDAOImpl;
import Model.DAO.Interface.AnnuncioDAO;
import Model.DAO.Interface.RichiestaDAO;
import Model.DAO.Interface.TirocinanteDAO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author lorenzo
 */
public class HomeTirocini extends InternshipBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException, DataLayerException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        AnnuncioDAO annuncioDAO = new AnnuncioDAOImpl();
        List<Annuncio> annunci = annuncioDAO.getAnnunci(0, "ATTIVO");

        data.put("annunci", annunci);

        if (data.get("utente_tipo")!=null) {
            if (data.get("utente_tipo").equals("ST")) {

                res.activate("homeTirociniStudente.ftl.html", data, response);
            }else{
                res.activate("homeTirocini.ftl.html", data, response);
            }
        } else {
            res.activate("homeTirocini.ftl.html", data, response);
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
        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {
            data.put("utente_username", s.getAttribute("username"));
            data.put("utente_tipo", s.getAttribute("tipo"));
        }

        try {
            if (request.getParameter("refA") != null) {
                long idAnnuncio = SecurityLayer.issetInt(request.getParameter("refA"));
                Annuncio annuncio = new Annuncio(idAnnuncio);
                Studente studente = new Studente((long) s.getAttribute("userid"));
                
                Richiesta richiesta = new Richiesta(annuncio, studente);
                RichiestaDAO richiestaDAO = new RichiestaDAOImpl();
                
                int result = richiestaDAO.saveRichiesta(richiesta);
                
                if(result==1){
                data.put("alert", "200");
                }else{
                    data.put("alert", "400");
                }
            }
                action_default(data, request, response);
        } catch (SecurityLayerException | TemplateManagerException | DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

}
