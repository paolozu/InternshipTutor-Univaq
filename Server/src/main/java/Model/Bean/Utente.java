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
public class Utente {

    protected long id;
    protected String username, password, email,tipo;

    public Utente(){}
    
    public Utente(String username, String password) {
        this.username=username;
        this.password=password;
    }
    
    public Utente(String username, String password, String email, String tipo) {
        this.username=username;
        this.password=password;
        this.email=email;
        this.tipo=tipo;
    }

    public Utente(long id, String username, String email, String tipo ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setId(Long id){
        this.id=id;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getId() {
        return id;
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

}
