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
    
    private String nome, cognome, codFiscale, telefono, crediti;
    private LocalDate dataNascita;
    private String indirizzoResidenza, corsoLaurea, diploma,laurea,dottorato, settore;
    private boolean handicap;
    private String capNascita,capResidenza,cittaResidenza,provinciaResidenza, cittaNascita, provinciaNascita;
    

    public Studente() {
    }
        
    public Studente(int id,String nome, String cognome,String email) {
        super.id=id;
        this.nome = nome;
        this.cognome = cognome;
        super.email = email;
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

    public void setCrediti(String crediti) {
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

    public void setSettore(String settore) {
        this.settore = settore;
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

    public String getCrediti() {
        return crediti;
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

    public String getSettore() {
        return settore;
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
    


    
  
}
