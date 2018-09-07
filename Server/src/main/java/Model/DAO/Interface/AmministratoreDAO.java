/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Interface;

import Framework.data.DataLayerException;
import Model.Bean.Azienda;
import Model.Bean.Convenzione;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author lorenzo
 */
public interface AmministratoreDAO {
    public List<Azienda> getListaAziende(String tipologia, int page) throws DataLayerException;
    public int setConvenzione(Convenzione convenzione, Azienda azienda) throws DataLayerException;
}
