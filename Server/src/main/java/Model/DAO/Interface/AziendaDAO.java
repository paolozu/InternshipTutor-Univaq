/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Interface;

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
    
      public List<Azienda> getAziende();
      public List<Studente> getRichieste(long id);
      public List<Studente> getTirocinanti(long id);
      public Convenzione getConvenzione(long id);
      public Azienda getApprovazione(long id);
      public void setConcludiTirocinio(Tirocinio tirocinio);
      public int setRegistrazioneAzienda(Azienda azienda);
      public int updateStato(Azienda azienda,String stato);
    
}
