package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void createDatabaseIfNotExists() {
        // Extract base URL and database name from DBConnection
        String dbUrlBase = DBConnection.getDbUrlBase(); // Method to get DB_URL_BASE
        String databaseName = DBConnection.getDatabaseName(); // Method to get DATABASE_NAME
        String username = DBConnection.getUsername(); // Reuse the loaded username
        String password = DBConnection.getPassword(); // Reuse the loaded password

        // Validate configuration
        if (dbUrlBase == null || databaseName == null || username == null || password == null) {
            System.err.println("Missing required configuration. Please check the .env file.");
            return;
        }

        // Connect to the MySQL server and create the database if it doesn't exist
        try (Connection connection = DBConnection.getRawConnection(dbUrlBase, username, password);
             Statement statement = connection.createStatement()) {

            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            statement.executeUpdate(createDatabaseSQL);
            System.out.println("Database '" + databaseName + "' has been created or already exists.");

        } catch (SQLException e) {
            System.err.println("Error initializing the database: " + e.getMessage());
        }
    }
}
