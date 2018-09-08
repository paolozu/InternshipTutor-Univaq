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
import Model.DAO.Impl.AmministratoreDAOImpl;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Interface.AmministratoreDAO;
import Model.DAO.Interface.AziendaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author lorenzo
 */
public class infoAzienda extends AmministratoreSecurity {

    private AziendaDAO aziendaDAO;
    private AmministratoreDAO ammDAO;

    public infoAzienda() {
        super();
        ammDAO = new AmministratoreDAOImpl();
        aziendaDAO = new AziendaDAOImpl();
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
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_registrata(HttpServletRequest request, HttpServletResponse response, long idAzienda) throws IOException, TemplateManagerException {
        Map data = new HashMap();
        Azienda azienda = new Azienda(idAzienda);

        if (request.getParameter("approva") != null) {
            try {
                //Action azienda approvata
                aziendaDAO.updateStato(new Azienda(idAzienda), "APPROVATA");
            } catch (DataLayerException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }
            s.setAttribute("message", "Azienda approvata correttamente");
            response.sendRedirect("homepage");
            
        } else if (request.getParameter("rifiuta") != null) {
            //Action azienda rifiutata
            try {
                aziendaDAO.removeAzienda(azienda);
            } catch (DataLayerException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }
            s.setAttribute("message", "Azienda rimossa correttamente");
            response.sendRedirect("homepage");
        }else{
            //Action default
            try {
                azienda = aziendaDAO.getAzienda(azienda);

                //TEMPLATE
                data.put("azienda", azienda);
                data.put("outline_tpl", "");//rimozione outline
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("approvazioneAzienda.ftl.html", data, response);

            } catch (DataLayerException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }
        }
    }

    private void action_convenzioanta(HttpServletRequest request, HttpServletResponse response, long idAzienda) throws IOException, TemplateManagerException, ServletException {

    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        long idAzienda;

        try {
            if (request.getParameter("refA") != null && request.getParameter("action") != null) {

                idAzienda = SecurityLayer.checkNumeric(request.getParameter("refA"));
                String action = request.getParameter("action");

                switch (action) {
                    case "registrata":
                        action_registrata(request, response, idAzienda);
                        break;
                    case "convenzionata":
                        action_convenzioanta(request, response, idAzienda);
                        break;
                    default:
                        request.setAttribute("message", "Invalid action");
                        action_error(request, response);
                }
            } else {
                request.setAttribute("message", "Invalid reference");
                action_error(request, response);
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Invalid number submitted");
            action_error(request, response);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

}
