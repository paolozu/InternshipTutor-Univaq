/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author lorenzo
 */
public class DB {

    private Connection conn;

    public DB() {

         InitialContext ctx;
         try {
             ctx = new InitialContext();
             DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/webdb");
         } catch (NamingException ex) {
             Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public  Connection getConnection() throws NamingException, SQLException   {

   
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/webdb");
            
            conn = ds.getConnection();
            
            return conn;
    }
    
    public void closeCon() throws SQLException {
        conn.close();
    }
    
}
