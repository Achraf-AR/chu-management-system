<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Accueil - CHU</title>
  <style>
    /* Style global */
    body, html {
      margin: 0;
      padding: 0;
      width: 100%;
      height: 100%;
      font-family: Arial, sans-serif;
    }
    /* Image de fond */
    body {
      background-image: url('images/background.jpg'); /* Remplacez par le chemin de votre image */
      background-size: cover;
      background-position: center;
      background-attachment: fixed;
      color: #fff;
      display: flex;
      flex-direction: column;
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
      box-sizing: border-box;
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
    .logo {
      width: 70px;
      height: 70px;
      overflow: hidden;
    }
    .logo img {
      max-width: 80%;
      max-height: 80%;
    }
    /* Contenu principal */
    .content {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      text-align: center;
      height: 100vh; /* Utilise toute la hauteur de la fenêtre */
      padding: 0 20px; /* Ajoute un peu d'espace sur les côtés */
    }
    .content h1 {
      font-size: 3em;
      margin-bottom: 40px;
      color: #000;
    }
    .content p {
      font-size: 1.2em;
      margin-bottom: 40px;
      max-width: 800px; /* Limite la largeur du texte pour une meilleure lisibilité */
      color: #000;
    }
    .btn {
      padding: 15px 30px;
      font-size: 1em;
      font-weight: bold;
      color: #fff;
      background-color: rgba(0, 10, 108);
      border: none;
      border-radius: 5px;
      cursor: pointer;
      transition: background-color 0.3s;
      text-decoration: none; /* Supprime le soulignement du lien */
      display: inline-block;
    }
    .btn:hover {
      background-color: #e68900;
    }

    /* Style responsive */
    @media (max-width: 768px) {
      .navbar {
        flex-direction: column;
        padding: 10px;
      }
      .navbar ul {
        margin: 10px 0;
        flex-wrap: wrap;
        justify-content: center;
      }
      .content {
        margin-top: 150px; /* Ajuste pour la navbar plus grande en mobile */
        padding-bottom: 20px;
      }
      .content h1 {
        font-size: 2em;
      }
    }
  </style>
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
    <h1>Bienvenue au CHU El Jadida</h1>
    <p>Explorez nos services, prenez rendez-vous et apprenez-en davantage sur notre équipe dédiée.</p>
    <a href="RendezVousServlet" class="btn">Prendre un Rendez-vous</a>
  </div>
</body>
</html>