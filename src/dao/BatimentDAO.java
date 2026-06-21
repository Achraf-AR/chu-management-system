package dao;

import models.Batiment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.Singleton;

public class BatimentDAO {
    private BatimentFactory batimentFactory;

    public BatimentDAO(BatimentFactory batimentFactory) {
        this.batimentFactory = batimentFactory;
    }

    public BatimentFactory getBatimentFactory() {
        return batimentFactory;
    }

    public List<Batiment> getAllBatiments() throws SQLException {
        List<Batiment> batiments = new ArrayList<>();
        String query = "SELECT * FROM batiments";

        try (Connection conn = Singleton.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                batiments.add(mapResultSetToBatiment(rs));
            }
        }
        return batiments;
    }

    public Batiment mapResultSetToBatiment(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String type = rs.getString("type");
        String taille = rs.getString("taille");
        String description = rs.getString("description");

        // Choisir la fabrique appropriée en fonction du type
        BatimentFactory factory = getFactoryForType(type);
        return factory.createBatiment(id, type, taille, description);
    }

    private BatimentFactory getFactoryForType(String type) {
        switch (type) {
            case "administration":
                return new AdministrationFactory();
            case "laboratoire":
                return new LaboratoireFactory();
            case "radiologie":
                return new RadiologieFactory();
            // Ajouter d'autres cas pour les autres types de bâtiments
            default:
                throw new IllegalArgumentException("Type de bâtiment non supporté : " + type);
        }
    }

    public Batiment getBatimentById(int id) throws SQLException {
        String query = "SELECT * FROM batiments WHERE id = ?";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToBatiment(rs);
            }
        }
        return null;
    }

    public void addBatiment(Batiment batiment) throws SQLException {
        String query = "INSERT INTO batiments (type, taille, description) VALUES (?, ?, ?)";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, batiment.getType());
            stmt.setString(2, batiment.getTaille());
            stmt.setString(3, batiment.getDescription());
            stmt.executeUpdate();
        }
    }

    public void updateBatiment(Batiment batiment) throws SQLException {
        String query = "UPDATE batiments SET type = ?, taille = ?, description = ? WHERE id = ?";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, batiment.getType());
            stmt.setString(2, batiment.getTaille());
            stmt.setString(3, batiment.getDescription());
            stmt.setInt(4, batiment.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteBatiment(int id) throws SQLException {
        String query = "DELETE FROM batiments WHERE id = ?";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}