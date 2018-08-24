/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Bean;

import java.util.List;

/**
 *
 * @author Paolo
 */
public class Azienda extends Utente {
    
    private String cap,nomeRappresentante, cognomeRappresentante, ragioneSociale,
            indirizzoSede, partitaIva, foroCompetente, telResponsabile,nomeResponsabile,cognomeResponsabile, citta, provincia;
    boolean abilitata;
    Convenzione convenzione;
    private List richiesteStudenti;
    private List tirocinanti;

    public Azienda(int id, String ragioneSociale) {
        super.id = id;
        this.ragioneSociale = ragioneSociale;
    }
    
    public Azienda(int id) {
        super.id = id;
    }

    public Azienda(int idAzienda, String nomeRappresentante, String cognomeRappresentante, String telResponsabile, String nomeResponsabile, String cognomeResponsabile, String emailResponsabile, String ragioneSociale, String indirizzoSede, String partitaIva, String foroCompetente, String cap, String citta, String provincia, Convenzione convenzione ) {
        super.id = id;
        this.cap = cap;
        this.nomeRappresentante = nomeRappresentante;
        this.cognomeRappresentante = cognomeRappresentante;
        this.ragioneSociale = ragioneSociale;
        this.indirizzoSede = indirizzoSede;
        this.partitaIva = partitaIva;
        this.foroCompetente = foroCompetente;
        this.telResponsabile = telResponsabile;
        this.citta = citta;
        this.provincia = provincia;
        this.convenzione = convenzione;
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
    
    public Convenzione getConvenzione() {
        return convenzione;
    }

    public void setConvenzione(Convenzione convenzione) {
        this.convenzione = convenzione;
    }

    public List getRichiesteStudenti() {
        return richiesteStudenti;
    }

    public void setRichiesteStudenti(List richiesteStudenti) {
        this.richiesteStudenti = richiesteStudenti;
    }

    public List getTirocinanti() {
        return tirocinanti;
    }

    public void setTirocinanti(List tirocinanti) {
        this.tirocinanti = tirocinanti;
    }
    
    public void setCitta(String citta) {
        this.citta = citta;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCitta() {
        return citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCAP() {
        return cap;
    }


    public String getNomeRappresentante() {
        return nomeRappresentante;
    }

    public String getCognomeRappresentante() {
        return cognomeRappresentante;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public String getIndirizzoSede() {
        return indirizzoSede;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public String getForoCompetente() {
        return foroCompetente;
    }


    public boolean isAbilitata() {
        return abilitata;
    }


    public void setCAP(String cap) {
        this.cap = cap;
    }


    public void setNomeRappresentante(String nomeRappresentante) {
        this.nomeRappresentante = nomeRappresentante;
    }

    public void setCognomeRappresentante(String cognomeRappresentante) {
        this.cognomeRappresentante = cognomeRappresentante;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public void setIndirizzoSede(String indirizzoSede) {
        this.indirizzoSede = indirizzoSede;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public void setForoCompetente(String foroCompetente) {
        this.foroCompetente = foroCompetente;
    }


    public void setAbilitata(boolean abilitata) {
        this.abilitata = abilitata;
    }

    @Override
    public String toString() {
        return "Azienda{" + "cap=" + cap + ", nomeRappresentante=" + nomeRappresentante + ", cognomeRappresentante=" + cognomeRappresentante + ", ragioneSociale=" + ragioneSociale + ", indirizzoSede=" + indirizzoSede + ", partitaIva=" + partitaIva + ", foroCompetente=" + foroCompetente + ", telResponsabile=" + telResponsabile + ", nomeResponsabile=" + nomeResponsabile + ", cognomeResponsabile=" + cognomeResponsabile + ", citta=" + citta + ", provincia=" + provincia + ", abilitata=" + abilitata + ", convenzione=" + convenzione + ", richiesteStudenti=" + richiesteStudenti + ", tirocinanti=" + tirocinanti + '}';
    }
    
    
}
