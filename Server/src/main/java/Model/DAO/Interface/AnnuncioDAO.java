/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Interface;

import Model.Bean.Annuncio;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author lorenzo
 */
public interface AnnuncioDAO {
    
    public Annuncio getAnnuncioById(int id);
    public List<Annuncio> getAnnunci();
    public void setAnnuncio(String titolo, String corpo, LocalDate dataAvvio, LocalDate dataTermine, String modalita, String sussidio, String settore, int idA, int idT);
}
