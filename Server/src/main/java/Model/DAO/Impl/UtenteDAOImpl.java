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
    public Utente getCredenziali(String username, String password) throws DataLayerException {

        Utente utente = null;

        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_CREDENZIALI)) {
                //Prepare statement
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rset = ps.executeQuery()) {

                    if (rset.next()) {
                        utente = new Utente(
                                rset.getLong("idUtente"),
                                rset.getString("username"),
                                rset.getString("email"),
                                rset.getString("tipologia")
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("CREDENZIALI UTENTE", ex);
        }
        return utente;
    }

    @Override
    public Utente nuovoUtente(Utente utente) throws DataLayerException {

        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SET_NUOVO_UTENTE, Statement.RETURN_GENERATED_KEYS)) {
                //Prepare statement
                ps.setString(1, utente.getUsername());
                ps.setString(2, utente.getPassword());
                ps.setString(3, utente.getEmail());
                ps.setString(4, utente.getTipo());

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {

                    if (generatedKeys.next()) {
                        utente.setId(generatedKeys.getLong(1));
                    } else {
                        throw new DataLayerException("RECUPERO UTENTE");
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("NUOVO UTENTE", ex);
        }

        return utente;
    }

    @Override
    public boolean getUsernameEsistente(String username) throws DataLayerException {

        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_USERNAME_ESISTENTE)) {
                //Prepare statement
                ps.setString(1, username);

                try (ResultSet rset = ps.executeQuery()){
                    if (rset.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("USERNAME UTENTE", ex);
        }
        
        return false;
    }

    @Override
    public boolean getEmailEsistente(String email) throws DataLayerException {

        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_EMAIL_ESISTENTE)) {
                //Prepare statement
                ps.setString(1, email);

                try (ResultSet rset = ps.executeQuery()){
                    if (rset.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("EMAIL UTENTE", ex);
        }
        
        return false;
    }
}
