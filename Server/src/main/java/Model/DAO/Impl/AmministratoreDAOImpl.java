/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Model.Bean.Azienda;
import Model.Bean.Studente;
import Model.DAO.Interface.AmministratoreDAO;
import Model.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author lorenzo
 */
public class AmministratoreDAOImpl implements  AmministratoreDAO{

    private static final String DA_CONVENZIONARE="SELECT * FROM Azienda WHERE Azienda.abilitata=0;";
    private static final String CONVENZIONATE="SELECT * FROM Azienda WHERE Azienda.abilitata=1;";
    
    @Override
    public List<Azienda> daConvenzionare() {
        DB db = new DB();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<Azienda> daConvenzionare = new ArrayList();
        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(DA_CONVENZIONARE);
            rset = ps.executeQuery();
            while (rset.next()) {
                daConvenzionare.add(new Azienda(rset.getInt("idAzienda"), rset.getString("ragSociale")));
            }
        } catch (NamingException | SQLException ex) {
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
        return daConvenzionare;
    }

    @Override
    public List<Azienda> convenzionate() {
        DB db = new DB();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<Azienda> convenzionate = new ArrayList();
        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(CONVENZIONATE);
            rset = ps.executeQuery();
            while (rset.next()) {
                convenzionate.add(new Azienda(rset.getInt("idAzienda"), rset.getString("ragSociale")));
            }
        } catch (NamingException | SQLException ex) {
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
        return convenzionate;
    }
    
    
    
}
