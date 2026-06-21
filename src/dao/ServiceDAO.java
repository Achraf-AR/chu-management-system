package dao;

import java.sql.*;
import java.util.*;
import models.*;

public class ServiceDAO {

    private Connection connection;

    public ServiceDAO(Connection connection) {
        this.connection = connection;
    }

    // ===== CREATE GROUPE =====
    public void creerGroupe(ServiceComposite groupe, Integer chefId) throws SQLException {

        String sql = "INSERT INTO service_groupes (nom, description, type, chef_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, groupe.getNom());
            ps.setString(2, groupe.getDescription());
            ps.setString(3, groupe.getType());
            ps.setObject(4, chefId);

            ps.executeUpdate();
        }

        if (chefId != null) {
            String updateChef = "UPDATE chefs_service SET groupe_id=?, service_id=NULL WHERE id=?";
            try (PreparedStatement ps = connection.prepareStatement(updateChef)) {
                ps.setInt(1, groupe.getId());
                ps.setInt(2, chefId);
                ps.executeUpdate();
            }
        }
    }

    // ===== CREATE SERVICE =====
    public void creerService(Service service, Integer chefId) throws SQLException {

        String sql = "INSERT INTO services (nom, description, type, chef_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, service.getNom());
            ps.setString(2, service.getDescription());
            ps.setString(3, service.getType());
            ps.setObject(4, chefId);

            ps.executeUpdate();
        }
    }

    // ===== READ =====
    public List<ServiceComposite> getAllGroupes() throws SQLException {

        List<ServiceComposite> list = new ArrayList<>();

        String sql = "SELECT * FROM service_groupes";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ServiceComposite g = new ServiceComposite(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("type")
                );

                list.add(g);
            }
        }

        return list;
    }
   

    	public List<Service> getAllServices() throws SQLException {

    	    List<Service> list = new ArrayList<>();

    	    String sql = "SELECT * FROM services";

    	    try (PreparedStatement ps = connection.prepareStatement(sql)) {

    	        ResultSet rs = ps.executeQuery();

    	        while (rs.next()) {

    	            Service s = new Service(
    	                    rs.getInt("id"),
    	                    rs.getString("nom"),
    	                    rs.getString("description"),
    	                    rs.getString("type")
    	            );

    	            // 🔥 CORRECTION
    	            s.setChefId(rs.getObject("chef_id") != null ? rs.getInt("chef_id") : null);

    	            list.add(s);
    	        }
    	    }

    	    return list;
    	}
   
    
 // ===== DELETE SERVICE =====
    public void deleteService(int id) throws SQLException {

        // enlever lien chef
        String reset = "UPDATE chefs_service SET service_id=NULL WHERE service_id=?";
        try (PreparedStatement ps = connection.prepareStatement(reset)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }

        String sql = "DELETE FROM services WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
 // ===== DELETE GROUPE =====
    public void deleteGroupe(int id) throws SQLException {

        // enlever lien chef
        String reset = "UPDATE chefs_service SET groupe_id=NULL WHERE groupe_id=?";
        try (PreparedStatement ps = connection.prepareStatement(reset)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }

        String sql = "DELETE FROM service_groupes WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public ServiceComposite getGroupeById(int id) throws SQLException {

        String sql = "SELECT * FROM service_groupes WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                ServiceComposite g = new ServiceComposite(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("type")
                );

                return g;
            }
        }

        return null;
    }
    
    public Service getServiceById(int id) throws SQLException {

        String sql = "SELECT * FROM services WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Service s = new Service(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("type")
                );

                return s;
            }
        }

        return null;
    }
    public void updateService(Service service, Integer chefId) throws SQLException {

        // 🔥 1. enlever ancien chef
        String reset = "UPDATE chefs_service SET service_id=NULL WHERE service_id=?";
        try (PreparedStatement ps = connection.prepareStatement(reset)) {
            ps.setInt(1, service.getId());
            ps.executeUpdate();
        }

        // 🔥 2. update service
        String sql = "UPDATE services SET nom=?, description=?, type=?, chef_id=? WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, service.getNom());
            ps.setString(2, service.getDescription());
            ps.setString(3, service.getType());
            ps.setObject(4, chefId);
            ps.setInt(5, service.getId());

            ps.executeUpdate();
        }

        // 🔥 3. affecter chef correctement
        if (chefId != null) {

            String updateChef = "UPDATE chefs_service SET service_id=?, groupe_id=NULL WHERE id=?";
            
            try (PreparedStatement ps = connection.prepareStatement(updateChef)) {
                ps.setInt(1, service.getId());
                ps.setInt(2, chefId);
                ps.executeUpdate();
            }

            System.out.println("Chef affecté au service ✔");
        } else {
            System.out.println("Aucun chef sélectionné");
        }
    }
    
    public void updateGroupe(ServiceComposite groupe, Integer chefId) throws SQLException {

        String sql = "UPDATE service_groupes SET nom=?, description=?, type=?, chef_id=? WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, groupe.getNom());
            ps.setString(2, groupe.getDescription());
            ps.setString(3, groupe.getType());
            ps.setObject(4, chefId);
            ps.setInt(5, groupe.getId());

            ps.executeUpdate();
        }

        if (chefId != null) {
            String updateChef = "UPDATE chefs_service SET groupe_id=?, service_id=NULL WHERE id=?";
            try (PreparedStatement ps = connection.prepareStatement(updateChef)) {
                ps.setInt(1, groupe.getId());
                ps.setInt(2, chefId);
                ps.executeUpdate();
            }
        }
    }
 // CREATE
    public void creerService(Service s) throws SQLException {

        String sql = "INSERT INTO services (nom, description, type, chef_id, parent_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, s.getNom());
            ps.setString(2, s.getDescription());
            ps.setString(3, s.getType());
            ps.setObject(4, s.getChefId());
            ps.setObject(5, s.getParent());

            ps.executeUpdate();
        }
    }

    // UPDATE
    public void updateService(Service s) throws SQLException {

        String sql = "UPDATE services SET nom=?, description=?, type=?, chef_id=?, parent_id=? WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, s.getNom());
            ps.setString(2, s.getDescription());
            ps.setString(3, s.getType());
            ps.setObject(4, s.getChefId());
            ps.setObject(5, s.getParent());
            ps.setInt(6, s.getId());

            ps.executeUpdate();
        }
    }
}