package de.unidue.inf.is.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.ibm.db2.jcc.DB2Driver;


public final class DBUtil {
	
    private DBUtil() {
    }

    
    static {
        com.ibm.db2.jcc.DB2Driver driver = new DB2Driver();
        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            throw new Error("Laden des Datenbanktreiber nicht möglich");
        }
    }


    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        

        InputStream input = null;
        try {
            input = new FileInputStream("settings.properties");
            System.out.println("trying to connect !! ");
            

            // Zugangsdaten aus der Properties-Datei lesen
            properties.load(input);
            System.out.println(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("we have connection problems");
        }

        String database = properties.getProperty("database");
        final String url = "jdbc:db2:" + database;
        System.out.println(url);
        return DriverManager.getConnection(url);
    }


    // Diese Methode benutzen, um sich von außerhalb der Uni mit der DB zu verbinden
    public static Connection getExternalConnection() throws SQLException {
        Properties properties = new Properties();

        InputStream input = null;
        try {
            input = new FileInputStream("settings.properties");

            // Zugangsdaten aus der Properties-Datei lesen
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String user = properties.getProperty("gruppenname");
        String pass = properties.getProperty("passwort");
        String rechnername = properties.getProperty("rechnername");
        String database = properties.getProperty("database");

        String gruppennummer = user.split("(?<=\\D)(?=\\d)")[1];

        final String url = "jdbc:db2://" + rechnername + ".is.inf.uni-due.de:50" + gruppennummer + "/" + database + ":currentSchema=" + user + ";";
        System.out.println("The Url :"+url);
        System.out.println("The User: "+user);
        
        Connection connection = DriverManager.getConnection(url, user, pass);
        System.out.println("the connection --"+connection.toString());
        return connection;
    }


    public static boolean checkDatabaseExistsExternal() {
        // Nur für Demozwecke!
        boolean exists = false;

        try (Connection connection = getExternalConnection()) {
            exists = true;
        } catch (SQLException e) {
            exists = false;
            e.printStackTrace();
        }

        return exists;
    }


    public static boolean checkDatabaseExists() {
        // Nur für Demozwecke!
        boolean exists = false;

        try (Connection connection = getConnection()) {
            exists = true;
        } catch (SQLException e) {
            exists = false;
            e.printStackTrace();
        }

        return exists;
    }



}