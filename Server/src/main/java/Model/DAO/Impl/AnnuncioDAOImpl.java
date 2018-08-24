/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Model.Bean.Annuncio;
import Model.Bean.Azienda;
import Model.Bean.Referente;
import Model.Bean.Docente;
import Model.DAO.Interface.AnnuncioDAO;
import Model.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author lorenzo
 */
public class AnnuncioDAOImpl implements AnnuncioDAO {

    private static final String GET_ANNUNCIO_BY_ID = "SELECT * FROM annuncio JOIN azienda "
            + "                                     ON annuncio.Azienda_idAzienda=azienda.idAzienda  "
            + "                                     JOIN docente ON annuncio.Docente_idDocente=docente.idDocente"
            + "                                     JOIN referente ON annuncio.Referente_idReferente=Referente.idReferente"
            + "                                     WHERE annuncio.idAnnuncio = ?";

    private static final String GET_ANNUNCI = "SELECT * FROM annuncio JOIN azienda "
            + "                                     ON annuncio.Azienda_idAzienda=azienda.idAzienda";

    private static final String SET_ANNUNCIO = "INSERT INTO annuncio (titolo, corpo, dataAvvio, dataTermine, modalita, sussidio, settore, Azienda_idAzienda,Docente_idDocente, Referente_idReferente)\n"
            + "VALUES(?,?,?,?,?,?,?,?,?,?)";

    public Annuncio getAnnuncioById(int id) {
        DB db = new DB();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        Annuncio annuncio = null;

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(GET_ANNUNCIO_BY_ID);
            ps.setInt(1, id);
            rset = ps.executeQuery();

            if (rset.next()) {
                Referente referenteAnnuncio = new Referente(rset.getString("referente.nome"), rset.getString("referente.cognome"),rset.getString("referente.telefono"));
                Docente docenteAnnuncio = new Docente(rset.getInt("idDocente"), rset.getString("nome"), rset.getString("cognome"), rset.getString("telefono"));
                Azienda aziendaAnnuncio = new Azienda(rset.getInt("idAzienda"));
                annuncio = new Annuncio(rset.getInt("idAnnuncio"), rset.getString("titolo"), rset.getString("corpo"), rset.getDate("dataAvvio"), rset.getDate("dataTermine"), rset.getString("modalita"), rset.getString("settore"), rset.getString("sussidio"), aziendaAnnuncio, docenteAnnuncio,referenteAnnuncio);
            }
        } catch (SQLException | NamingException ex) {
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
        return annuncio;

    }

    @Override
    public List<Annuncio> getAnnunci() {
        DB db = new DB();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<Annuncio> annunci = new ArrayList();
        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(GET_ANNUNCI);
            rset = ps.executeQuery();
            while (rset.next()) {
                annunci.add(new Annuncio(rset.getInt("idAnnuncio"), rset.getString("titolo"), rset.getString("corpo"), rset.getDate("dataAvvio"), rset.getDate("dataTermine"), rset.getString("modalita"), rset.getString("settore"), rset.getString("sussidio"), new Azienda(rset.getInt("idAzienda"))));
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
        return annunci;
    }

    @Override
    public void setAnnuncio(String titolo, String corpo, LocalDate dataAvvio, LocalDate dataTermine, String modalita, String sussidio, String settore, int idAzienda, int idDocente,int idReferente) {

        DB db = new DB();
        PreparedStatement ps = null;
        Connection connection = null;
      
        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(SET_ANNUNCIO);
            ps.setString(1, titolo);
            ps.setString(2, corpo);
            ps.setDate(3, java.sql.Date.valueOf( dataAvvio ));
            ps.setDate(4, java.sql.Date.valueOf( dataTermine ));
            ps.setString(5, modalita);
            ps.setString(6, sussidio);
            ps.setString(7, settore);
            ps.setInt(8, idAzienda);
            ps.setInt(9, idDocente);
            ps.setInt(10, idReferente);
            
            
            int result = ps.executeUpdate();
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
