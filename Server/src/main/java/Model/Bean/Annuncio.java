/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Bean;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author lorenzo
 */
public class Annuncio {
    
    private long id;
    private String titolo,corpo, modalita, settore, sussidio;
    private LocalDate dataAvvio, dataTermine;
    private Azienda azienda;
    private Docente docente;
    private Referente referente;


    public Annuncio(String titolo, String corpo, LocalDate dataAvvio, LocalDate dataTermine, String modalita, String settore, String sussidio, Azienda azienda, Docente docente, Referente referente) {
        this.titolo = titolo;
        this.corpo = corpo;
        this.modalita = modalita;
        this.settore = settore;
        this.sussidio = sussidio;
        this.dataAvvio = dataAvvio;
        this.dataTermine = dataTermine;
        this.azienda = azienda;
        this.docente = docente;
        this.referente = referente;
    }
    
    public Annuncio(long id, String titolo, String corpo, LocalDate dataAvvio, LocalDate dataTermine, String modalita, String settore, String sussidio, Azienda azienda, Docente docente, Referente referente) {
        this.id = id;
        this.titolo = titolo;
        this.corpo = corpo;
        this.modalita = modalita;
        this.settore = settore;
        this.sussidio = sussidio;
        this.dataAvvio = dataAvvio;
        this.dataTermine = dataTermine;
        this.azienda = azienda;
        this.docente = docente;
        this.referente = referente;
    }
        
    public Annuncio(long id, String titolo, String corpo, LocalDate dataAvvio, LocalDate dataTermine, String modalita, String settore, String sussidio, Azienda azienda) {
        this.id = id;
        this.titolo = titolo;
        this.corpo = corpo;
        this.modalita = modalita;
        this.settore = settore;
        this.sussidio = sussidio;
        this.dataAvvio = dataAvvio;
        this.dataTermine = dataTermine;
        this.azienda = azienda;
    }

    public Annuncio(Azienda aziendaAnnuncio, Docente docenteAnnuncio){
    this.azienda=aziendaAnnuncio;
    this.docente=docenteAnnuncio;
    }


    public Annuncio(long id) {
        this.id=id;
    }
    
    public Annuncio(Azienda azienda) {
        this.azienda=azienda;
    }

    public Annuncio() {
       
    }
    
    
    public long getId() {
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

    public LocalDate getDataAvvio() {
        return dataAvvio;
    }

    public void setDataAvvio(LocalDate dataAvvio) {
        this.dataAvvio = dataAvvio;
    }

    public LocalDate getDataTermine() {
        return dataTermine;
    }

    public void setDataTermine(LocalDate dataTermine) {
        this.dataTermine = dataTermine;
    }
    
    
    public Azienda getAzienda() {
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        this.azienda = azienda;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Referente getReferente() {
        return referente;
    }

    public void setReferente(Referente referente) {
        this.referente = referente;
    }

    @Override
    public String toString() {
        return "Annuncio{" + "id=" + id + ", titolo=" + titolo + ", corpo=" + corpo + ", modalita=" + modalita + ", settore=" + settore + ", sussidio=" + sussidio + ", dataAvvio=" + dataAvvio + ", dataTermine=" + dataTermine + ", azienda=" + azienda + ", docente=" + docente + ", referente=" + referente + '}';
    }
    
    
            
           
}
