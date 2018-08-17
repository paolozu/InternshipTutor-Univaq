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
public class Tirocinio {
    
    private int id;
    private Date dataInizio, dataFine;
    private int oreSvolte;
    private String attivitaSvolta,risConseguito,valutazione;

    public int getId() {
        return id;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public int getOreSvolte() {
        return oreSvolte;
    }

    public void setOreSvolte(int oreSvolte) {
        this.oreSvolte = oreSvolte;
    }

    public String getAttivitaSvolta() {
        return attivitaSvolta;
    }

    public void setAttivitaSvolta(String attivitaSvolta) {
        this.attivitaSvolta = attivitaSvolta;
    }

    public String getRisConseguito() {
        return risConseguito;
    }

    public void setRisConseguito(String risConseguito) {
        this.risConseguito = risConseguito;
    }

    public String getValutazione() {
        return valutazione;
    }

    public void setValutazione(String valutazione) {
        this.valutazione = valutazione;
    }
    
}
