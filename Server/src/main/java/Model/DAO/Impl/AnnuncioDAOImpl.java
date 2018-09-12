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

    private static final String GET_ANNUNCIO_BY_ID = "SELECT * FROM annuncio JOIN azienda ON annuncio.Azienda_idAzienda=azienda.idAzienda  WHERE annuncio.idAnnuncio = ?";

    private static final String GET_ANNUNCI_AZIENDA_BY_STATO = "SELECT * FROM annuncio JOIN azienda ON annuncio.Azienda_idAzienda=azienda.idAzienda WHERE annuncio.Azienda_idAzienda=? AND annuncio.stato=? LIMIT ?,4";

    private static final String GET_ANNUNCI_STATO = "SELECT * FROM annuncio JOIN azienda ON annuncio.Azienda_idAzienda=azienda.idAzienda WHERE annuncio.stato=? LIMIT ?,4";

    private static final String SAVE_ANNUNCIO = "INSERT INTO annuncio (titolo, corpo, dataAvvio, dataTermine, modalita, sussidio, settore, Azienda_idAzienda,nomeDocente,cognomeDocente,emailDocente,nomeReferente,cognomeReferente,emailReferente,telefonoReferente,stato) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'ATTIVO')";

    private static final String UPDATE_STATO = "UPDATE Annuncio SET Stato=? WHERE idAnnuncio=?";
    
    private static final String GET_ANNUNCI_SEARCH = "SELECT *, MATCH(Corpo,Citta) AGAINST(?) AS Rate FROM Annuncio JOIN azienda ON annuncio.Azienda_idAzienda=azienda.idAzienda WHERE MATCH(Corpo) AGAINST(?)  AND Annuncio.Stato='ATTIVO' ORDER BY RATE DESC LIMIT ?,4";
    
    private static final String COUNT_ANNUNCI="SELECT COUNT(*) AS Number FROM Annuncio";
    
    
    @Override
    public int countAnnunci() throws DataLayerException{
    
        int result = 0;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(COUNT_ANNUNCI)) {
                try (ResultSet rset = ps.executeQuery()) {

                    if (rset.next()) {
                        result =  rset.getInt("Number");
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("COUNT ANNUNCIO", ex);
        }
        
        return result;
    }
    
    @Override
    public Annuncio getAnnuncioById(long id) throws DataLayerException {

        Annuncio annuncio = null;

        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_ANNUNCIO_BY_ID)) {
                ps.setLong(1, id);
                try (ResultSet rset = ps.executeQuery()) {

                    if (rset.next()) {
                        Referente referenteAnnuncio = new Referente(rset.getString("nomeReferente"), rset.getString("cognomeReferente"), rset.getString("telefonoReferente"));
                        Docente docenteAnnuncio = new Docente(rset.getString("nomeDocente"), rset.getString("cognomeDocente"), rset.getString("emailDocente"));
                        Azienda aziendaAnnuncio = new Azienda(rset.getInt("idAzienda"));
                        annuncio = new Annuncio(rset.getInt("idAnnuncio"), rset.getString("titolo"), rset.getString("corpo"), rset.getDate("dataAvvio").toLocalDate(), rset.getDate("dataTermine").toLocalDate(), rset.getString("modalita"), rset.getString("settore"), rset.getString("sussidio"), aziendaAnnuncio, docenteAnnuncio, referenteAnnuncio);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("GET ANNUNCIO", ex);
        }

        return annuncio;
    }

    @Override
    public List<Annuncio> getAnnunci(long idAzienda, int valuePage, String stato) throws DataLayerException {

        List<Annuncio> annunci = new ArrayList();

        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_ANNUNCI_AZIENDA_BY_STATO)) {
                ps.setLong(1, idAzienda);
                ps.setString(2, stato);
                ps.setInt(3, valuePage);
                try (ResultSet rset = ps.executeQuery()) {
                    while (rset.next()) {
                        annunci.add(
                                new Annuncio(rset.getInt("idAnnuncio"),
                                rset.getString("titolo"), rset.getString("corpo"),
                                rset.getDate("dataAvvio").toLocalDate(),
                                rset.getDate("dataTermine").toLocalDate(),
                                rset.getString("modalita"),
                                rset.getString("settore"),
                                rset.getString("sussidio"),
                                new Azienda(rset.getInt("idAzienda")))
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("GET ANNUNCI FROM AZIENDA", ex);
        }

        return annunci;
    }

    @Override
    public List<Annuncio> getAnnunci(int valuePage, String stato) throws DataLayerException {

        List<Annuncio> annunci = new ArrayList();
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_ANNUNCI_STATO)) {

                ps.setString(1, stato);
                ps.setInt(2, valuePage);

                try (ResultSet rset = ps.executeQuery()) {
                    while (rset.next()) {
                        annunci.add(new Annuncio(rset.getInt("idAnnuncio"),
                                rset.getString("titolo"),
                                rset.getString("corpo"),
                                rset.getDate("dataAvvio").toLocalDate(),
                                rset.getDate("dataTermine").toLocalDate(),
                                rset.getString("modalita"),
                                rset.getString("settore"),
                                rset.getString("sussidio"),
                                new Azienda(rset.getInt("idAzienda")))
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("GET ANNUNCI BY PAGE", ex);
        }

        return annunci;
    }

    @Override
    public List<Annuncio> getAnnunciSearch(int valuePage, String campoRicerca) throws DataLayerException{
    
        List<Annuncio> annunci = new ArrayList();
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_ANNUNCI_SEARCH)) {

                ps.setString(1, campoRicerca);
                ps.setString(2, campoRicerca);
                ps.setInt(3, valuePage);

                try (ResultSet rset = ps.executeQuery()) {
                    while (rset.next()) {
                        annunci.add(new Annuncio(rset.getInt("idAnnuncio"),
                                rset.getString("titolo"),
                                rset.getString("corpo"),
                                rset.getDate("dataAvvio").toLocalDate(),
                                rset.getDate("dataTermine").toLocalDate(),
                                rset.getString("modalita"),
                                rset.getString("settore"),
                                rset.getString("sussidio"),
                                new Azienda(rset.getInt("idAzienda")))
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("GET ANNUNCI BY SEARCH", ex);
        }

        return annunci;
    }
    
    @Override
    public int saveAnnuncio(Annuncio annuncio) throws DataLayerException {
        int result = -1;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SAVE_ANNUNCIO)) {

                ps.setString(1, annuncio.getTitolo());
                ps.setString(2, annuncio.getCorpo());
                ps.setDate(3, java.sql.Date.valueOf(annuncio.getDataAvvio()));
                ps.setDate(4, java.sql.Date.valueOf(annuncio.getDataTermine()));
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

                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DataLayerException("SAVE ANNUNCIO", ex);
        }
        return result;
    }

    @Override
    public int updateStato(Annuncio annuncio) throws DataLayerException{
    
        int result = -1;
        
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_STATO)) {

                //Prepare query
                ps.setString(1, annuncio.getStato());
                ps.setLong(2, annuncio.getId());

                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DataLayerException("UPDATE STATO ANNUNCIO", ex);
        }
        
        return result;
    }
    
    
}
