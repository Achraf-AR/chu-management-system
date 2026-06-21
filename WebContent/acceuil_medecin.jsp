<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Patient" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil Médecin</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary-color: #4361ee;
            --secondary-color: #3f37c9;
            --accent-color: #4cc9f0;
            --success-color: #4caf50;
            --warning-color: #ff9800;
            --danger-color: #f44336;
            --text-primary: #333;
            --text-secondary: #666;
            --background-light: #f8f9fa;
            --background-white: #ffffff;
            --shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            --border-radius: 12px;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body, html {
            font-family: 'Poppins', sans-serif;
            background-color: var(--background-light);
            color: var(--text-primary);
            line-height: 1.6;
            height: 100%;
            width: 100%;
        }

        .container {
            max-width: 1200px;
            margin: 40px auto;
            padding: 30px;
            background-color: var(--background-white);
            border-radius: var(--border-radius);
            box-shadow: var(--shadow);
        }

        header {
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 1px solid #eee;
        }

        h1 {
            color: var(--primary-color);
            font-size: 2.2rem;
            font-weight: 600;
            margin-bottom: 10px;
            text-align: center;
        }

        h2 {
            color: var(--secondary-color);
            font-size: 1.5rem;
            font-weight: 500;
            margin: 25px 0 15px 0;
        }

        .info {
            background-color: rgba(67, 97, 238, 0.05);
            padding: 20px;
            border-radius: var(--border-radius);
            margin-bottom: 30px;
            border-left: 4px solid var(--primary-color);
        }

        .info p {
            margin-bottom: 10px;
            color: var(--text-secondary);
        }

        .info p strong {
            color: var(--text-primary);
            font-weight: 600;
        }

        .table-container {
            max-height: 500px;
            overflow-y: auto;
            border-radius: var(--border-radius);
            border: 1px solid #eee;
            margin-top: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 0.95rem;
        }

        th {
            background-color: var(--primary-color);
            color: white;
            padding: 15px;
            text-align: left;
            position: sticky;
            top: 0;
            z-index: 10;
            font-weight: 500;
        }

        td {
            padding: 12px 15px;
            border-bottom: 1px solid #eee;
        }

        tr:nth-child(even) {
            background-color: rgba(67, 97, 238, 0.03);
        }

        tr:hover {
            background-color: rgba(67, 97, 238, 0.07);
            transition: background-color 0.2s ease;
        }

        .status {
            padding: 6px 12px;
            border-radius: 20px;
            display: inline-block;
            font-size: 0.85rem;
            font-weight: 500;
        }

        .status-consulte {
            background-color: rgba(76, 175, 80, 0.1);
            color: var(--success-color);
        }

        .status-non-consulte {
            background-color: rgba(255, 152, 0, 0.1);
            color: var(--warning-color);
        }

        .actions a {
            margin: 0 8px;
            color: var(--accent-color);
            text-decoration: none;
            font-weight: 500;
            transition: all 0.2s ease;
        }

        .actions a:hover {
            color: var(--primary-color);
        }

        .actions a.delete {
            color: var(--danger-color);
        }

        button {
            background-color: var(--primary-color);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: var(--border-radius);
            font-size: 1rem;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.2s ease;
            margin-top: 20px;
        }

        button:hover {
            background-color: var(--secondary-color);
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        footer {
            text-align: center;
            margin-top: 30px;
            padding-top: 20px;
            color: var(--text-secondary);
            font-size: 0.9rem;
            border-top: 1px solid #eee;
        }

        /* Responsive design */
        @media (max-width: 768px) {
            .container {
                width: 95%;
                padding: 20px;
                margin: 20px auto;
            }

            h1 {
                font-size: 1.8rem;
            }

            h2 {
                font-size: 1.3rem;
            }

            th, td {
                padding: 10px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>Tableau de Bord Médecin</h1>
        </header>
        
        <div class="info">
            <p><strong>Bienvenue Dr.</strong> <%= session.getAttribute("nomMedecin") %></p>
            <p><strong>Spécialité :</strong> <%= session.getAttribute("specialite") %></p>
            <p><strong>Téléphone :</strong> <%= session.getAttribute("telephone") %></p>
        </div>

        <h2>Liste des Rendez-vous</h2>
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th>Téléphone</th>
                        <th>Date et Heure</th>
                        <th>Statut</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Patient> rendezVousList = (List<Patient>) session.getAttribute("rendezVousList");
                        if (rendezVousList != null && !rendezVousList.isEmpty()) {
                            for (Patient patient : rendezVousList) {
                    %>
                    <tr>
                        <td><%= patient.getNom() %></td>
                        <td><%= patient.getPrenom() %></td>
                        <td><%= patient.getTelephone() %></td>
                        <td><%= patient.getDateHeure() %></td>
                        <td>
                            <span class="status <%= patient.isStatut() ? "status-consulte" : "status-non-consulte" %>">
                                <%= patient.isStatut() ? "Consulté" : "Non consulté" %>
                            </span>
                        </td>
                    </tr>
                    <% 
                            }
                        } else { 
                    %>
                    <tr>
                        <td colspan="5" style="text-align: center; padding: 20px;">
                            Aucun rendez-vous trouvé.
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
        
        <button onclick="window.location.href='dashboard.jsp'">Retour</button>
        
        <footer>
            <p>&copy; 2026 - Système de Gestion de Rendez-vous Médicaux</p>
        </footer>
    </div>
</body>
</html>