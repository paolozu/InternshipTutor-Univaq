/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Interface;

import Framework.data.DataLayerException;
import Model.Bean.Annuncio;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author lorenzo
 */
public interface AnnuncioDAO {

    public Annuncio getAnnuncioById(long id) throws DataLayerException;
    
    public Annuncio getAnnuncio(Annuncio annuncio) throws DataLayerException;

    public List<Annuncio> getAnnunci(long idAzienda, int valuePage, String stato) throws DataLayerException;

    public List<Annuncio> getAnnunci(int valuePage, String stato) throws DataLayerException;
    
    public List<Annuncio> getAnnunciSearch(int valuePage, String campoRicerca) throws DataLayerException;

    public int saveAnnuncio(Annuncio annuncio) throws DataLayerException;
    
    public int updateStato(Annuncio annuncio) throws DataLayerException;
    
    public int countAnnunci() throws DataLayerException;
    
    public int countAnnunciSearch(String campoRicerca) throws DataLayerException;
    
    
}
