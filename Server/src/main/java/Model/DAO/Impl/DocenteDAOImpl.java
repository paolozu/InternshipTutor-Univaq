/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Model.DAO.Interface.DocenteDAO;
import Model.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author lorenzo
 */
public class DocenteDAOImpl implements DocenteDAO {

    private static final String SET_DOCENTE = "INSERT INTO docente (nome, cognome, email) VALUES (?,?,?);";

    @Override
    public void setDocente(String nome, String cognome, String email) {

        DB db = new DB();
        PreparedStatement ps = null;
        Connection connection = null;

        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(SET_DOCENTE);
            ps.setString(1, nome);
            ps.setString(2, cognome);
            ps.setString(3, email);

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
