package db;

import models.Producte;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager implements Serializable {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public ProductManager() {
        // Constructor can be empty as we rely on DBConnection for configuration
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    // Crear un nou producte
    public void createProduct(Producte producte) throws SQLException {
        String sql = "INSERT INTO productes (nom, preu) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, producte.getNom());
            stmt.setDouble(2, producte.getPreu());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    int oldId = producte.getId();
                    producte.setId(keys.getInt(1));
                    pcs.firePropertyChange("productCreated", oldId, producte);
                }
            }
        }
    }

    // Llegir tots els productes
    public List<Producte> readAllProducts() throws SQLException {
        List<Producte> products = new ArrayList<>();
        String sql = "SELECT * FROM productes";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producte producte = new Producte(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("preu")
                );
                products.add(producte);
            }
        }
        return products;
    }

    // Actualitzar un producte
    public void updateProduct(Producte producte) throws SQLException {
        String sql = "UPDATE productes SET nom = ?, preu = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producte.getNom());
            stmt.setDouble(2, producte.getPreu());
            stmt.setInt(3, producte.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                pcs.firePropertyChange("productUpdated", null, producte);
            }
        }
    }

    // Esborrar un producte
    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM productes WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                pcs.firePropertyChange("productDeleted", id, null);
            }
        }
    }
}
