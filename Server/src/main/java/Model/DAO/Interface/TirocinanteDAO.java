/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Interface;

import Framework.data.DataLayerException;
import Model.Bean.Annuncio;
import Model.Bean.Resoconto;
import Model.Bean.Tirocinio;
import java.util.List;

/**
 *
 * @author lorenzo
 */
public interface TirocinanteDAO {
    
     public List<Tirocinio> getInfoTirocinio(int idTirocinante) throws DataLayerException;
     public void setValutazione(int valutazione, int idResoconto) throws DataLayerException;
     public Resoconto getPathResoconto(int idResoconto) throws DataLayerException;
}
