/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import Model.DAO.Interface.StudenteDAO;
import Model.DB;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author lorenzo
 */
public class StudenteDAOImpl implements StudenteDAO {

    private static final String INSERIMENTO = "INSERT INTO `utente` (`idAmministratore`, `username`, `password`, `Studente_idStudente`, `Azienda_idAzienda`)\n" +
"VALUES\n" +
"	(13, '', '', NULL, NULL);"; // inserimento casuale

    @Override
    public void executeQ() {

    DB db = new DB();
    Connection connection;
    
        try {
            connection = db.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(INSERIMENTO)) {
            int result = ps.executeUpdate();
            }
            connection.close();
            db.closeCon();
                
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
          

    }
    
    

}
