/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Interface;

import Model.Bean.Azienda;
import java.util.List;

/**
 *
 * @author lorenzo
 */
public interface AmministratoreDAO {
    public List<Azienda> daConvenzionare();
    public List<Azienda> convenzionate();
    
}
