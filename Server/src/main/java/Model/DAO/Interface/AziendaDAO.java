/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Interface;

import Framework.data.DataLayerException;
import Model.Bean.Azienda;
import Model.Bean.Convenzione;
import Model.Bean.Studente;
import Model.Bean.Tirocinio;
import java.util.List;

/**
 *
 * @author lorenzo
 */
public interface AziendaDAO {
    
      public List<Azienda> getAziende() throws DataLayerException;
      public List<Studente> getRichieste(long id) throws DataLayerException;
      public List<Studente> getTirocinanti(long id) throws DataLayerException;
      public Convenzione getConvenzione(long id) throws DataLayerException;
      public String getStato(long id) throws DataLayerException;
      public Azienda getApprovazione(long id) throws DataLayerException;
      public void setConcludiTirocinio(Tirocinio tirocinio) throws DataLayerException;
      public int setRegistrazioneAzienda(Azienda azienda) throws DataLayerException;
      public int updateStato(Azienda azienda,String stato) throws DataLayerException;
    
}
