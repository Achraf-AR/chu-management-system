<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Batiment" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier un bâtiment - CHU El Jadida</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #0056b3;
            --secondary-color: #f8f9fa;
            --accent-color: #007bff;
            --danger-color: #dc3545;
            --success-color: #28a745;
            --warning-color: #ffc107;
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
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .card {
            width: 100%;
            max-width: 900px;
            background-color: white;
            border-radius: var(--border-radius);
            box-shadow: var(--shadow);
            overflow: hidden;
            display: flex;
        }

        .card-side {
            padding: 30px;
        }

        .card-left {
            background-color: var(--primary-color);
            color: white;
            width: 40%;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
        }

        .card-right {
            width: 60%;
            padding: 40px;
        }

        .logo-container {
            margin-bottom: 30px;
        }

        .logo-title {
            font-size: 24px;
            font-weight: 600;
            margin-bottom: 10px;
        }

        .logo-subtitle {
            font-size: 16px;
            opacity: 0.9;
        }

        .card-info {
            margin-top: 30px;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.1);
            border-radius: var(--border-radius);
        }

        .card-info h3 {
            margin-bottom: 10px;
            font-size: 18px;
        }

        .card-info p {
            font-size: 14px;
            line-height: 1.6;
        }

        .form-header {
            margin-bottom: 30px;
            position: relative;
        }

        .form-title {
            font-size: 26px;
            font-weight: 600;
            color: var(--text-dark);
            margin-bottom: 15px;
            display: flex;
            align-items: center;
        }

        .form-title i {
            margin-right: 12px;
            color: var(--primary-color);
        }

        .form-subtitle {
            color: var(--text-light);
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--text-dark);
        }

        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ced4da;
            border-radius: 6px;
            font-size: 15px;
            transition: var(--transition);
        }

        .form-control:focus {
            border-color: var(--accent-color);
            outline: none;
            box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.25);
        }

        textarea.form-control {
            min-height: 120px;
            resize: vertical;
        }

        .form-buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 30px;
        }

        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 6px;
            font-weight: 500;
            font-size: 15px;
            cursor: pointer;
            transition: var(--transition);
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }

        .btn i {
            margin-right: 8px;
        }

        .btn-warning {
            background-color: var(--warning-color);
            color: var(--text-dark);
            min-width: 140px;
        }

        .btn-warning:hover {
            background-color: #e0a800;
        }

        .btn-danger {
            background-color: var(--danger-color);
            color: white;
            min-width: 140px;
        }

        .btn-danger:hover {
            background-color: #bd2130;
        }

        .error-message {
            background-color: var(--danger-color);
            color: white;
            padding: 15px;
            border-radius: var(--border-radius);
            margin-bottom: 20px;
            text-align: center;
            font-weight: 500;
        }

        @media (max-width: 768px) {
            .card {
                flex-direction: column;
            }

            .card-left, .card-right {
                width: 100%;
            }

            .card-left {
                padding: 30px 20px;
            }

            .form-buttons {
                flex-direction: column;
                gap: 15px;
            }

            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<%
    Batiment batiment = (Batiment) session.getAttribute("batiment");
    if (batiment == null) {
%>
    <div class="error-message">
        <i class="fas fa-exclamation-triangle"></i> Aucune donnée disponible pour ce bâtiment.
    </div>
<%
    } else {
%>
    <div class="card">
        <div class="card-side card-left">
            <div class="logo-container">
                <h1 class="logo-title">CHU El Jadida</h1>
                <p class="logo-subtitle">Gestion des infrastructures</p>
            </div>

            <div class="card-info">
                <h3><i class="fas fa-edit"></i> Modification</h3>
                <p>Vous pouvez modifier les informations du bâtiment existant. Veillez à remplir tous les champs requis pour enregistrer vos modifications.</p>
            </div>
        </div>

        <div class="card-side card-right">
            <div class="form-header">
                <h2 class="form-title">
                    <i class="fas fa-building"></i> Modifier un bâtiment
                </h2>
                <p class="form-subtitle">Modifiez les informations du bâtiment sélectionné</p>
            </div>

            <form method="post" action="./BatimentServlet">
                <input type="hidden" name="action" value="modifier">
                <input type="hidden" name="id" value="<%= batiment.getId() %>">

                <div class="form-group">
                    <label for="type" class="form-label">Type de bâtiment</label>
                    <select id="type" name="type" class="form-control" required>
                        <option value="administration" <%= batiment.getType().equals("administration") ? "selected" : "" %>>Administration</option>
                        <option value="laboratoire" <%= batiment.getType().equals("laboratoire") ? "selected" : "" %>>Laboratoire</option>
                        <option value="radiologie" <%= batiment.getType().equals("radiologie") ? "selected" : "" %>>Radiologie</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="taille" class="form-label">Taille (m²)</label>
                    <input type="text" id="taille" name="taille" class="form-control" value="<%= batiment.getTaille() %>" required>
                </div>

                <div class="form-group">
                    <label for="description" class="form-label">Description</label>
                    <textarea id="description" name="description" class="form-control" required><%= batiment.getDescription() %></textarea>
                </div>

                <div class="form-buttons">
                    <button type="submit" class="btn btn-warning">
                        <i class="fas fa-save"></i> Enregistrer
                    </button>
                    <button type="button" class="btn btn-danger" onclick="window.location.href='BatimentServlet?action=list'">
                        <i class="fas fa-times-circle"></i> Annuler
                    </button>
                </div>
            </form>
        </div>
    </div>
<%
    }
%>
</body>
</html>