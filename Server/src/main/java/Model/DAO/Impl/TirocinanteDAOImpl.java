/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Model.Bean.Annuncio;
import Model.Bean.Azienda;
import Model.Bean.Convenzione;
import Model.Bean.Resoconto;
import Model.Bean.Tirocinio;
import Model.Bean.Docente;
import Model.DAO.Interface.TirocinanteDAO;
import Framework.data.DB;
import Framework.data.DataLayerException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author lorenzo
 */
public class TirocinanteDAOImpl implements TirocinanteDAO {

    private static final String GET_INFO_TIROCINIO="SELECT Tirocinio.dataInizio, Tirocinio.dataFine, Tirocinio.Resoconto_idResoconto,Resoconto.nome,Resoconto.valutazione,Azienda.ragSociale, "
            + "                                 Azienda.indirizzoSede, Azienda.citta, Azienda.nomeResponsabile, Azienda.cognomeResponsabile, "
            + "                                 Azienda.emailResponsabile, Azienda.telResponsabile, Annuncio.nomeDocente, Annuncio.cognomeDocente,"
            + "                                 Annuncio.emailDocente "
            + "                                     FROM Tirocinio "
            + "                                     JOIN Annuncio ON Tirocinio.Annuncio_idAnnuncio = Annuncio.idAnnuncio "
            + "                                     LEFT JOIN Resoconto ON Tirocinio.Resoconto_idResoconto = Resoconto.idResoconto "
            + "                                     JOIN Azienda ON Annuncio.Azienda_idAzienda = Azienda.idAzienda"
            + "                                     WHERE Tirocinio.Studente_idStudente=?";
            
    
    private static final String SET_VALUTAZIONE="UPDATE Resoconto SET valutazione=? WHERE idResoconto=?";
    
    private static final String GET_PATH_RESOCONTO="SELECT Resoconto.nome, Resoconto.directory, Resoconto.estensione, Resoconto.peso FROM Resoconto WHERE idResoconto=?";
    
    private static final String DOWNLOAD_RESOCONTO="SELECT Resoconto.file FROM Resoconto WHERE idResoconto=?";
    
    private static final String UPLOAD_RESOCONTO = "UPDATE Resoconto SET Nome= ?, File=?, Estensione=?, Peso=? WHERE idResoconto=?;";

    
    @Override
    public List<Tirocinio> getTirocini(long idStudente) throws DataLayerException{     
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<Tirocinio> tirocini = new ArrayList();
        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_INFO_TIROCINIO);
            ps.setLong(1, idStudente);
            rset = ps.executeQuery();
            while (rset.next()) {
                Resoconto resocontoAnnuncio = new Resoconto(rset.getInt("Tirocinio.Resoconto_idResoconto"),rset.getString("Resoconto.nome"),rset.getInt("Resoconto.valutazione"));
                Docente docenteAnnuncio = new Docente(rset.getString("Annuncio.nomeDocente"),rset.getString("Annuncio.cognomeDocente"), rset.getString("Annuncio.emailDocente"));
                Azienda aziendaAnnuncio = new Azienda(rset.getString("ragSociale"), rset.getString("indirizzoSede"),  rset.getString("citta"),rset.getString("nomeResponsabile"), rset.getString("cognomeResponsabile"), rset.getString("emailResponsabile"),rset.getString("telResponsabile"));
                Annuncio annuncio = new Annuncio(aziendaAnnuncio, docenteAnnuncio);
                tirocini.add(new Tirocinio(resocontoAnnuncio,annuncio, rset.getDate("Tirocinio.dataInizio").toLocalDate(),rset.getDate("Tirocinio.dataFine").toLocalDate()));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE INFO TIROCINIO UTENTE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tirocini;
    }

    @Override
    public void setValutazione(int valutazione,long idResoconto) throws DataLayerException{
      
        DB db = new DB();
        PreparedStatement ps = null;
        Connection connection = null;
      
        try {
            connection = db.getConnection();
            ps = connection.prepareStatement(SET_VALUTAZIONE);
            ps.setInt(1, valutazione);
            ps.setLong(2, idResoconto);
           
            int result = ps.executeUpdate();
        } catch (SQLException  ex) {
             throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
        } finally {
            try {
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public Resoconto getPathResoconto(int idResoconto) throws DataLayerException {
        //REMOVE
        return null;
    }
    
    @Override
    public InputStream downloadResoconto(Resoconto resoconto) throws DataLayerException {
        PreparedStatement ps;
        InputStream inputStream = null;

        try (Connection connection = DB.getConnection()) {
            ps = connection.prepareStatement(DOWNLOAD_RESOCONTO);
            ps.setLong(1, resoconto.getId());
            ResultSet rset = ps.executeQuery();

            if (rset.next()) {
                Blob blob = rset.getBlob("file");
                inputStream = blob.getBinaryStream();
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            throw new DataLayerException("Get resoconto", e);
        }

        return inputStream;
    }

    @Override
    public int uploadResoconto(Resoconto resoconto) throws DataLayerException {
        System.out.println("res:"+resoconto.getId());
        System.out.println("query:"+resoconto.getFile());
        PreparedStatement ps;
        int result = 0;
        long idConvenzione;

        try (Connection connection = DB.getConnection()) {
            ps = connection.prepareStatement(UPLOAD_RESOCONTO);

            ps.setString(1, resoconto.getNome());
            ps.setBlob(2, resoconto.getFile());
            ps.setString(3, resoconto.getEstensione());
            ps.setLong(4, resoconto.getPeso());
            ps.setLong(5, resoconto.getId());
            
            System.out.println("query:"+ps.toString());
            result = ps.executeUpdate();
             

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new DataLayerException("ERRORE UPLOAD", e);
        }
        
        return result;
    }
    
   
}
