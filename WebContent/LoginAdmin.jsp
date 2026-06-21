<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Connexion Admin - CHU</title>
  <style>
    body, html {
      margin: 0;
      padding: 0;
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      background-image: url('images/background.jpg'); 
       sbackground-size: contain; 
      background-repeat: no-repeat; 
      background-position: center; 
      background-color: #000; 
    }
    .main-section {
      display: flex;
      justify-content: space-between;
      align-items: center;
      width: 800px;
      background-color: #000A6C;
      padding: 20px;
      border-radius: 10px;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
    }

    /* Section gauche : Logo */
    .left-section {
      width: 40%;
      text-align: center;
    }

    .left-section img {
      max-width: 100%;
      height: auto;
      border-radius: 5px;
    }
    .right-section {
      width: 50%;
      color: #fff;
    }

    .right-section h2 {
      margin-bottom: 20px;
      font-size: 1.5em;
    }
    .right-section form {
      display: flex;
      flex-direction: column;
    }
    .right-section input {
      width: 100%;
      padding: 10px;
      margin-bottom: 15px;
      border: none;
      border-radius: 5px;
      font-size: 1em;
    }
    .right-section button {
      padding: 10px;
      font-size: 1em;
      font-weight: bold;
      color: #00116c;
      background-color: #ff9800;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      transition: background-color 0.3s;
    }
    .right-section button:hover {
      background-color: #e68900;
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
  </style>
</head>
<body>


  <!-- Barre de navigation -->
  <div class="navbar">
    <div class="logo">
      <img src="images/logo.png" alt="CHU Logo">
    </div>
    <ul>
       <li><a href="Index.jsp">Acceuil</a></li>
      <li><a href="login.jsp">Chef Service</a></li>
    
      <li><a href="MedecinLogin.jsp">Medecin</a></li>
    </ul>
  </div>
  


  <div class="main-section">
    <div class="left-section">
      <img src="images/logo.png" alt="Logo CHU El Jadida">
    </div>
    <div class="right-section">
      <h2>Connexion Admin</h2>
      <form action="./AdminLoginServlet" method="post">
                  <input type="text" name="username" placeholder="Nom d'utilisateur" required>
                  <input type="password" name="password" placeholder="Mot de passe" required>
        <button type="submit">Se connecter</button>
      </form>
    </div>
  </div>
</body>
</html>