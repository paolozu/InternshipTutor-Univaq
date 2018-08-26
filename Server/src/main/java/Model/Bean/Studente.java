/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Bean;
import java.time.LocalDate;
/**
 *
 * @author lorenzo
 */
public class Studente extends Utente {
    
    private String nome, cognome, codFiscale, telefono, indirizzoResidenza, corsoLaurea, diploma,laurea,dottorato;
    private String capNascita,capResidenza,cittaResidenza,provinciaResidenza, cittaNascita, provinciaNascita;
    private int crediti;
    private LocalDate dataNascita;
    private boolean handicap;
    

    public Studente() {
    }

    public Studente(String username, String password, String email, String tipo,String nome, String cognome, String codFiscale, String telefono, String indirizzoResidenza, String corsoLaurea, String diploma, String laurea, String dottorato, String capNascita, String capResidenza, String cittaResidenza, String provinciaResidenza, String cittaNascita, String provinciaNascita, int crediti, LocalDate dataNascita, boolean handicap) {
        this.username=username;
        this.password=password;
        this.email=email;
        this.tipo=tipo;
        this.nome = nome;
        this.cognome = cognome;
        this.codFiscale = codFiscale;
        this.telefono = telefono;
        this.indirizzoResidenza = indirizzoResidenza;
        this.corsoLaurea = corsoLaurea;
        this.diploma = diploma;
        this.laurea = laurea;
        this.dottorato = dottorato;
        this.capNascita = capNascita;
        this.capResidenza = capResidenza;
        this.cittaResidenza = cittaResidenza;
        this.provinciaResidenza = provinciaResidenza;
        this.cittaNascita = cittaNascita;
        this.provinciaNascita = provinciaNascita;
        this.crediti = crediti;
        this.dataNascita = dataNascita;
        this.handicap = handicap;
    }
    
    
        
    public Studente(long id,String nome, String cognome,String email) {
        super.id=id;
        this.nome = nome;
        this.cognome = cognome;
        super.email = email;
    }

    public Studente(long id) {
        super.id=id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCrediti(int crediti) {
        this.crediti = crediti;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public void setIndirizzoResidenza(String indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public void setCorsoLaurea(String corsoLaurea) {
        this.corsoLaurea = corsoLaurea;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public void setLaurea(String laurea) {
        this.laurea = laurea;
    }

    public void setDottorato(String dottorato) {
        this.dottorato = dottorato;
    }

    public void setHandicap(boolean handicap) {
        this.handicap = handicap;
    }

    public void setCapNascita(String capNascita) {
        this.capNascita = capNascita;
    }

    public void setCapResidenza(String capResidenza) {
        this.capResidenza = capResidenza;
    }

    public void setCittaResidenza(String cittaResidenza) {
        this.cittaResidenza = cittaResidenza;
    }

    public void setProvinciaResidenza(String provinciaResidenza) {
        this.provinciaResidenza = provinciaResidenza;
    }

    public void setCittaNascita(String cittaNascita) {
        this.cittaNascita = cittaNascita;
    }

    public void setProvinciaNascita(String provinciaNascita) {
        this.provinciaNascita = provinciaNascita;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public String getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    public String getCorsoLaurea() {
        return corsoLaurea;
    }

    public String getDiploma() {
        return diploma;
    }

    public String getLaurea() {
        return laurea;
    }

    public String getDottorato() {
        return dottorato;
    }

    public boolean isHandicap() {
        return handicap;
    }

    public String getCapNascita() {
        return capNascita;
    }

    public String getCapResidenza() {
        return capResidenza;
    }

    public String getCittaResidenza() {
        return cittaResidenza;
    }

    public String getProvinciaResidenza() {
        return provinciaResidenza;
    }

    public String getCittaNascita() {
        return cittaNascita;
    }

    public String getProvinciaNascita() {
        return provinciaNascita;
    }
    
    public int getCrediti() {
        return crediti;
    }
    


    
  
}
