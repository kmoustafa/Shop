/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kareem.Moustafa
 */
public class DBHandler  {

    private DBHandler() {
        try {
            Class.forName(DRIVERCLASS);
            this.dbURL = "jdbc:oracle:thin:@localhost:1521:XE";
            this.dbPassword = "shop";
            this.dbUserName = "SHOPDB";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public static Connection connect() {
        return instance.createConnection();

    }


    private static DBHandler instance = new DBHandler();
    private String dbUserName;
    private String dbPassword;
    private String dbURL;
    private static final String DRIVERCLASS = "oracle.jdbc.OracleDriver";
}
