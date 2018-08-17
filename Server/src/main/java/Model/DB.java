/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author lorenzo
 */
public class DB {

    Connection conn;

    public DB() {

         InitialContext ctx;
         try {
             ctx = new InitialContext();
             DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/webdb2");
         } catch (NamingException ex) {
             Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public  Connection getConnection() throws NamingException, SQLException   {

   
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/webdb2");
            
            conn = ds.getConnection();
            
            return conn;

    }
    
    public void closeCon() throws SQLException {
        conn.close();
    }

    public Connection getConnectionOld() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webdb", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
