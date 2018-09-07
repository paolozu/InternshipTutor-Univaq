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
public class Resoconto {
    private long id;
    private String nome,directory,estensione;
    private long peso;
    private String attivitaSvolta, risultatoConseguito;
    private int oreSvolte, valutazione;
    
    public Resoconto(long idResoconto) {
        this.id=idResoconto;
    }

    public Resoconto(long idResoconto,String nome, int valutazione) {
        this.id=idResoconto;
        this.nome=nome;
        this.valutazione=valutazione;
    }

    public Resoconto(String nome, String directory, String estensione, long peso) {
        this.nome = nome;
        this.directory = directory;
        this.estensione = estensione;
        this.peso = peso;
    }
    
    public Resoconto(int oreSvolte, String attivitaSvolta, String risultatoConseguito) {
        this.oreSvolte = oreSvolte;
        this.attivitaSvolta = attivitaSvolta;
        this.risultatoConseguito = risultatoConseguito;
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

    
    
    public long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getEstensione() {
        return estensione;
    }

    public void setEstensione(String estensione) {
        this.estensione = estensione;
    }

    public long getPeso() {
        return peso;
    }

    public void setPeso(long peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "Resoconto{" + "id=" + id + ", nome=" + nome + ", directory=" + directory + ", estensione=" + estensione + ", peso=" + peso + '}';
    }
}
