package dao;

import database.Singleton;
import models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Méthode pour récupérer tous les patients
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients";  // Remplace par ta table patients

        try (Connection conn = Singleton.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                patients.add(mapResultSetToPatient(rs));  // Mapping de ResultSet vers Patient
            }
        }
        return patients;
    }

    // Méthode pour récupérer un patient par ID
    public Patient getPatientById(int id) throws SQLException {
        String query = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToPatient(rs);  // Mapping de ResultSet vers Patient
            }
        }
        return null;
    }

    // Méthode pour ajouter un patient
    public void addPatient(Patient patient) throws SQLException {
        String query = "INSERT INTO patients (nom, prenom, medecin, date_heure, telephone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setString(3, patient.getMedecin());
            stmt.setTimestamp(4, new Timestamp(patient.getDateHeure().getTime()));  // Utilisation de Timestamp
            stmt.setString(5, patient.getTelephone());
            stmt.executeUpdate();
        }
    }

    // Méthode pour mettre à jour un patient
    public void updatePatient(Patient patient) throws SQLException {
        String query = "UPDATE patients SET nom = ?, prenom = ?, medecin = ?, date_heure = ?, telephone = ? WHERE id = ?";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setString(3, patient.getMedecin());
            stmt.setTimestamp(4, new Timestamp(patient.getDateHeure().getTime()));  // Utilisation de Timestamp
            stmt.setString(5, patient.getTelephone());
            stmt.setInt(6, patient.getId());
            stmt.executeUpdate();
        }
    }

    // Méthode pour supprimer un patient
    public void deletePatient(int id) throws SQLException {
        String query = "DELETE FROM patients WHERE id = ?";
        try (Connection conn = Singleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Méthode pour mapper le ResultSet vers un objet Patient
    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("medecin"),
                rs.getTimestamp("date_heure"),  // Conversion à partir du champ Timestamp
                rs.getString("telephone")
        );
    }
}
