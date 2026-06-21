<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.ChefService"%>
<%@ page import="models.Message"%>
<%@ page import="dao.ChefServiceDAO"%>
<%@ page import="mediator.ConcreteChefServiceMediator"%>
<%@ page import="java.util.List"%>
<%@ page import="database.Singleton"%>

<%
    // Vérification de la session
    ChefService currentChef = (ChefService) session.getAttribute("chef");
    if (currentChef == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Initialisation du médiateur et des DAOs
    ConcreteChefServiceMediator mediator = new ConcreteChefServiceMediator();
    ChefServiceDAO chefServiceDAO = new ChefServiceDAO(Singleton.getInstance().getConnection());
    
    // Récupération des messages
    List<Message> messages = mediator.getMessages(currentChef);
    
    // Récupération de tous les chefs de service pour la liste des destinataires
    List<ChefService> allChefs = chefServiceDAO.getAllChefServices();
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - CHU El Jadida</title>
    <!-- Ajout de Font Awesome pour les icônes -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #0056b3;
            --secondary-color: #f8f9fa;
            --accent-color: #007bff;
            --danger-color: #dc3545;
            --text-dark: #343a40;
            --text-light: #6c757d;
            --shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            --border-radius: 8px;
            --transition: all 0.3s ease;
        }
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f8fa;
            color: var(--text-dark);
            line-height: 1.6;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .header {
            background-color: white;
            padding: 15px 0;
            border-bottom: 1px solid #eaeaea;
            margin-bottom: 30px;
            box-shadow: var(--shadow);
            border-radius: var(--border-radius);
        }
        
        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 20px;
        }
        
        .logo {
            font-size: 24px;
            font-weight: bold;
            color: var(--primary-color);
        }
        
        .card {
            background-color: white;
            border-radius: var(--border-radius);
            box-shadow: var(--shadow);
            margin-bottom: 25px;
            overflow: hidden;
            transition: var(--transition);
        }
        
        .card:hover {
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }
        
        .card-header {
            background-color: var(--primary-color);
            color: white;
            padding: 15px 20px;
            font-weight: 600;
            display: flex;
            align-items: center;
        }
        
        .card-header i {
            margin-right: 10px;
        }
        
        .card-body {
            padding: 20px;
        }
        
        .profile-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 15px;
        }
        
        .profile-item {
            margin-bottom: 10px;
        }
        
        .profile-label {
            font-weight: 600;
            color: var(--text-light);
            display: block;
            margin-bottom: 5px;
        }
        
        .profile-value {
            padding: 8px;
            background-color: var(--secondary-color);
            border-radius: 4px;
            border: 1px solid #eaeaea;
        }
        
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 500;
            transition: var(--transition);
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }
        
        .btn i {
            margin-right: 8px;
        }
        
        .btn-primary {
            background-color: var(--primary-color);
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #004494;
        }
        
        .btn-danger {
            background-color: var(--danger-color);
            color: white;
        }
        
        .btn-danger:hover {
            background-color: #bd2130;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
        }
        
        .form-control {
            width: 100%;
            padding: 12px;
            border: 1px solid #ced4da;
            border-radius: 4px;
            transition: var(--transition);
        }
        
        .form-control:focus {
            border-color: var(--accent-color);
            outline: none;
            box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.25);
        }
        
        .message-list {
            max-height: 500px;
            overflow-y: auto;
        }
        
        .message-item {
            padding: 15px;
            border-bottom: 1px solid #eaeaea;
            transition: var(--transition);
        }
        
        .message-item:last-child {
            border-bottom: none;
        }
        
        .message-item:hover {
            background-color: var(--secondary-color);
        }
        
        .message-header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            font-weight: 600;
            color: var(--primary-color);
        }
        
        .message-content {
            background-color: var(--secondary-color);
            padding: 15px;
            border-radius: 6px;
            margin-bottom: 10px;
            border-left: 4px solid var(--primary-color);
        }
        
        .message-date {
            color: var(--text-light);
            font-size: 0.85em;
            text-align: right;
        }
        
        .message-sent {
            border-left: 4px solid var(--accent-color);
        }
        
        .message-received {
            border-left: 4px solid var(--primary-color);
        }
        
        .empty-state {
            text-align: center;
            padding: 30px;
            color: var(--text-light);
        }
        
        @media (max-width: 768px) {
            .profile-grid {
                grid-template-columns: 1fr;
            }
            
            .header-content {
                flex-direction: column;
                text-align: center;
            }
            
            .header-content .logo {
                margin-bottom: 15px;
            }
        }
    </style>
</head>
<body>
    <header class="header">
        <div class="header-content container">
            <div class="logo">
                <i class="fas fa-hospital"></i> CHU El Jadida
            </div>
            <form action="./LogoutServlet" method="post">
                <button type="submit" class="btn btn-danger">
                    <i class="fas fa-sign-out-alt"></i> Déconnexion
                </button>
            </form>
        </div>
    </header>

    <div class="container">
        <!-- Section Profil -->
        <div class="card">
            <div class="card-header">
                <i class="fas fa-user-circle"></i> Profil du Chef de Service
            </div>
            <div class="card-body">
                <div class="profile-grid">
                    <div class="profile-item">
                        <span class="profile-label">Nom</span>
                        <div class="profile-value"><%= currentChef.getNom() %></div>
                    </div>
                    <div class="profile-item">
                        <span class="profile-label">Prénom</span>
                        <div class="profile-value"><%= currentChef.getPrenom() %></div>
                    </div>
                    <div class="profile-item">
                        <span class="profile-label">Email</span>
                        <div class="profile-value"><%= currentChef.getEmail() %></div>
                    </div>
                    <div class="profile-item">
                        <span class="profile-label">Téléphone</span>
                        <div class="profile-value"><%= currentChef.getTelephone() %></div>
                    </div>
                    <div class="profile-item">
                        <span class="profile-label">Date de nomination</span>
                        <div class="profile-value"><%= currentChef.getDateNomination() %></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Section Nouveau Message -->
        <div class="card">
            <div class="card-header">
                <i class="fas fa-paper-plane"></i> Envoyer un message
            </div>
            <div class="card-body">
                <form action="SendMessageServlet" method="post">
                    <div class="form-group">
                        <label for="receiver" class="form-label">Destinataire</label>
                        <select name="receiver" id="receiver" class="form-control" required>
                            <option value="">Sélectionnez un destinataire</option>
                            <% for(ChefService chef : allChefs) { 
                                if(chef.getId() != currentChef.getId()) { %>
                                    <option value="<%= chef.getId() %>">
                                        <%= chef.getNom() + " " + chef.getPrenom() %>
                                    </option>
                            <% }} %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="content" class="form-label">Message</label>
                        <textarea name="content" id="content" rows="4" class="form-control" required></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-paper-plane"></i> Envoyer
                    </button>
                </form>
            </div>
        </div>

        <!-- Section Messages -->
        <div class="card">
            <div class="card-header">
                <i class="fas fa-comments"></i> Messages
            </div>
            <div class="card-body">
                <div class="message-list">
                    <% if(messages.isEmpty()) { %>
                        <div class="empty-state">
                            <i class="fas fa-inbox fa-3x" style="margin-bottom: 15px; color: #ccc;"></i>
                            <p>Vous n'avez aucun message pour le moment.</p>
                        </div>
                    <% } else {
                        for(Message message : messages) { 
                            boolean isSent = message.getSender().getId() == currentChef.getId();
                    %>
                        <div class="message-item">
                            <div class="message-header">
                                <div>
                                    <i class="fas <%= isSent ? "fa-paper-plane" : "fa-envelope" %>"></i>
                                    <%= isSent ? "Envoyé à " + message.getReceiver().getNom() + " " + message.getReceiver().getPrenom() 
                                             : "Reçu de " + message.getSender().getNom() + " " + message.getSender().getPrenom() %>
                                </div>
                            </div>
                            <div class="message-content <%= isSent ? "message-sent" : "message-received" %>">
                                <%= message.getContent() %>
                            </div>
                            <div class="message-date">
                                <i class="far fa-clock"></i> <%= message.getSendDate() %>
                            </div>
                        </div>
                    <% }} %>
                </div>
            </div>
        </div>
    </div>
</body>
</html>