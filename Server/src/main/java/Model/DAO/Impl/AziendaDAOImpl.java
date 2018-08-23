/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Model.Bean.Annuncio;
import Model.Bean.Azienda;
import Model.Bean.Studente;
import Model.DAO.Interface.AziendaDAO;
import Model.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author lorenzo
 */
public class AziendaDAOImpl implements AziendaDAO {

    private static final String GET_RICHIESTE = "SELECT Studente.idStudente,Studente.nome,Studente.cognome,Studente.email "
            + "                                  FROM Azienda JOIN Richiesta  ON Azienda.idAzienda=Richiesta.Azienda_idAzienda "
            + "                                  JOIN Studente ON Richiesta.Studente_idStudente = Studente.idStudente "
            + "                                  WHERE Azienda.idAzienda = ?";

    private static final String GET_AZIENDE = "SELECT azienda.idAzienda,azienda.ragSociale FROM azienda";
    
    
    
    
    
    
    public List<Studente> getRichieste(int id) {
        DB db = new DB();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<Studente> richieste = new ArrayList();
        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(GET_RICHIESTE);
            ps.setInt(1, id);
            rset = ps.executeQuery();
            while (rset.next()) {
                richieste.add(new Studente(rset.getInt("idStudente"), rset.getString("nome"), rset.getString("cognome"), rset.getString("email")));
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return richieste;
    }

    public List<Azienda> getAziende() {
        DB db = new DB();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<Azienda> aziende = new ArrayList();
        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(GET_AZIENDE);
            rset = ps.executeQuery();
            while (rset.next()){
                aziende.add(new Azienda(rset.getInt("idAzienda"), rset.getString("ragSociale")));
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return aziende;
    }
}
