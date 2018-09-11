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
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author lorenzo
 */
public interface TirocinioDAO {

    public List<Tirocinio> getTirocini(long idTirocinante) throws DataLayerException;

    public int setValutazione(int valutazione, long idResoconto) throws DataLayerException;

    public InputStream downloadResoconto(Resoconto resoconto) throws DataLayerException;

    public int uploadResoconto(Resoconto resoconto) throws DataLayerException;

}
