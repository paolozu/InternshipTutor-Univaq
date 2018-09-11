/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Framework.data.DB;
import Framework.data.DataLayerException;
import Model.Bean.Richiesta;
import Model.DAO.Interface.RichiestaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author lorenzo
 */
public class RichiestaDAOImpl implements RichiestaDAO {

    private static final String SAVE_RICHIESTA = "INSERT INTO Richiesta (Studente_idStudente, Annuncio_idAnnuncio) VALUES (?, ?);";

    private static final String DELETE_RICHIESTA = "DELETE FROM Richiesta WHERE Studente_idStudente=? AND Annuncio_idAnnuncio=?;";

    @Override
    public int saveRichiesta(Richiesta richiesta) throws DataLayerException {
        int result = -1;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SAVE_RICHIESTA)) {

                ps.setLong(1, richiesta.getStudente().getId());
                ps.setLong(2, richiesta.getAnnuncio().getId());

                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                //duplicate primary key 
                return 1062;
            }
            throw new DataLayerException("SAVE RICHIESTA", ex);
        }
        return result;
    }

    @Override
    public int deleteRichiesta(Richiesta richiesta) throws DataLayerException {
        int result = -1;
        try (Connection connection = DB.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(DELETE_RICHIESTA)) {

                ps.setLong(1, richiesta.getStudente().getId());
                ps.setLong(2, richiesta.getAnnuncio().getId());

                result = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DataLayerException("DELETE RICHIESTA", ex);
        }
        return result;
    }

    @Override
    public int updateRichiesta(Richiesta richiesta) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
