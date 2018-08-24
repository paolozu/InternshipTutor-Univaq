/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Bean;

import java.awt.List;

/**
 *
 * @author lorenzo
 */
public class Tirocinante extends Studente{
    
    private List tirociniInCorso;
    private List tirociniSvolti;
    
    public List getTirocini() {
        return tirociniInCorso;
    }

    public void setTirocini(List tirociniInCorso) {
        this.tirociniInCorso = tirociniInCorso;
    }

    public List getTirociniSvolti() {
        return tirociniSvolti;
    }

    public void setTirociniSvolti(List tirociniSvolti) {
        this.tirociniSvolti = tirociniSvolti;
    }
    

    
}
