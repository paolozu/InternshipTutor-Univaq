/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Framework.security;

/**
 *
 * @author lorenzo
 */
public class SecurityLayerException extends Exception {


    public SecurityLayerException(String message) {
        super(message);
    }

    public SecurityLayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityLayerException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + (getCause()!=null?" ("+getCause().getMessage()+")":"");                
    }

    
}
