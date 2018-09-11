/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Model.Bean.Studente;
import Model.Bean.Utente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Model.DAO.Interface.StudenteDAO;
import Model.DAO.Interface.UtenteDAO;
import Framework.data.DB;
import Framework.data.DataLayerException;
import java.sql.ResultSet;

/**
 *
 * @author lorenzo
 */
public class StudenteDAOImpl implements StudenteDAO {

    private static final String REGISTRAZIONE_STUDENTE = "INSERT INTO Studente (idStudente, nome, cognome, codFiscale, telefono, handicap, dataNascita, indirizzoResidenza, corsoLaurea, diploma, laurea, dottorato, cap_nascita, citta_nascita, provincia_nascita, cap_residenza, citta_residenza, provincia_residenza) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_STUDENTE = "SELECT * FROM Studente WHERE Studente.idStudente=?";

    @Override
    public int setRegistrazioneStudente(Studente studente) throws DataLayerException {
        int result = -1;

        //Creazione Utente
        UtenteDAO u = new UtenteDAOImpl();
        Utente nuovoUtente = u.nuovoUtente(new Utente(studente.getUsername(), studente.getPassword(), studente.getEmail(), "ST"));

        if (nuovoUtente != null) {//NUOVO UTENTE

            try (Connection connection = DB.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement(REGISTRAZIONE_STUDENTE)) {
                    //Prepare statement
                    ps.setLong(1, nuovoUtente.getId());
                    ps.setString(2, studente.getNome());
                    ps.setString(3, studente.getCognome());
                    ps.setString(4, studente.getCodFiscale());
                    ps.setString(5, studente.getTelefono());
                    ps.setBoolean(6, studente.isHandicap());
                    ps.setDate(7, java.sql.Date.valueOf(studente.getDataNascita()));
                    ps.setString(8, studente.getIndirizzoResidenza());
                    ps.setString(9, studente.getCorsoLaurea());
                    ps.setString(10, studente.getDiploma());
                    ps.setString(11, studente.getLaurea());
                    ps.setString(12, studente.getDottorato());
                    ps.setString(13, studente.getCapNascita());
                    ps.setString(14, studente.getCittaNascita());
                    ps.setString(15, studente.getProvinciaNascita());
                    ps.setString(16, studente.getCapResidenza());
                    ps.setString(17, studente.getCittaResidenza());
                    ps.setString(18, studente.getProvinciaResidenza());

                    result = ps.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new DataLayerException("REGISTRAZIONE STUDENTE", ex);
            }
        }
        return result;
    }

    @Override
    public Studente getStudente(long id) throws DataLayerException {

        Studente studente = null;

        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_STUDENTE)) {
                ps.setLong(1, id);
                try (ResultSet rset = ps.executeQuery()) {

                    if (rset.next()) {
                        studente = new Studente(
                                rset.getLong("idStudente"),
                                rset.getString("nome"),
                                rset.getString("cognome"),
                                rset.getString("codFiscale"),
                                rset.getString("telefono"),
                                rset.getString("indirizzoResidenza"),
                                rset.getString("corsoLaurea"),
                                rset.getString("cap_Residenza"),
                                rset.getString("citta_Residenza"),
                                rset.getString("provincia_Residenza"),
                                rset.getBoolean("handicap"),
                                rset.getDate("dataNascita").toLocalDate()
                        );
                    } else {
                        throw new DataLayerException("GET STUDENTE");
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("GET STUDENTE", ex);
        }
        return studente;
    }
}
