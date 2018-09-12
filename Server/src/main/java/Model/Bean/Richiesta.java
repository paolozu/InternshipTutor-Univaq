/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Bean;

/**
 *
 * @author lorenzo
 */
public class Richiesta {
    private Annuncio annuncio;
    private Studente studente;
    private String nome,cognome;
    private int crediti;
    
    

    public Richiesta(Annuncio annuncio, Studente studente) {
        this.annuncio = annuncio;
        this.studente = studente;
    }
    
    public Richiesta(Annuncio annuncio, Studente studente, String nome, String cognome, int crediti) {
        this.annuncio = annuncio;
        this.studente = studente;
        this.nome=nome;
        this.cognome=cognome;
        this.crediti=crediti;
    }

    public Annuncio getAnnuncio() {
        return annuncio;
    }

    public void setAnnuncio(Annuncio annuncio) {
        this.annuncio = annuncio;
    }

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getCrediti() {
        return crediti;
    }

    public void setCrediti(int crediti) {
        this.crediti = crediti;
    }
    
    
    
}
