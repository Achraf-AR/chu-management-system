package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import models.RendezVous;
import database.Singleton;
import models.Medecin;

public class RendezVousDAO {
    // Requêtes SQL simplifiées
    private static final String INSERT_RDV = "INSERT INTO patients (nom, prenom, telephone, date_heure, medecin_id, statut, section_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String CHECK_DISPONIBILITE = "SELECT COUNT(*) FROM patients WHERE medecin_id = ? AND date_heure = ? AND statut = 0";
    private static final String GET_MEDECINS_BY_SPECIALITE = "SELECT id, nom, specialite, telephone FROM medecins WHERE specialite = ?";
    private static final String GET_SPECIALITES = "SELECT DISTINCT specialite FROM medecins ORDER BY specialite";
    private static final String GET_ALL_RDV_FROM_PATIENTS = "SELECT p.*, m.nom as medecin_nom FROM patients p LEFT JOIN medecins m ON p.medecin_id = m.id ORDER BY p.date_heure DESC";
    private static final String UPDATE_RDV_STATUT = "UPDATE patients SET statut = ? WHERE id = ?";
    private static final String DELETE_RDV = "DELETE FROM patients WHERE id = ?";

    // Méthode pour ajouter un rendez-vous
    public boolean ajouterRendezVous(RendezVous rendezVous) {
        try (Connection connection = Singleton.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_RDV)) {
            
            // Vérifier que tous les champs nécessaires sont présents
            if (rendezVous.getNom() == null || rendezVous.getPrenom() == null || 
                rendezVous.getTelephone() == null || rendezVous.getDateHeure() == null) {
                System.err.println("Données de rendez-vous incomplètes");
                return false;
            }
            
            // Afficher les données pour le débogage
            System.out.println("Insertion RDV: " + rendezVous.toString());
            
            ps.setString(1, rendezVous.getNom());
            ps.setString(2, rendezVous.getPrenom());
            ps.setString(3, rendezVous.getTelephone());
            ps.setTimestamp(4, rendezVous.getDateHeure());
            ps.setInt(5, rendezVous.getMedecinId());
            ps.setBoolean(6, rendezVous.isStatut());
            
            // Gestion du champ section_id qui peut être null
            if (rendezVous.getSectionId() != null) {
                ps.setInt(7, rendezVous.getSectionId());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }
            
            int result = ps.executeUpdate();
            System.out.println("Résultat de l'insertion: " + result + " ligne(s) affectée(s)");
            return result > 0;
            
        } catch (SQLException e) {
            // Afficher plus de détails sur l'erreur pour faciliter le débogage
            System.err.println("Erreur SQL lors de l'ajout du rendez-vous: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour vérifier la disponibilité
    public boolean verifierDisponibilite(int medecinId, Timestamp dateHeure) {
        try (Connection connection = Singleton.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(CHECK_DISPONIBILITE)) {
            
            ps.setInt(1, medecinId);
            ps.setTimestamp(2, dateHeure);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0; // Disponible si aucun rendez-vous n'existe
                }
            }
            
            return true; // Par défaut, considérer comme disponible
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En cas d'erreur, considérer comme non disponible
        }
    }

    // Méthode pour obtenir la liste des spécialités
    public List<String> getSpecialites() {
        List<String> specialites = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            connection = Singleton.getInstance().getConnection();
            ps = connection.prepareStatement(GET_SPECIALITES);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                specialites.add(rs.getString("specialite"));
                System.out.println("Spécialité trouvée: " + rs.getString("specialite"));
            }
            
            System.out.println("Total des spécialités récupérées: " + specialites.size());
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des spécialités: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fermer les ressources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return specialites;
    }
    // Méthode pour obtenir les médecins par spécialité
    public List<Medecin> getMedecinsBySpecialite(String specialite) {
        List<Medecin> medecins = new ArrayList<>();
        
        try (Connection connection = Singleton.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_MEDECINS_BY_SPECIALITE)) {
            
            ps.setString(1, specialite);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medecin medecin = new Medecin();
                    medecin.setId(rs.getInt("id"));
                    medecin.setNom(rs.getString("nom"));
                    medecin.setSpecialite(rs.getString("specialite"));
                    medecin.setTelephone(rs.getString("telephone"));
                    medecins.add(medecin);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return medecins;
    }

    // Méthode pour convertir une date et une heure en Timestamp
    private Timestamp convertToTimestamp(String dateStr, String heureStr) {
        try {
            // Format attendu: dateStr = "yyyy-MM-dd", heureStr = "HH:mm"
            String dateTimeStr = dateStr + " " + heureStr + ":00";
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date parsedDate = dateFormat.parse(dateTimeStr);
            return new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour vérifier si un créneau est disponible
    public boolean isCreneauDisponible(int medecinId, String dateRdv, String heureRdv) {
        boolean disponible = true;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = Singleton.getInstance().getConnection(); // Votre méthode pour obtenir une connexion
            
            // Convertir en Timestamp pour la comparaison
            Timestamp dateHeure = convertToTimestamp(dateRdv, heureRdv);
            
            // Durée d'un rendez-vous en minutes (à ajuster selon votre système)
            int dureeRendezVous = 30;
            
            // Calculer la fin du rendez-vous
            Timestamp finRendezVous = new Timestamp(dateHeure.getTime() + (dureeRendezVous * 60 * 1000));
            
            // Vérifier si un rendez-vous existe dans l'intervalle pour ce médecin
            String sql = "SELECT COUNT(*) FROM patients WHERE medecin_id = ? " +
                         "AND ((date_heure <= ? AND DATE_ADD(date_heure, INTERVAL ? MINUTE) > ?) " +
                         "OR (date_heure < ? AND DATE_ADD(date_heure, INTERVAL ? MINUTE) >= ?))";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, medecinId);
            pstmt.setTimestamp(2, dateHeure);
            pstmt.setInt(3, dureeRendezVous);
            pstmt.setTimestamp(4, dateHeure);
            pstmt.setTimestamp(5, finRendezVous);
            pstmt.setInt(6, dureeRendezVous);
            pstmt.setTimestamp(7, dateHeure);
            
            rs = pstmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                disponible = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            disponible = false; // En cas d'erreur, considérer que le créneau n'est pas disponible
        } finally {
            // Fermer les ressources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return disponible;
    }
 
    // Méthode pour récupérer tous les rendez-vous depuis la table patients
    public List<RendezVous> getAllRendezVousFromPatients() {
        List<RendezVous> rendezVousList = new ArrayList<>();

        try (Connection connection = Singleton.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_ALL_RDV_FROM_PATIENTS);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Exécution de la requête SQL : " + GET_ALL_RDV_FROM_PATIENTS);

            while (rs.next()) {
                RendezVous rendezVous = new RendezVous();
                rendezVous.setId(rs.getInt("id"));
                rendezVous.setNom(rs.getString("nom"));
                rendezVous.setPrenom(rs.getString("prenom"));
                rendezVous.setTelephone(rs.getString("telephone"));
                rendezVous.setDateHeure(rs.getTimestamp("date_heure"));
                rendezVous.setMedecinId(rs.getInt("medecin_id"));

                // Convertir l'entier en booléen pour le statut
                boolean statut = rs.getInt("statut") == 1;
                rendezVous.setStatut(statut);

                // Définir les informations supplémentaires
                rendezVous.setMedecinNom(rs.getString("medecin_nom"));

                // Gérer le section_id qui peut être null
                if (rs.getObject("section_id") != null) {
                    rendezVous.setSectionId(rs.getInt("section_id"));
                }

                rendezVousList.add(rendezVous);
            }

            System.out.println("Nombre de rendez-vous récupérés : " + rendezVousList.size());

        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la récupération des rendez-vous : " + e.getMessage());
            e.printStackTrace();
        }

        return rendezVousList;
    }

    // Méthode pour mettre à jour le statut d'un rendez-vous
    public boolean updateRendezVousStatut(int rendezVousId, boolean nouveauStatut) {
        try (Connection connection = Singleton.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_RDV_STATUT)) {

            int statutValue = nouveauStatut ? 1 : 0;
            stmt.setInt(1, statutValue);
            stmt.setInt(2, rendezVousId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la mise à jour du statut : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour supprimer un rendez-vous
    public boolean supprimerRendezVous(int rendezVousId) {
        try (Connection connection = Singleton.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_RDV)) {

            stmt.setInt(1, rendezVousId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la suppression du rendez-vous : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}