/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Framework.data.DB;
import Framework.data.DataLayerException;
import Model.Bean.Annuncio;
import Model.Bean.Richiesta;
import Model.Bean.Studente;
import Model.DAO.Interface.RichiestaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lorenzo
 */
public class RichiestaDAOImpl implements RichiestaDAO {

    private static final String SAVE_RICHIESTA = "INSERT INTO Richiesta (Studente_idStudente, Annuncio_idAnnuncio, nomeDocente, cognomeDocente, crediti) VALUES (?, ?, ?, ?, ?);";

    private static final String DELETE_RICHIESTA = "DELETE FROM Richiesta WHERE Studente_idStudente=? AND Annuncio_idAnnuncio=?;";

    private static final String GET_RICHIESTA_STUDENTE = "SELECT * FROM Richiesta JOIN STUDENTE ON Richiesta.Studente_idStudente=Studente.idStudente WHERE Studente.idStudente=? AND Richiesta.Annuncio_idAnnuncio=?";

    
    @Override
    public int saveRichiesta(Richiesta richiesta) throws DataLayerException {
        int result = -1;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SAVE_RICHIESTA)) {

                ps.setLong(1, richiesta.getStudente().getId());
                ps.setLong(2, richiesta.getAnnuncio().getId());
                ps.setString(3, richiesta.getNome());
                ps.setString(4, richiesta.getCognome());
                ps.setInt(5, richiesta.getCrediti());

                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                //duplicate primary key 
                return 1062;
            }
            throw new DataLayerException("SAVE RICHIESTA", ex);
        }
        return result;
    }

    @Override
    public int deleteRichiesta(Richiesta richiesta) throws DataLayerException {
        int result = -1;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(DELETE_RICHIESTA)) {

                ps.setLong(1, richiesta.getStudente().getId());
                ps.setLong(2, richiesta.getAnnuncio().getId());

                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DataLayerException("DELETE RICHIESTA", ex);
        }
        return result;
    } 
    
    @Override
    public Richiesta getRichiestaStudente(Richiesta richiesta) throws DataLayerException {

        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_RICHIESTA_STUDENTE)) {

                ps.setLong(1, richiesta.getStudente().getId());
                ps.setLong(2, richiesta.getAnnuncio().getId());

                try (ResultSet rset = ps.executeQuery()) {

                    if (rset.next()) {

                        Studente s = new Studente(
                                rset.getLong("idStudente"),
                                rset.getString("nome"), rset.getString("cognome"),
                                rset.getString("codFiscale"), rset.getString("telefono"),
                                rset.getString("indirizzoResidenza"), rset.getString("corsoLaurea"),
                                rset.getString("cap_Residenza"), rset.getString("citta_Residenza"),
                                rset.getString("provincia_Residenza"), rset.getBoolean("handicap"),
                                rset.getDate("dataNascita").toLocalDate()
                        );

                        Annuncio a = new Annuncio(rset.getLong("Richiesta.Annuncio_idAnnuncio"));
                        richiesta = new Richiesta(a, s);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("GET RICHIESTA STUDENTE", ex);
        }
        return richiesta;
    }

}
