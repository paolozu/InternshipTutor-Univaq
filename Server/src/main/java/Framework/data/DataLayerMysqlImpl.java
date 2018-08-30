package Framework.data;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Giuseppe Della Penna
 */
public class DataLayerMysqlImpl implements DataLayer {

    protected DataSource datasource;
    protected Connection connection;

    public DataLayerMysqlImpl(DataSource datasource) throws SQLException, NamingException {
        super();
        this.datasource = datasource;
        this.connection = null;
    }

    @Override
    public void init() throws DataLayerException {
        try {
            //connessione al database locale
            //database connection
            connection = datasource.getConnection();
        } catch (SQLException ex) {
            throw new DataLayerException("Error initializing data layer", ex);
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException ex) {
            //
        }
    }

    @Override
    //metodo dell'interfaccia AutoCloseable (permette di usare questa classe nei try-with-resources)
    //method from the Autocloseable interface (allows this class to be used in try-with-resources)
    public void close() throws Exception {
        destroy();
    }
}
