package de.unidue.inf.is.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBUtil {
    
    private DBUtil() {
    }

    // Load Oracle JDBC Driver
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Oracle JDBC Driver Loaded Successfully");
        } catch (ClassNotFoundException e) {
            throw new Error("Unable to load Oracle JDBC Driver", e);
        }
    }

    
    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("settings.properties");
            System.out.println("Trying to connect using local properties file...");

            // Read database properties from the file
            properties.load(input);
        } catch (IOException ex) {
            System.out.println("Error reading properties file!");
            ex.printStackTrace();
            throw new SQLException("Failed to read settings.properties file", ex);
        }

        // Get database connection details from properties file
        String host = properties.getProperty("host", "localhost");
        String port = properties.getProperty("port", "1521");
        String serviceName = properties.getProperty("serviceName", "XE");
        String user = properties.getProperty("username");
        String password = properties.getProperty("password");

        // Construct Oracle JDBC connection URL
        final String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + serviceName;

        System.out.println("Connecting to: " + url);
        
        return DriverManager.getConnection(url, user, password);
    }

    
    public static Connection getExternalConnection() throws SQLException {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("settings.properties");
            properties.load(input);
        } catch (IOException ex) {
            System.out.println("Error loading properties file for external connection");
            ex.printStackTrace();
            throw new SQLException("Failed to read settings.properties file", ex);
        }

        String user = properties.getProperty("username");
        String password = properties.getProperty("password");
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String serviceName = properties.getProperty("serviceName");

        // Build Oracle DB connection string for remote server
        final String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + serviceName;

        System.out.println("Connecting to: " + url);
        System.out.println("User: " + user);
        
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connection established successfully: " + connection.toString());
        return connection;
    }

    
    public static boolean checkDatabaseExists() {
        boolean exists = false;

        try (Connection connection = getConnection()) {
            exists = true;
        } catch (SQLException e) {
            exists = false;
            e.printStackTrace();
        }

        return exists;
    }

    
    public static boolean checkDatabaseExistsExternal() {
        boolean exists = false;

        try (Connection connection = getExternalConnection()) {
            exists = true;
        } catch (SQLException e) {
            exists = false;
            e.printStackTrace();
        }

        return exists;
    }
}
