package mediator;

import models.ChefService;
import models.Message;
import java.sql.*;
import java.util.*;
import database.Singleton;

public class ConcreteChefServiceMediator implements ChefServiceMediator {
    private Connection connection;
    
    
    public ConcreteChefServiceMediator() throws SQLException {
        try {
            this.connection = Singleton.getInstance().getConnection();
            if (this.connection == null) {
                throw new SQLException("Impossible d'établir la connexion à la base de données");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de l'initialisation du médiateur: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = Singleton.getInstance().getConnection();
        }
        return connection;
    }

    @Override
    public void sendMessage(ChefService sender, ChefService receiver, String content) {
        String sql = "INSERT INTO messages (sender_id, receiver_id, content, send_date) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, sender.getId());
            pstmt.setInt(2, receiver.getId());
            pstmt.setString(3, content);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> getMessages(ChefService chef) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT m.*, " +
                    "s.nom as sender_nom, s.prenom as sender_prenom, " +
                    "r.nom as receiver_nom, r.prenom as receiver_prenom " +
                    "FROM messages m " +
                    "JOIN chefs_service s ON m.sender_id = s.id " +
                    "JOIN chefs_service r ON m.receiver_id = r.id " +
                    "WHERE m.sender_id = ? OR m.receiver_id = ? " +
                    "ORDER BY m.send_date DESC";
                    
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, chef.getId());
            pstmt.setInt(2, chef.getId());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("id"),
                    rs.getString("content"),
                    rs.getTimestamp("send_date"),
                    new ChefService(rs.getInt("sender_id"), rs.getString("sender_nom"), rs.getString("sender_prenom")),
                    new ChefService(rs.getInt("receiver_id"), rs.getString("receiver_nom"), rs.getString("receiver_prenom"))
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public void addChef(ChefService chef) {
        // Cette méthode pourrait être utilisée pour gérer les connexions actives
        // Pour l'instant, nous utilisons la base de données directement
    }

    @Override
    public void removeChef(ChefService chef) {
        if (chef == null) return;
        
        String sql = "UPDATE chefs_service SET last_logout = NOW() WHERE id = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, chef.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
