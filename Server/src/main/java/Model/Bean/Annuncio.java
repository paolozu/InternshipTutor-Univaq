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
    
    private int id, id_referente;
    private String titolo,corpo, modalita, settore, sussidio;
    private Date dataAvvio, dataTermine;
    private Azienda azienda;
    private Tutore tutore;

    public Annuncio(int id, String titolo, String corpo, Date dataAvvio, Date dataTermine, String modalita, String settore, String sussidio, Azienda azienda, int id_referente) {
        this.id = id;
        this.titolo = titolo;
        this.corpo = corpo;
        this.modalita = modalita;
        this.settore = settore;
        this.sussidio = sussidio;
        this.dataAvvio = dataAvvio;
        this.dataTermine = dataTermine;
        this.azienda = azienda;
        this.id_referente = id_referente;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Annuncio{" + "id=" + id + ", titolo=" + titolo + ", corpo=" + corpo + ", modalita=" + modalita + ", settore=" + settore + ", sussidio=" + sussidio + ", dataAvvio=" + dataAvvio + ", dataTermine=" + dataTermine + ", azienda=" + azienda + ", tutore=" + tutore + '}';
    }

    public Annuncio(int id, String titolo, String corpo, Date dataAvvio, Date dataTermine, String modalita, String settore, String sussidio, Azienda azienda, Tutore tutore, int id_referente) {
        this.id = id;
        this.titolo = titolo;
        this.corpo = corpo;
        this.modalita = modalita;
        this.settore = settore;
        this.sussidio = sussidio;
        this.dataAvvio = dataAvvio;
        this.dataTermine = dataTermine;
        this.azienda = azienda;
        this.tutore = tutore;
        this.id_referente = id_referente;

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

    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
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
