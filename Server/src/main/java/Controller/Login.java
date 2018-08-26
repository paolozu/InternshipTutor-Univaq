/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Framework.TemplateManagerException;
import Controller.Framework.TemplateResult;
import Model.Bean.Azienda;
import Model.Bean.Studente;
import Model.DAO.Impl.AziendaDAOImpl;
import Model.DAO.Impl.StudenteDAOImpl;
import Model.DAO.Interface.AziendaDAO;
import Model.DAO.Interface.StudenteDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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

/*ANNUNCIO*/
//        AnnuncioDAO query = new AnnuncioDAOImpl();
//        Annuncio annuncio = query.getAnnuncioById(1);
//          System.out.println(query.getAnnunci(1).toString());
//          System.out.println(query.getAnnunci(2).toString());
//          System.out.println(query.getAnnunci(3).toString());
//        System.out.println(annuncio.toString());
//
//        Azienda a = new Azienda(100);
//        Docente d = new Docente("no","co","em");
//        Referente r = new Referente("no","co","em","tel");
//        
//        Annuncio annuncio1 = new Annuncio("","",LocalDate.of(2014, 9, 9),LocalDate.of(2014, 9, 9), "","","",a,d,r);
//        query.saveAnnuncio(annuncio1);
/*AZIENDA*/
//        AziendaDAO queryA = new AziendaDAOImpl();
//        Resoconto r = new Resoconto(80,"OK","OK");
//        Studente s = new Studente(202);
//        Annuncio an = new Annuncio(1);
//        Tirocinio t = new Tirocinio(s,an,r);
//        queryA.setConcludiTirocinio(t);
//        System.out.println(queryA.getRichieste(101));
//        System.out.println(queryA.getAziende());
//        System.out.println(queryA.getTirocinanti(101));
//        System.out.println(queryA.getConvenzione(100));
//        System.out.println(queryA.getApprovazione(100).toString());
//        System.out.println(queryA.getApprovazione(100).getConvenzione().getDataConvezione());

/*AMMINISTRATORE*/
//        AmministratoreDAO queryAmm = new AmministratoreDAOImpl();
//        System.out.println(queryAmm.daConvenzionare());

/*TIROCINANTE*/
//        TirocinanteDAO queryT = new TirocinanteDAOImpl();
//        System.out.println(queryT.getInfoTirocinio(201)); //info  tirocinante 201
//        queryT.setValutazione(1, 1);  //Update valutazione tirocinio      
//        System.out.println(queryT.getPathResoconto(1)); //info  resoconto 1

/*UTENTE*/
//          UtenteDAO queryU = new UtenteDAOImpl();
//          Utente nuovoUtente = new Utente("lolo","123","email","ST");
//          System.out.println(queryU.nuovoUtente(nuovoUtente).getId());

//        System.out.println(queryU.getCredenziali("loreand", "123"));

/*NUOVA AZIENDA*/   //ATTENZIONE DUPLICATE
          AziendaDAO queryAz = new AziendaDAOImpl();
//          Azienda nuovoAzienda = new Azienda("lolo","123","email","AZ","nomR","cognR","telResp","nomeResp","cognResp","emailResp","ragSoc","ind","piva","foro","cap","citta","pro");
//          queryAz.setRegistrazioneAzienda(nuovoAzienda);
          
          Azienda nuovoAzienda = new Azienda(100);
          queryAz.updateStato(nuovoAzienda, "CONVENZIONATA");
          
/*NUOVO STUDENTE*/
        Studente st = new Studente("lolo2","123","email2","ST","nomstu5", "cognstu5", "codFisclare", "438543324", "indirizzoResidenza", "corsoLaurea",  "diploma", "laurea" , "no" ,  "543",  "capResidenza",  "citta", "pescara",  "cittaNascita", "provinciaNascita", 4, LocalDate.of(2014, 9, 9), false);
        StudenteDAO queryStu = new StudenteDAOImpl();
        queryStu.setRegistrazioneStudente(st);
/*REFERENTE*/
//        ReferenteDAO queryR =new ReferenteDAOImpl();
//        queryR.setReferente("Mario", "Verdi", "ref@mail.it", "380591435");

/*Docente*/
//          DocenteDAO queryD = new DocenteDAOImpl();
//          queryD.setDocente("Henry", "Muccini", "doc@mail.it");
          
/*TEMPLATE*/
        try {
            Map data = new HashMap();
            data.put("outline_tpl", "");//rimozione outline
            TemplateResult res = new TemplateResult(getServletContext());//inizializzazione
            res.activate("login.html", data, response);
        } catch (TemplateManagerException ex) {
            throw new ServletException(ex);
        }

    }

    public Login() {
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
