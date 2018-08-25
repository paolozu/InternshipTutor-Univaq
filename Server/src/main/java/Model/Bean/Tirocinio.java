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
    
    private Annuncio annuncio;
    private LocalDate dataInizio, dataFine;

    private Studente studente;
    private Resoconto resoconto;
    
    public Tirocinio(Resoconto resoconto) {
        this.resoconto = resoconto;
    }

    public Tirocinio(LocalDate dataInizio, LocalDate dataFine) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public Tirocinio(Resoconto resoconto, Annuncio annuncio, LocalDate dataInizio, LocalDate dataFine) {
       this.resoconto=resoconto;
       this.annuncio=annuncio;
       this.dataInizio=dataInizio;
       this.dataFine = dataFine;
    }

    public Tirocinio(Studente s, Annuncio an, Resoconto r) {
        this.studente=s;
        this.annuncio=an;
        this.resoconto=r;
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

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }
    
}
