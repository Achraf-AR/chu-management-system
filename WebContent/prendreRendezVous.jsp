<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="models.Medecin" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prendre Rendez-vous - CHU El Jadida</title>
    <style>
        /* Style global */
        body, html {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            font-family: Arial, sans-serif;
        }
        body {
            background-image: url('images/background.jpg');
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
            color: #333;
        }
        /* Barre de navigation */
        .navbar {
            position: fixed;
            top: 0;
            width: 100%;
            background-color: rgba(0, 10, 108);
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 20px;
            z-index: 1000;
        }
        .navbar ul {
            list-style: none;
            margin: 20px;
            padding: 0;
            display: flex;
        }
        .navbar ul li {
            margin: 0 15px;
        }
        .navbar ul li a {
            text-decoration: none;
            color: #fff;
            font-weight: bold;
            transition: color 0.3s;
        }
        .navbar ul li a:hover {
            color: #ff9800;
        }
        /* Contenu principal */
        .content {
            margin-top: 100px;
            padding: 30px;
            background-color: rgba(255, 255, 255, 0.9);
            max-width: 800px;
            margin-left: auto;
            margin-right: auto;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
        }
        .logo {
            width: 70px;
            height: 70px;
            overflow: hidden;
        }
        .logo img {
            max-width: 80%;
            max-height: 80%;
        }
        .content h1 {
            color: rgba(0, 10, 108);
            margin-bottom: 30px;
            text-align: center;
        }
        /* Formulaire */
        .form-container {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
        }
        .form-actions {
            grid-column: span 2;
            text-align: center;
            margin-top: 20px;
        }
        .btn {
            padding: 12px 24px;
            font-size: 1em;
            font-weight: bold;
            color: #fff;
            background-color: rgba(0, 10, 108);
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #e68900;
        }
        /* Messages */
        .message {
            padding: 15px;
            margin: 20px 0;
            border-radius: 5px;
            text-align: center;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
    <!-- jQuery et jQuery UI pour le datepicker -->
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
</head>
<body>
    <!-- Barre de navigation -->
    <div class="navbar">
        <div class="logo">
            <img src="images/logo.png" alt="CHU Logo">
        </div>
        <ul>
            <li><a href="login.jsp">Chef Service</a></li>
            <li><a href="LoginAdmin.jsp">Admin</a></li>
            <li><a href="MedecinLogin.jsp">Medecin</a></li>
            
        </ul>
    </div>
    
    <!-- Contenu principal -->
    <div class="content">
        <h1>Prendre un Rendez-vous</h1>
        
        <!-- Affichage des messages -->
        <%
            String message = (String) request.getAttribute("message");
            String typeMessage = (String) request.getAttribute("typeMessage");
            if (message != null && !message.isEmpty()) {
        %>
            <div class="message <%= "success".equals(typeMessage) ? "success" : "error" %>">
                <%= message %>
            </div>
        <% } %>
        
        <!-- Formulaire de sélection de spécialité -->
        <form action="RendezVousServlet" method="post">
            <input type="hidden" name="action" value="chargerMedecins">
            <div class="form-container">
                <div class="form-group" style="grid-column: span 2;">
                    <label for="specialite">Spécialité médicale*</label>
                    <select id="specialite" name="specialite" required>
                        <option value="">Sélectionnez une spécialité</option>
                        <%
                            List<String> specialites = (List<String>) request.getAttribute("specialites");
                            String specialiteSelectionnee = (String) request.getAttribute("specialiteSelectionnee");
                            if (specialites != null && !specialites.isEmpty()) {
                                for (String specialite : specialites) {
                        %>
                        <option value="<%= specialite %>" <%= (specialiteSelectionnee != null && specialite.equals(specialiteSelectionnee)) ? "selected" : "" %>>
                            <%= specialite %>
                        </option>
                        <%
                                }
                            } else {
                        %>
                        <option value="">Aucune spécialité disponible</option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>
        </form>
        
        <%
            List<Medecin> medecins = (List<Medecin>) request.getAttribute("medecins");
            if (specialiteSelectionnee != null && !specialiteSelectionnee.isEmpty() && medecins != null && !medecins.isEmpty()) {
        %>
        <!-- Formulaire de prise de rendez-vous -->
        <hr style="margin: 30px 0;">
        <h2 style="color: rgba(0, 10, 108); text-align: center;">Informations du rendez-vous</h2>
        <form action="RendezVousServlet" method="post">
            <input type="hidden" name="action" value="enregistrerRendezVous">
            <input type="hidden" name="specialite" value="<%= specialiteSelectionnee %>">
            <div class="form-container">
                <div class="form-group">
                    <label for="nom">Nom*</label>
                    <input type="text" id="nom" name="nom" required>
                </div>
                <div class="form-group">
                    <label for="prenom">Prénom*</label>
                    <input type="text" id="prenom" name="prenom" required>
                </div>
                <div class="form-group">
                    <label for="telephone">Téléphone*</label>
                    <input type="tel" id="telephone" name="telephone" required>
                </div>
                <div class="form-group">
                    <label for="medecin_id">Médecin*</label>
                    <select id="medecin_id" name="medecin_id" required>
                        <option value="">Sélectionnez un médecin</option>
                        <% for (Medecin medecin : medecins) { %>
                        <option value="<%= medecin.getId() %>">
                            Dr. <%= medecin.getNom() %> - <%= medecin.getSpecialite() %> (Tel: <%= medecin.getTelephone() %>)
                        </option>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="date_rdv">Date du rendez-vous*</label>
                    <input type="text" id="date_rdv" name="date_rdv" required readonly>
                </div>
                <div class="form-group">
                    <label for="heure_rdv">Heure du rendez-vous*</label>
                    <select id="heure_rdv" name="heure_rdv" required>
                        <option value="">Sélectionnez une heure</option>
                        <option value="08:00">08:00</option>
                        <option value="08:30">08:30</option>
                        <option value="09:00">09:00</option>
                        <option value="09:30">09:30</option>
                        <option value="10:00">10:00</option>
                        <option value="10:30">10:30</option>
                        <option value="11:00">11:00</option>
                        <option value="11:30">11:30</option>
                        <option value="14:00">14:00</option>
                        <option value="14:30">14:30</option>
                        <option value="15:00">15:00</option>
                        <option value="15:30">15:30</option>
                        <option value="16:00">16:00</option>
                        <option value="16:30">16:30</option>
                    </select>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn">Confirmer le rendez-vous</button>
                </div>
            </div>
        </form>
        <% } %>
    </div>
    
    <script>
        $(function() {
            // Initialisation du datepicker
            $("#date_rdv").datepicker({
                dateFormat: 'yy-mm-dd',
                minDate: 1,
                maxDate: '+2M',
                beforeShowDay: function(date) {
                    var day = date.getDay();
                    return [day != 0 && day != 6, ''];
                }
            });
            // Soumission automatique du formulaire lorsque la spécialité change
            $("#specialite").change(function() {
                if ($(this).val() !== "") {
                    $(this).closest("form").submit();
                }
            });
        });
        
        $(document).ready(function() {
            // On s'assure que creneauxOccupes est bien une chaîne JSON (ou "[]" par défaut)
            var creneauxOccupes = <%= request.getAttribute("creneauxOccupes") != null ? request.getAttribute("creneauxOccupes") : "[]" %>;
            $("#heure_rdv option").each(function() {
                if (creneauxOccupes.includes($(this).val())) {
                    $(this).prop("disabled", true);
                }
            });
        });
    </script>
</body>
</html>