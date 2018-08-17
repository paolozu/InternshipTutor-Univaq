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
public class Convenzione {
    private int id;
    private String nome,directory,estensione,peso,durataConvezione;
    private Date dataConvezione;

    public int getId() {
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

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getDurataConvezione() {
        return durataConvezione;
    }

    public void setDurataConvezione(String durataConvezione) {
        this.durataConvezione = durataConvezione;
    }

    public Date getDataConvezione() {
        return dataConvezione;
    }

    public void setDataConvezione(Date dataConvezione) {
        this.dataConvezione = dataConvezione;
    }
    
    
}
