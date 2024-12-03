package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static String DB_URL_BASE;
    private static String DATABASE_NAME;
    private static String USERNAME;
    private static String PASSWORD;
    private static String DB_URL; // Full database URL

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(".env")) {
            if (input == null) {
                throw new IOException("No s'ha pogut trobar el fitxer .env");
            }

            Properties props = new Properties();
            props.load(input);

            DB_URL_BASE = props.getProperty("DB_URL_BASE");
            DATABASE_NAME = props.getProperty("DATABASE_NAME");
            DB_URL = DB_URL_BASE + DATABASE_NAME;

            USERNAME = props.getProperty("USERNAME");
            PASSWORD = props.getProperty("PASSWORD");
        } catch (IOException e) {
            System.err.println("Error carregant el fitxer .env: " + e.getMessage());
        }
    }

    public static String getDbUrlBase() {
        return DB_URL_BASE;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    /**
     * Provides a Connection object to the configured database.
     *
     * @return a Connection object.
     * @throws SQLException if the connection fails.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no trobat", e);
        }

        // Establish and return a connection
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    public static Connection getRawConnection(String dbUrlBase, String username, String password) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no trobat", e);
        }
        return DriverManager.getConnection(dbUrlBase, username, password);
    }
}
