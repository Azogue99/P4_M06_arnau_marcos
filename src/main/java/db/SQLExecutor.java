package db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExecutor {
    public static void executeSQLFile(String filePath) {
        try (InputStream inputStream = SQLExecutor.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Fitxer no trobat: " + filePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                 Connection connection = DBConnection.getConnection();
                 Statement statement = connection.createStatement()) {

                StringBuilder sql = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sql.append(line);
                }

                statement.execute(sql.toString());
                System.out.println("Fitxer SQL executat correctament: " + filePath);

            } catch (SQLException e) {
                System.err.println("Error executant el fitxer SQL: " + e.getMessage());
                throw e;
            }
        } catch (Exception e) {
            System.err.println("Error llegint el fitxer SQL: " + e.getMessage());
        }
    }
}
