/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Model.Bean.Utente;
import Model.DAO.Interface.UtenteDAO;
import Framework.data.DB;
import Framework.data.DataLayerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author lorenzo
 */
public class UtenteDAOImpl implements UtenteDAO {

    private static final String GET_CREDENZIALI = "SELECT * FROM Utente WHERE username=? AND password=?";
    private static final String SET_NUOVO_UTENTE = "INSERT INTO Utente(username,password,email,tipologia) VALUES(?,?,?,?)";
    private static final String GET_USERNAME_ESISTENTE = "SELECT Utente.username FROM Utente WHERE Utente.username=?";
    private static final String GET_EMAIL_ESISTENTE = "SELECT Utente.email FROM Utente WHERE Utente.email=?";

    @Override
    public Utente getCredenziali(String username, String password) throws DataLayerException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        Utente utente = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_CREDENZIALI);
            ps.setString(1, username);
            ps.setString(2, password);
            rset = ps.executeQuery();

            if (rset.next()) {
                utente = new Utente(rset.getLong("idUtente"),rset.getString("username"),rset.getString("email"), rset.getString("tipologia"));
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

        return utente;
    }

    @Override
    public Utente nuovoUtente(Utente utente) throws DataLayerException{

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(SET_NUOVO_UTENTE,Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, utente.getUsername());
            ps.setString(2, utente.getPassword());
            ps.setString(3, utente.getEmail());
            ps.setString(4, utente.getTipo());

            int result = ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                utente.setId(generatedKeys.getLong(1));
            } else {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, "Errore recupero ID utente");
                return null;
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE CREAZIONE UTENTE", ex);
        } finally {
            try {
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, "Errore chiusura connessione", ex);
                return null;
            }
        }
        return utente;
    }

    @Override
    public boolean getUsernameEsistente(String username) throws DataLayerException{

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_USERNAME_ESISTENTE);
            ps.setString(1, username);

            rset = ps.executeQuery();

            if (rset.next()) {
                return true;
            }

        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, "Errore chiusura connessione", ex);
            }
        }

        return false;
    }
    
    @Override
    public boolean getEmailEsistente(String email) throws DataLayerException{

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_EMAIL_ESISTENTE);
            ps.setString(1, email);

            rset = ps.executeQuery();

            if (rset.next()) {
                return true;
            }

        } catch (SQLException  ex) {
             throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, "Errore chiusura connessione", ex);
            }
        }

        return false;
    }
}
