/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Interface;

import Model.Bean.Azienda;
import Model.Bean.Studente;
import java.util.List;

/**
 *
 * @author lorenzo
 */
public interface AziendaDAO {
    
      public List<Azienda> getAziende();
      public List<Studente> getRichieste(int id);
    
}
