/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Bean;

import java.util.List;

/**
 *
 * @author lorenzo
 */
public class AziendaConvenzionata extends Azienda {
    
    private List richiesteStudenti;
    private List tirocinanti;
    
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
}
