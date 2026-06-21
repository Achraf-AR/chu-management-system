package models;
import java.sql.*;
import java.util.*;
import database.Singleton;

public class ServiceMediator {
    // Structure pour stocker les conversations
    private Map<String, List<ChatMessage>> conversations;
    
    public ServiceMediator() {
        this.conversations = new HashMap<>();
        System.out.println("ServiceMediator créé avec une nouvelle Map de conversations");
    }
    
    // Classe interne pour représenter un message
    private static class ChatMessage {
        String expediteur;
        String destinataire;
        String contenu;
        Timestamp timestamp;
        
        ChatMessage(String expediteur, String destinataire, String contenu) {
            this.expediteur = expediteur;
            this.destinataire = destinataire;
            this.contenu = contenu;
            this.timestamp = new Timestamp(System.currentTimeMillis());
        }
        
        @Override
        public String toString() {
            return String.format("[%s] %s → %s: %s", 
                timestamp.toString(), expediteur, destinataire, contenu);
        }
    }
    
    // Envoyer un message à un autre chef de service
    public void envoyerMessage(String expediteur, String destinataire, String message) {
        System.out.println("Envoi de message - De: " + expediteur + ", À: " + destinataire);
        
        // Créer la clé de conversation (triée pour assurer la cohérence)
        String conversationKey = getConversationKey(expediteur, destinataire);
        
        // Initialiser la liste de messages si nécessaire
        conversations.computeIfAbsent(conversationKey, k -> new ArrayList<>());
        
        // Ajouter le message
        ChatMessage chatMessage = new ChatMessage(expediteur, destinataire, message);
        conversations.get(conversationKey).add(chatMessage);
        
        System.out.println("Message ajouté à la conversation: " + conversationKey);
    }
    
    // Récupérer tous les messages pour un chef (envoyés et reçus)
    public List<String> getMessagesForChef(String chefNom) {
        List<String> allMessages = new ArrayList<>();
        
        // Parcourir toutes les conversations
        for (Map.Entry<String, List<ChatMessage>> entry : conversations.entrySet()) {
            String conversationKey = entry.getKey();
            // Si le chef fait partie de cette conversation
            if (conversationKey.contains(chefNom)) {
                for (ChatMessage msg : entry.getValue()) {
                    allMessages.add(msg.toString());
                }
            }
        }
        
        // Trier les messages par timestamp
        allMessages.sort((m1, m2) -> m2.compareTo(m1)); // Du plus récent au plus ancien
        
        return allMessages;
    }
    
    // Obtenir la liste des autres chefs disponibles pour la discussion
    public List<String> getAvailableChefs(String currentChef) throws SQLException {
        List<String> chefs = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = Singleton.getInstance().getConnection();
            stmt = conn.prepareStatement("SELECT nom FROM chefs_service WHERE nom != ?");
            stmt.setString(1, currentChef);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                chefs.add(rs.getString("nom"));
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignored */ }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { /* ignored */ }
        }
        
        return chefs;
    }
    
    // Utilitaire pour créer une clé de conversation cohérente
    private String getConversationKey(String chef1, String chef2) {
        // Trier les noms alphabétiquement pour assurer la cohérence
        return Arrays.asList(chef1, chef2)
            .stream()
            .sorted()
            .reduce((a, b) -> a + "_" + b)
            .get();
    }
}