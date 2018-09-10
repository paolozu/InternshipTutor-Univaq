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
import Model.Bean.Convenzione;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author lorenzo
 */
public class StudenteDAOImpl implements StudenteDAO {

    private static final String REGISTRAZIONE_STUDENTE="INSERT INTO Studente (idStudente, nome, cognome, codFiscale, telefono, handicap, dataNascita, indirizzoResidenza, corsoLaurea, diploma, laurea, dottorato, cap_nascita, citta_nascita, provincia_nascita, cap_residenza, citta_residenza, provincia_residenza)"
            +                                          " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_STUDENTE="SELECT * FROM Studente WHERE Studente.idStudente=?";
    
    
    @Override
    public int setRegistrazioneStudente(Studente studente) throws DataLayerException{
       int result = 0;
        //Creazione Utente
        UtenteDAO u = new UtenteDAOImpl();
        Utente nuovoUtente = u.nuovoUtente(new Utente(studente.getUsername(), studente.getPassword(), studente.getEmail(), "STU"));

        if (nuovoUtente != null) {//NUOVO UTENTE - OK

            Connection connection = null;
            PreparedStatement ps = null;

            try {
                connection = DB.getConnection(); // CREAZIONE CONNESSIONE
                
                ps = connection.prepareStatement(REGISTRAZIONE_STUDENTE);

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

            } catch (SQLException ex) {
                 throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
            } finally {
                //CHIUSURA CONNESSIONE
                try {
                    ps.close();
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {//NUOVO UTENTE - ERRORE
            Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, "ERRORE CREAZIONE - UTENTE");
            //Verificare presenza utenti
        }
        return result;
    }
    
    public Studente getStudente(long id) throws DataLayerException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        Studente studente = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_STUDENTE);
            ps.setLong(1, id);
            rset = ps.executeQuery();

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
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE GET STUDENTE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }
        return studente;
    }
    

}
