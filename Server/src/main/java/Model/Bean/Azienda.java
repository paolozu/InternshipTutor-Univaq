/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Bean;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Paolo
 */
public class Azienda extends Utente {
    
<<<<<<< HEAD
    private String cap,nomeRappresentante, cognomeRappresentante, ragioneSociale,
            indirizzoSede, partitaIva, foroCompetente, telResponsabile,nomeResponsabile,cognomeResponsabile,emailResponsabile, citta, provincia;
    LocalDate dataIscrione, dataTermine;
=======
    private String cap, nomeRappresentante, cognomeRappresentante, ragioneSociale,
            indirizzoSede, partitaIva, foroCompetente, telResponsabile,nomeResponsabile,
            cognomeResponsabile,emailResponsabile, citta, provincia,
            telSede, telRappresentante, durataConvenzione, emailRappresentante;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDurataConvenzione() {
        return durataConvenzione;
    }

    public void setDurataConvenzione(String durataConvenzione) {
        this.durataConvenzione = durataConvenzione;
    }

    public String getEmailRappresentante() {
        return emailRappresentante;
    }

    public void setEmailRappresentante(String emailRappresentante) {
        this.emailRappresentante = emailRappresentante;
    }

    public String getTelRappresentante() {
        return telRappresentante;
    }

    public void setTelRappresentante(String telRappresentante) {
        this.telRappresentante = telRappresentante;
    }

    public String getTelSede() {
        return telSede;
    }

    public void setTelSede(String telSede) {
        this.telSede = telSede;
    }
>>>>>>> 9ff2d2a39d75b00ca2e0a5e9d5e8e006cbc7a682
    
    public Azienda(){}
    
    public Azienda(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }
        
    public Azienda(long id, String ragioneSociale) {
        super.id = id;
        this.ragioneSociale = ragioneSociale;
    }
    
    public Azienda(long id) {
        super.id = id;
    }
    
    public Azienda(long idAzienda, String nomeRappresentante, String cognomeRappresentante, String telResponsabile, String nomeResponsabile, String cognomeResponsabile, String emailResponsabile, String ragioneSociale, String indirizzoSede, String partitaIva, String foroCompetente, String cap, String citta, String provincia ) {
        super.id = idAzienda;
        this.cap = cap;
        this.nomeResponsabile=nomeResponsabile;
        this.cognomeResponsabile=cognomeResponsabile;
        this.nomeRappresentante = nomeRappresentante;
        this.cognomeRappresentante = cognomeRappresentante;
        this.ragioneSociale = ragioneSociale;
        this.indirizzoSede = indirizzoSede;
        this.partitaIva = partitaIva;
        this.foroCompetente = foroCompetente;
        this.telResponsabile = telResponsabile;
        this.emailResponsabile = emailResponsabile;
        this.citta = citta;
        this.provincia = provincia;
    }

    public Azienda(String ragioneSociale, String indirizzoSede, String citta, String nomeResponsabile, String cognomeResponsabile, String emailResponsabile, String telResponsabile) {
        this.ragioneSociale = ragioneSociale;
        this.indirizzoSede = indirizzoSede;
        this.citta = citta;
        this.nomeResponsabile = nomeResponsabile;
        this.cognomeResponsabile = cognomeResponsabile;
        this.emailResponsabile = emailResponsabile;
        this.telResponsabile = telResponsabile;
    }
    
    

    public Azienda(String username, String password, String email, String tipo) {
        this.username=username;
        this.password=password;
        this.email=email;
        this.tipo=tipo;
    }

    public Azienda(String username, String password, String email, String tipo, String nomR, String cognR, String telResp, String nomeResp, String cognResp, String emailResp, String ragSoc, String ind, String piva, String foro, String cap, String citta, String pro) {
        this.username=username;
        this.password=password;
        this.email=email;
        this.tipo=tipo;
        this.cap = cap;
        this.nomeRappresentante = nomR;
        this.cognomeRappresentante = cognR;
        this.ragioneSociale = ragSoc;
        this.indirizzoSede = ind;
        this.partitaIva = piva;
        this.foroCompetente = foro;
        this.telResponsabile = telResp;
        this.emailResponsabile = emailResp;
        this.nomeResponsabile = nomeResp;
        this.cognomeResponsabile = cognResp;
        this.citta = citta;
        this.provincia = pro;
    }

    public LocalDate getDataIscrione() {
        return dataIscrione;
    }

    public void setDataIscrione(LocalDate dataIscrione) {
        this.dataIscrione = dataIscrione;
    }

    public LocalDate getDataTermine() {
        return dataTermine;
    }

    public void setDataTermine(LocalDate dataTermine) {
        this.dataTermine = dataTermine;
    }
    
    
    
    public void setCap(String cap) {
        this.cap=cap;
    }
    
    public String getCap() {
        return cap;
    }
    
    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getNomeRappresentante() {
        return nomeRappresentante;
    }

    public void setNomeRappresentante(String nomeRappresentante) {
        this.nomeRappresentante = nomeRappresentante;
    }

    public String getCognomeRappresentante() {
        return cognomeRappresentante;
    }

    public void setCognomeRappresentante(String cognomeRappresentante) {
        this.cognomeRappresentante = cognomeRappresentante;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public String getIndirizzoSede() {
        return indirizzoSede;
    }

    public void setIndirizzoSede(String indirizzoSede) {
        this.indirizzoSede = indirizzoSede;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getForoCompetente() {
        return foroCompetente;
    }

    public void setForoCompetente(String foroCompetente) {
        this.foroCompetente = foroCompetente;
    }

    public String getTelResponsabile() {
        return telResponsabile;
    }

    public void setTelResponsabile(String telResponsabile) {
        this.telResponsabile = telResponsabile;
    }

    public String getNomeResponsabile() {
        return nomeResponsabile;
    }

    public void setNomeResponsabile(String nomeResponsabile) {
        this.nomeResponsabile = nomeResponsabile;
    }

    public String getCognomeResponsabile() {
        return cognomeResponsabile;
    }

    public void setCognomeResponsabile(String cognomeResponsabile) {
        this.cognomeResponsabile = cognomeResponsabile;
    }

    public String getEmailResponsabile() {
        return emailResponsabile;
    }

    public void setEmailResponsabile(String emailResponsabile) {
        this.emailResponsabile = emailResponsabile;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Azienda{" + "cap=" + cap + ", nomeRappresentante=" + nomeRappresentante + ", cognomeRappresentante=" + cognomeRappresentante + ", ragioneSociale=" + ragioneSociale + ", indirizzoSede=" + indirizzoSede + ", partitaIva=" + partitaIva + ", foroCompetente=" + foroCompetente + ", telResponsabile=" + telResponsabile + ", nomeResponsabile=" + nomeResponsabile + ", cognomeResponsabile=" + cognomeResponsabile + ", emailResponsabile=" + emailResponsabile + ", citta=" + citta + ", provincia=" + provincia + '}';
    }


    
    
}
