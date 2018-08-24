/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Bean;

import java.time.LocalDate;

/**
 *
 * @author Paolo
 */
public class Tirocinio {
    
    private int oreSvolte, valutazione;
    private Annuncio annuncio;
    private LocalDate dataInizio, dataFine;
    private String attivitaSvolta, risultatoConseguito;
    private Studente studente;
    private Resoconto resoconto;

    public Tirocinio(LocalDate dataInizio, LocalDate dataFine) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public Tirocinio(Annuncio annuncio, LocalDate dataInizio, LocalDate dataFine) {
       this.annuncio=annuncio;
       this.dataInizio=dataInizio;
       this.dataFine = dataFine;
    }

    
    
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

    public Annuncio getAnnuncio() {
        return annuncio;
    }

    public void setAnnuncio(Annuncio annuncio) {
        this.annuncio = annuncio;
    }

    public Resoconto getResoconto() {
        return resoconto;
    }

    public void setIdResoconto(Resoconto resoconto) {
        this.resoconto = resoconto;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
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
