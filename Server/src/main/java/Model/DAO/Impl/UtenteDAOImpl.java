/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Model.Bean.Utente;
import Model.DAO.Interface.UtenteDAO;
import Model.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author lorenzo
 */
public class UtenteDAOImpl implements UtenteDAO{
    
    private static final String GET_CREDENZIALI="SELECT * FROM Utente WHERE username=? AND password=?";

    
    @Override
    public Utente getCredenziali(String username, String password){
        DB db = new DB();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        Utente utente = null;
        
        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(GET_CREDENZIALI);
            ps.setString(1, username);
            ps.setString(2, password);
            rset = ps.executeQuery();

            if (rset.next()) {
                utente = new Utente(rset.getString("username"),rset.getString("password"));
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
        
        return utente;
}
}
