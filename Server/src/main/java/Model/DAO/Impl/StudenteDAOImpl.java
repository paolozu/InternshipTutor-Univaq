/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Model.DAO.Interface.StudenteDAO;
import Model.DB;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author lorenzo
 */
public class StudenteDAOImpl implements StudenteDAO {

    private static final String INSERIMENTO = "INSERT INTO `utente` "
            + "                                (`idAmministratore`, `username`, `password`, `Studente_idStudente`, `Azienda_idAzienda`)\n"
            + "VALUES\n"
            + "	(13, '', '', NULL, NULL);"; // inserimento casuale

    @Override
    public void executeQ() {

        DB db = new DB();
        PreparedStatement ps = null;
        Connection connection = null;

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(INSERIMENTO);
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
