package dao;

import database.Singleton;
import models.Medecin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedecinDAO {

    public List<Medecin> getAllMedecins() {
        List<Medecin> medecins = new ArrayList<>();
        String query = "SELECT * FROM medecins";

        try (Connection conn = Singleton.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                medecins.add(mapResultSetToMedecin(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des médecins", e);
        }
        return medecins;
    }

    public Medecin getMedecinById(int id) {
        String query = "SELECT * FROM medecins WHERE id = ?";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToMedecin(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du médecin avec ID : " + id, e);
        }
        return null;
    }

    public void addMedecin(Medecin medecin) {
        String query = "INSERT INTO medecins (nom, specialite, telephone, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, medecin.getNom());
            stmt.setString(2, medecin.getSpecialite());
            stmt.setString(3, medecin.getTelephone());
            stmt.setString(4, "chu");
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du médecin", e);
        }
    }

    public void updateMedecin(Medecin medecin) {
        String query = "UPDATE medecins SET nom = ?, specialite = ?, telephone = ? WHERE id = ?";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, medecin.getNom());
            stmt.setString(2, medecin.getSpecialite());
            stmt.setString(3, medecin.getTelephone());
            stmt.setInt(4, medecin.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du médecin avec ID : " + medecin.getId(), e);
        }
    }

    public void deleteMedecin(int id) {
        String query = "DELETE FROM medecins WHERE id = ?";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du médecin avec ID : " + id, e);
        }
    }

    private Medecin mapResultSetToMedecin(ResultSet rs) throws SQLException {
        return new Medecin(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("specialite"),
                rs.getString("telephone"),
                rs.getString("password")
        );
    }
    
    public Medecin authenticateMedecin(String nom, String password) {
        System.out.println("Authentification en cours pour le nom : " + nom);
        System.out.println("Mot de passe reçu : " + password);

        String query = "SELECT * FROM medecins WHERE nom = ? AND password = ?";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nom);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Authentification réussie pour : " + nom);
                return new Medecin(
                    resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getString("specialite"),
                    resultSet.getString("telephone"),
                    resultSet.getString("password")
                );
            } else {
                System.out.println("Authentification échouée pour : " + nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




}
