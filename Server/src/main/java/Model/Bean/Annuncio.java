/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Bean;

import java.util.Date;

/**
 *
 * @author lorenzo
 */
public class Annuncio {
    
    private int id;
    private String titolo,corpo, modalita, setotre, sussidio;
    private Date dataAvvio, dataTermine;

    public int getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getModalita() {
        return modalita;
    }

    public void setModalita(String modalita) {
        this.modalita = modalita;
    }

    public String getSetotre() {
        return setotre;
    }

    public void setSetotre(String setotre) {
        this.setotre = setotre;
    }

    public String getSussidio() {
        return sussidio;
    }

    public void setSussidio(String sussidio) {
        this.sussidio = sussidio;
    }

    public Date getDataAvvio() {
        return dataAvvio;
    }

    public void setDataAvvio(Date dataAvvio) {
        this.dataAvvio = dataAvvio;
    }

    public Date getDataTermine() {
        return dataTermine;
    }

    public void setDataTermine(Date dataTermine) {
        this.dataTermine = dataTermine;
    }
    
    
            
           
}
