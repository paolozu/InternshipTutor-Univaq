/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Model.Bean.Annuncio;
import Model.Bean.Azienda;
import Model.Bean.Tutore;
import Model.DAO.Interface.AnnuncioDAO;
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
public class AnnuncioDAOImpl implements AnnuncioDAO {
    
    private static final String GET_ANNUNCIO_BY_ID = "SELECT * FROM annuncio JOIN azienda "
            + "                                     ON annuncio.Azienda_idAzienda=azienda.idAzienda  "
            + "                                     JOIN tutore ON annuncio.Tutore_idTutore=tutore.idTutore"
            + "                                     WHERE annuncio.idAnnuncio = ?";
            
    
    
    public Annuncio getAnnuncioById(int id){
    DB db = new DB();
    Connection connection;
    Annuncio annuncio = null;
        try {
            connection = db.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(GET_ANNUNCIO_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rset = ps.executeQuery();
           
            if(rset.next()){
                Tutore tutoreAnnuncio = new Tutore(rset.getInt("idTutore"),rset.getString("nome"),rset.getString("cognome"),rset.getString("telefono"));
                Azienda aziendaAnnuncio = new Azienda(rset.getString("idAzienda"));
                annuncio = new Annuncio(rset.getInt("idAnnuncio"),rset.getString("titolo"),rset.getString("corpo"),rset.getDate("dataAvvio"),rset.getDate("dataTermine"),rset.getString("modalita"),rset.getString("settore"),rset.getString("sussidio"),aziendaAnnuncio,tutoreAnnuncio);
            }
            }
            connection.close();
            db.closeCon();
                
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return annuncio;
    }
    
    
}