package dao;

import models.ChefService;
import models.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChefServiceDAO {
    private Connection connection;

    public ChefServiceDAO(Connection connection) {
        this.connection = connection;
    }
    
    public ChefService authenticate(String email, String motpasse) throws SQLException {
        String sql = "SELECT * FROM chefs_service WHERE email = ? AND motpasse = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, motpasse);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new ChefService(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("motpasse"),
                    rs.getString("telephone"),
                    rs.getString("date_nomination"),
                    rs.getString("status")
                );
            }
        }
        return null;
    }
    public void creerChefService(ChefService chef) throws SQLException {
        String sql = "INSERT INTO chefs_service (nom, prenom, email, motpasse, telephone, " +
                    "service_id, groupe_id, date_nomination, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, chef.getNom());
            pstmt.setString(2, chef.getPrenom());
            pstmt.setString(3, chef.getEmail());
            pstmt.setString(4, chef.getMotpasse());
            pstmt.setString(5, chef.getTelephone());
            
            if (chef.getServiceId() != null) {
                pstmt.setInt(6, chef.getServiceId());
            } else {
                pstmt.setNull(6, Types.INTEGER);
            }
            
            if (chef.getGroupeId() != null) {
                pstmt.setInt(7, chef.getGroupeId());
            } else {
                pstmt.setNull(7, Types.INTEGER);
            }
            
            pstmt.setString(8, chef.getDateNomination());
            pstmt.setString(9, chef.getStatus());
            
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                chef.setId(rs.getInt(1));
            }
        }
    }

    public boolean isServiceOrGroupeHasChef(Integer serviceId, Integer groupeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM chefs_service WHERE service_id = ? OR groupe_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, serviceId);
            pstmt.setObject(2, groupeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public ChefService getChefServiceById(int id) throws SQLException {
        String sql = "SELECT * FROM chefs_service WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new ChefService(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motpasse"),
                        rs.getString("telephone"),
                        rs.getString("date_nomination"),
                        rs.getString("status")
                    );
                }
            }
            System.out.println("Aucun chef trouvé avec l'ID: " + id);
            return null;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du chef avec l'ID: " + id);
            e.printStackTrace();
            throw e;
        }
    }

    public void updateChefService(ChefService chef) throws SQLException {
        String sql = "UPDATE chefs_service SET nom = ?, prenom = ?, email = ?, motpasse = ?, " +
                    "telephone = ?, service_id = ?, groupe_id = ?, date_nomination = ?, status = ? " +
                    "WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, chef.getNom());
            pstmt.setString(2, chef.getPrenom());
            pstmt.setString(3, chef.getEmail());
            pstmt.setString(4, chef.getMotpasse());
            pstmt.setString(5, chef.getTelephone());
            
            if (chef.getServiceId() != null) {
                pstmt.setInt(6, chef.getServiceId());
            } else {
                pstmt.setNull(6, Types.INTEGER);
            }
            
            if (chef.getGroupeId() != null) {
                pstmt.setInt(7, chef.getGroupeId());
            } else {
                pstmt.setNull(7, Types.INTEGER);
            }
            
            pstmt.setString(8, chef.getDateNomination());
            pstmt.setString(9, chef.getStatus());
            pstmt.setInt(10, chef.getId());
            
            pstmt.executeUpdate();
        }
    }

    public void deleteChefService(int id) throws SQLException {
        String sql = "DELETE FROM chefs_service WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<ChefService> getAllChefServices() throws SQLException {
        List<ChefService> chefs = new ArrayList<>();
        String sql = "SELECT * FROM chefs_service";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ChefService chef = new ChefService(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("motpasse"),
                    rs.getString("telephone"),
                    rs.getString("date_nomination"),
                    rs.getString("status")
                );
                chef.setServiceId(rs.getObject("service_id", Integer.class));
                chef.setGroupeId(rs.getObject("groupe_id", Integer.class));
                chefs.add(chef);
            }
        }
        return chefs;
    }

    // Nouvelle méthode pour récupérer les chefs disponibles
    public List<ChefService> getAvailableChefsForService() throws SQLException {
        List<ChefService> chefs = new ArrayList<>();
        String sql = "SELECT * FROM chefs_service WHERE status = 'chef_service' AND (service_id IS NULL AND groupe_id IS NULL)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ChefService chef = new ChefService(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("motpasse"),
                    rs.getString("telephone"),
                    rs.getString("date_nomination"),
                    rs.getString("status")
                );
                chef.setServiceId(rs.getObject("service_id", Integer.class));
                chef.setGroupeId(rs.getObject("groupe_id", Integer.class));
                chefs.add(chef);
            }
        }
        return chefs;
    }
    public List<ChefService> getAvailableChefsForGroup() throws SQLException {
        List<ChefService> chefs = new ArrayList<>();
        String sql = "SELECT * FROM chefs_service WHERE status = 'chef_groupe' AND (service_id IS NULL AND groupe_id IS NULL)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ChefService chef = new ChefService(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("motpasse"),
                    rs.getString("telephone"),
                    rs.getString("date_nomination"),
                    rs.getString("status")
                );
                chef.setServiceId(rs.getObject("service_id", Integer.class));
                chef.setGroupeId(rs.getObject("groupe_id", Integer.class));
                chefs.add(chef);
            }
        }
        return chefs;
    }
    public List<Service> getServicesByChefId(int chefId) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM services WHERE chef_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, chefId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Service service = new Service(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("description"),
                    rs.getString("type")
                );
                services.add(service);
            }
        }
        return services;
    }
}