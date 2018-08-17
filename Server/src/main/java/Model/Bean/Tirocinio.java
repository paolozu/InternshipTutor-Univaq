<<<<<<< HEAD
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
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Bean;

import java.util.Date;

/**
 *
 * @author Paolo
 */
public class Tirocinio {
    
    private int oreSvolte, valutazione, idAnnuncio, idResoconto;
    private Date dataInizio, dataFine;
    private String attivitaSvolta, risultatoConseguito;
    private Studente studente;

    public int getOreSvolte() {
        return oreSvolte;
    }

    public void setOreSvolte(int oreSvolte) {
        this.oreSvolte = oreSvolte;
    }

    public int getValutazione() {
        return valutazione;
    }

    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    public int getIdAnnuncio() {
        return idAnnuncio;
    }

    public void setIdAnnuncio(int idAnnuncio) {
        this.idAnnuncio = idAnnuncio;
    }

    public int getIdResoconto() {
        return idResoconto;
    }

    public void setIdResoconto(int idResoconto) {
        this.idResoconto = idResoconto;
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

    public String getAttivitaSvolta() {
        return attivitaSvolta;
    }

    public void setAttivitaSvolta(String attivitaSvolta) {
        this.attivitaSvolta = attivitaSvolta;
    }

    public String getRisultatoConseguito() {
        return risultatoConseguito;
    }

    public void setRisultatoConseguito(String risultatoConseguito) {
        this.risultatoConseguito = risultatoConseguito;
    }

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }
    
    
}
>>>>>>> bddf4711a2f55570bd8ba7ed28947e718fadb578
