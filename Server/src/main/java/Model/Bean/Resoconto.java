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
    private int id;
    private String nome,directory,estensione;
    private long peso;

    public Resoconto(int idResoconto) {
        this.id=idResoconto;
    }


    public Resoconto(String nome, String directory, String estensione, long peso) {
        this.nome = nome;
        this.directory = directory;
        this.estensione = estensione;
        this.peso = peso;
    }

    
    
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
