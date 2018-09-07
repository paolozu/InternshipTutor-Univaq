/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Bean;

import java.time.LocalDate;
import java.util.Date;
import javax.servlet.http.Part;

/**
 *
 * @author lorenzo
 */
public class Convenzione {
    private long id;
    private int durataConvezione;
    private long peso;
    private String nome,estensione;
    Part file;
    private LocalDate dataConvezione;

    public Convenzione(String nome, Part file, String estensione, long peso) {
        this.peso = peso;
        this.nome = nome;
        this.file=file;
        this.estensione = estensione;
    }

    public Convenzione(int durataConvezione, LocalDate dataConvezione) {
        this.durataConvezione = durataConvezione;
        this.dataConvezione = dataConvezione;
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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
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

    public int getDurataConvezione() {
        return durataConvezione;
    }

    public void setDurataConvezione(int durataConvezione) {
        this.durataConvezione = durataConvezione;
    }

    public LocalDate getDataConvezione() {
        return dataConvezione;
    }

    public void setDataConvezione(LocalDate dataConvezione) {
        this.dataConvezione = dataConvezione;
    }
    
    
}
