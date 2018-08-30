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
import Framework.data.DB;
import Framework.data.DataLayerException;
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
            + "                                     WHERE annuncio.idAnnuncio = ?";

    private static final String GET_ANNUNCI = "SELECT * FROM annuncio JOIN azienda "
            + "                                     ON annuncio.Azienda_idAzienda=azienda.idAzienda LIMIT ?,4";

    private static final String SET_ANNUNCIO = "INSERT INTO annuncio (titolo, corpo, dataAvvio, dataTermine, modalita, sussidio, settore, Azienda_idAzienda,nomeDocente,cognomeDocente,emailDocente,nomeReferente,cognomeReferente,emailReferente,telefonoReferente)\n"
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public Annuncio getAnnuncioById(long id) throws DataLayerException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        Annuncio annuncio = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_ANNUNCIO_BY_ID);
            ps.setLong(1, id);
            rset = ps.executeQuery();

            if (rset.next()) {
                Referente referenteAnnuncio = new Referente(rset.getString("nomeReferente"), rset.getString("cognomeReferente"),rset.getString("telefonoReferente"));
                Docente docenteAnnuncio = new Docente(rset.getString("nomeDocente"), rset.getString("cognomeDocente"), rset.getString("emailDocente"));
                Azienda aziendaAnnuncio = new Azienda(rset.getInt("idAzienda"));
                annuncio = new Annuncio(rset.getInt("idAnnuncio"), rset.getString("titolo"), rset.getString("corpo"), rset.getDate("dataAvvio").toLocalDate(), rset.getDate("dataTermine").toLocalDate(), rset.getString("modalita"), rset.getString("settore"), rset.getString("sussidio"), aziendaAnnuncio, docenteAnnuncio,referenteAnnuncio);
            }
        } catch (SQLException ex) {
             throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
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
    public List<Annuncio> getAnnunci(int valuePage) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<Annuncio> annunci = new ArrayList();
        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_ANNUNCI);
            ps.setInt(1, valuePage);
            rset = ps.executeQuery();
            while (rset.next()) {
                annunci.add(new Annuncio(rset.getInt("idAnnuncio"), rset.getString("titolo"), rset.getString("corpo"), rset.getDate("dataAvvio").toLocalDate(), rset.getDate("dataTermine").toLocalDate(), rset.getString("modalita"), rset.getString("settore"), rset.getString("sussidio"), new Azienda(rset.getInt("idAzienda"))));
            }
        } catch (SQLException ex) {
             throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
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
    public void saveAnnuncio(Annuncio annuncio) throws DataLayerException{
        
        Connection connection = null;
        PreparedStatement ps = null;
        
        
        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(SET_ANNUNCIO);
            ps.setString(1, annuncio.getTitolo());
            ps.setString(2, annuncio.getCorpo());
            ps.setDate(3, java.sql.Date.valueOf( annuncio.getDataAvvio() ));
            ps.setDate(4, java.sql.Date.valueOf( annuncio.getDataTermine() ));
            ps.setString(5, annuncio.getModalita());
            ps.setString(6, annuncio.getSussidio());
            ps.setString(7, annuncio.getSettore());
            ps.setLong(8, annuncio.getAzienda().getId());
            ps.setString(9, annuncio.getDocente().getNome());
            ps.setString(10, annuncio.getDocente().getCognome());
            ps.setString(11, annuncio.getDocente().getEmail());
            ps.setString(12, annuncio.getReferente().getNome());
            ps.setString(13, annuncio.getReferente().getCognome());
            ps.setString(14, annuncio.getReferente().getEmail());
            ps.setString(15, annuncio.getReferente().getTelefono());
            
            
            int result = ps.executeUpdate();
        } catch (SQLException ex) {
             throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
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
