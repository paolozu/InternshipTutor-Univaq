
package Framework.data;

import Model.DAO.Impl.StudenteDAOImpl;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giuseppe Della Penna
 */
public class DataLayerException extends Exception {

    public DataLayerException(String message) {
        super(message);
    }

    public DataLayerException(String message, Throwable cause) {
        super(message, cause);
        Logger.getLogger(message).log(Level.SEVERE, null, cause);
    }

    public DataLayerException(Throwable cause) {
        super(cause);
        Logger.getLogger("").log(Level.SEVERE, null, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + (getCause()!=null?" ("+getCause().getMessage()+")":"");                
    }
}
