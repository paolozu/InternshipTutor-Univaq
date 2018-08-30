/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Interface;

import Framework.data.DataLayerException;
import Model.Bean.Utente;

/**
 *
 * @author lorenzo
 */
public interface UtenteDAO {
    
    public Utente getCredenziali(String username, String password) throws DataLayerException;
    public Utente nuovoUtente(Utente utente) throws DataLayerException;
    public boolean getEmailEsistente(String email) throws DataLayerException;
    public boolean getUsernameEsistente(String username) throws DataLayerException;
}
