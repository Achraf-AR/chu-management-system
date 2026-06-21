<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="models.Patient" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Liste des Patients</title>
    <style type="text/css">
        /* Style global */
        body, html {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            width: 80%;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        h1 {
            color: #333;
            margin-bottom: 20px;
            text-align: center;
        }

        a {
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
            display: inline-block;
            margin-bottom: 20px;
        }

        a:hover {
            text-decoration: underline;
        }

        .table-container {
            max-height: 400px;
            overflow-y: auto;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 1em;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th {
            background-color: #007bff;
            color: white;
            padding: 10px;
            position: sticky;
            top: 0;
            z-index: 1;
        }

        td {
            text-align: center;
            padding: 10px;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .actions a {
            margin: 0 10px;
            color: #ff9800;
            font-weight: bold;
            text-decoration: none;
        }

        .actions a:hover {
            color: #e68900;
        }

        .actions a.delete {
            color: #e63946;
        }

        .actions a.delete:hover {
            color: #d12c2f;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .container {
                width: 95%;
            }

            table {
                font-size: 0.9em;
            }
        }
        
        button {
            padding: 8px;
            margin-bottom: 15px;
            border-radius: 5px;
            font-size: 1em;
            background-color: #FFA500;
            border: none;
            cursor: pointer;
            text-align: center;
        }

        button:hover {
            background-color: #e68900;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Liste des Patients</h1>
    
    <div class="table-container">
        <table>
            <tr>
                <th>Numéro</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Téléphone</th>
                <th>Medecin</th>
                <th>Rendez-vous</th>
            </tr>
            <%
                // Récupérer la liste des patients depuis la session
                List<Patient> patients = (List<Patient>) session.getAttribute("patients");

                if (patients != null && !patients.isEmpty()) {
                    for (Patient patient : patients) {
            %>
            <tr>
            
                <td><%= patient.getId() %></td>
                <td><%= patient.getNom() %></td>
                <td><%= patient.getPrenom() %></td>
                <td> <%= patient.getMedecin() %> </td>
                <td> <%= patient.getTelephone() %> </td>
                <td> <%= patient.getDateHeure() %> </td>
             
                
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="6">Aucun patient trouvé.</td>
            </tr>
            <%
                }
            %>
        </table>
        
    </div>         
</div>
                     

</body>
</html>
