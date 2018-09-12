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
import Model.DAO.Impl.AnnuncioDAOImpl;
import Model.DAO.Impl.RichiestaDAOImpl;
import Model.DAO.Interface.AnnuncioDAO;
import Model.DAO.Interface.RichiestaDAO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
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

    private void action_richiesta(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException, DataLayerException, TemplateManagerException, SecurityLayerException {

//        if (request.getParameter("refA") != null) {
//                //Invio richiesta tirocinio
//                long idAnnuncio = SecurityLayer.issetInt(request.getParameter("refA"));
//                Annuncio annuncio = new Annuncio(idAnnuncio);
//                Studente studente = new Studente((long) s.getAttribute("userid"));
//
//                Richiesta richiesta = new Richiesta(annuncio, studente);
//                RichiestaDAO richiestaDAO = new RichiestaDAOImpl();
//
//                //Query
//                int result = richiestaDAO.saveRichiesta(richiesta);
//
//                //Notifica
//                switch (result) {
//                    case -1:
//                        data.put("alert", "-1");
//                        break;
//
//                    case 1:
//                        data.put("alert", "1");
//                        break;
//
//                    case 1062:
//                        data.put("alert", "1062");
//                        break;
//                }}
    }

    private void action_search(Map data, int page, String campo, HttpServletRequest request, HttpServletResponse response) throws IOException, DataLayerException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
        AnnuncioDAO annuncioDAO = new AnnuncioDAOImpl();

        List<Annuncio> annunci = annuncioDAO.getAnnunciSearch(page, campo);

        data.put("annunci", annunci);

        if (data.get("utente_tipo") != null) {
            if (data.get("utente_tipo").equals("ST")) {

                res.activate("homeTirociniStudente.ftl.html", data, response);
            } else {
                res.activate("homeTirocini.ftl.html", data, response);
            }
        } else {
            res.activate("homeTirocini.ftl.html", data, response);
        }

    }

    private void action_default(Map data, HttpServletRequest request, HttpServletResponse response) throws IOException, DataLayerException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());//inizializzazione

        if (data.get("utente_tipo") != null) {
            if (data.get("utente_tipo").equals("ST")) {

                res.activate("homeTirociniStudente.ftl.html", data, response);
            } else {
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
        data.put("activeTirocini", "active");

        //Check sessione
        HttpSession s = SecurityLayer.checkSession(request);
        if (s != null) {
            data.put("utente_username", s.getAttribute("username"));
            data.put("utente_tipo", s.getAttribute("tipo"));
        }

        try {
            if (request.getParameter("refA") != null) {
                // invia richiesta
            } else {
                int page = SecurityLayer.checkPage(request.getParameter("page"));
                String campo = request.getParameter("search");
                
                System.out.println("p"+page);
                
                AnnuncioDAO annuncioDAO = new AnnuncioDAOImpl();
                List<Annuncio> annunci;
                int count;
                
                if (campo != null) {
                    //azione ricerca
                     count = annuncioDAO.countAnnunci();//CHANGE
                     annunci = annuncioDAO.getAnnunciSearch(page*4, campo);
                } else {
                    System.out.println("qui");
                    //count annunci
                    count = annuncioDAO.countAnnunci()/4;
                    annunci = annuncioDAO.getAnnunci(page*4, "ATTIVO");
                }
                
                System.out.println("c"+count);
                if(page<=0){
                    data.put("removeLeft","hidden");
                //remove left arrow
                }else if(page >= count-1){
                //remove right arrow
                data.put("removeRight","hidden");
                }
                
                
                data.put("page", page);
                data.put("count", count-1);
                data.put("annunci", annunci);
                action_default(data,request, response);
            }
        } catch (SecurityLayerException | DataLayerException |TemplateManagerException ex) {
            request.setAttribute("exception", ex);
        action_error(request, response);
        }
    }
}
