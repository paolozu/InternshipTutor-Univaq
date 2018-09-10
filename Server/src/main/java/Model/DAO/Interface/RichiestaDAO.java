/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Interface;

import Framework.data.DataLayerException;
import Model.Bean.Richiesta;
import Model.Bean.Studente;

/**
 *
 * @author lorenzo
 */
public interface RichiestaDAO {
    
    public int saveRichiesta(Richiesta richiesta) throws DataLayerException;
    
    public int deleteRichiesta(Richiesta richiesta) throws DataLayerException;
    
    public int updateRichiesta(Richiesta richiesta) throws DataLayerException;
}
