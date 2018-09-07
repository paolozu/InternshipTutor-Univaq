/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Model.Bean.Azienda;
import Model.Bean.Studente;
import Model.DAO.Interface.AmministratoreDAO;
import Framework.data.DB;
import Framework.data.DataLayerException;
import Model.Bean.Convenzione;
import java.io.IOException;
import java.io.InputStream;
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
public class AmministratoreDAOImpl implements  AmministratoreDAO{

    private static final String AZIENDE="SELECT * FROM Azienda WHERE Azienda.stato=? LIMIT ?,?";
    
    private static final String UPLOAD_CONVENZIONE="INSERT INTO convenzione (nome, file, estensione, peso)\n" +
                                                    "VALUES(?,?,?,?)";
    
    private static final String UPDATE_CONVEZIONE="UPDATE Azienda SET Convenzione_idConvenzione=?, Stato='CONVENZIONATA'  WHERE idAzienda=?;";
    private final int NUMBER_ELEMENT = 4;
    
    
        @Override
	public int setConvenzione(Convenzione convenzione, Azienda azienda) throws DataLayerException {
		PreparedStatement ps;
		int result = 0;
		long idConvenzione;
                
		try (Connection connection = DB.getConnection()) {
		ps = connection.prepareStatement(UPLOAD_CONVENZIONE,Statement.RETURN_GENERATED_KEYS);
		
                ps.setString(1, convenzione.getNome());
		ps.setBlob(2, convenzione.getFile().getInputStream());
		ps.setString(3, convenzione.getEstensione());
                ps.setLong(4, convenzione.getPeso());
		
                    if (ps.executeUpdate() == 1) {
                        try (ResultSet keys = ps.getGeneratedKeys()) {
                            keys.first();
                            idConvenzione = keys.getLong(1);
                            ps = connection.prepareStatement(UPDATE_CONVEZIONE);
                            ps.setLong(1, idConvenzione);
                            ps.setLong(2, azienda.getId());
                            
                            result = ps.executeUpdate();
                        }
                    } else {
                        //remove last insert
                    }
                
                ps.close();
		connection.close();
		} catch (IOException|SQLException e) {
			throw new DataLayerException("ERRORE UPLOAD", e);
		}
		
		return result;
	}
    
    @Override
    public List<Azienda> getListaAziende(String tipologia, int page ) throws DataLayerException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;

        List<Azienda> listaAziende = new ArrayList();
        
        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(AZIENDE);
            ps.setString(1, tipologia);
            ps.setInt(2, page*NUMBER_ELEMENT);
            ps.setInt(3, NUMBER_ELEMENT);
            rset = ps.executeQuery();
            while (rset.next()) {
                listaAziende.add(new Azienda(rset.getInt("idAzienda"), rset.getString("ragSociale")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, "LISTA AZIENDE", ex);
            throw new DataLayerException("Error getListaAziende", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                 Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, "CLOSE CONNECTION", ex);
            }
        }
        return listaAziende;
    }
    
    
    
}
