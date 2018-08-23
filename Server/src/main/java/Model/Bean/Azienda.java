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
    
    private int CAP, idConvenzione;
    private String nomeRappresentante, cognomeRappresentante, ragioneSociale,
            indirizzoSede, partitaIva, foroCompetente, telefono, citta, provincia;
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

    public int getCAP() {
        return CAP;
    }

    public int getIdConvenzione() {
        return idConvenzione;
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

    public String getTelefono() {
        return telefono;
    }

    public boolean isAbilitata() {
        return abilitata;
    }


    public void setCAP(int CAP) {
        this.CAP = CAP;
    }

    public void setIdConvenzione(int idConvenzione) {
        this.idConvenzione = idConvenzione;
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

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setAbilitata(boolean abilitata) {
        this.abilitata = abilitata;
    }
    
    
}
