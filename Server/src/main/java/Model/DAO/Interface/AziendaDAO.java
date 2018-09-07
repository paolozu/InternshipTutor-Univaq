/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Interface;

import Framework.data.DataLayerException;
import Model.Bean.Azienda;
import Model.Bean.Convenzione;
import Model.Bean.Richiesta;
import Model.Bean.Studente;
import Model.Bean.Tirocinio;
import java.util.List;

/**
 *
 * @author lorenzo
 */
public interface AziendaDAO {

    public List<Azienda> getAziende() throws DataLayerException;
    
    public int removeAzienda(Azienda azienda) throws DataLayerException;
    
    public Azienda getAzienda(Azienda azienda) throws DataLayerException;
    
    public List<Azienda> getAllAziendeConvenzionate() throws DataLayerException;

    public List<Richiesta> getRichieste(long id) throws DataLayerException;
    
    public Richiesta getRichiestaStudente(Richiesta richiesta) throws DataLayerException;

    public List<Tirocinio> getTirocini(long id) throws DataLayerException;

    public Convenzione getConvenzione(long id) throws DataLayerException;

    public boolean isConvenzionata(long id) throws DataLayerException;

    public String getStato(long id) throws DataLayerException;

    public Azienda getApprovazione(long id) throws DataLayerException;

    public void setConcludiTirocinio(Tirocinio tirocinio) throws DataLayerException;

    public int setRegistrazioneAzienda(Azienda azienda) throws DataLayerException;

    public int updateStato(Azienda azienda, String stato) throws DataLayerException;

    public void removeTirocinio(Tirocinio tirocinio) throws DataLayerException;

    public void removeRichiesta(Richiesta richiesta) throws DataLayerException;
    
    public int setNuovoTirocinio(Tirocinio tirocinio) throws DataLayerException;

}
