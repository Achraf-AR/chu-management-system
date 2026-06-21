<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    HttpSession sessionUser = request.getSession(false);
    if (sessionUser == null || sessionUser.getAttribute("username") == null) {
        response.sendRedirect("LoginAdmin.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil CHU</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #3498db;
            --accent-color: #e74c3c;
            --text-color: #333;
            --background-color: #f5f6fa;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            background-color: var(--background-color);
            color: var(--text-color);
            padding-left: 300px; /* Espace pour le menu fixe */
        }

        header {
            background-color: var(--primary-color);
            color: white;
            padding: 1rem;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            position: relative;
        }

        .header-content {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .logo-container {
            display: flex;
            align-items: center;
            gap: 1rem;
        }

        .logo {
            width: 50px;
            height: 50px;
            background-color: white;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }

        .logo img {
            max-width: 100%;
            max-height: 100%;
        }

        .admin-welcome {
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .admin-welcome i {
            font-size: 1.2rem;
        }

        .drawer {
            position: fixed;
            left: 0;
            top: 0;
            width: 300px;
            height: 100vh;
            background-color: white;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            z-index: 1000;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        .drawer-header {
            background-color: var(--primary-color);
            color: white;
            padding: 1.5rem;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .drawer ul {
            list-style: none;
            padding: 1rem 0;
            flex-grow: 1;
        }

        .drawer li {
            padding: 0.5rem 2rem;
            transition: background-color 0.3s ease;
        }

        .drawer li:hover {
            background-color: var(--background-color);
        }

        .drawer a {
            text-decoration: none;
            color: var(--text-color);
            display: block;
            padding: 0.5rem 0;
        }

        .logout {
            padding: 1rem 2rem;
            border-top: 1px solid #eee;
        }

        .logout a {
            color: var(--accent-color);
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        main {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        .welcome {
            background: white;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            text-align: center;
        }

        .welcome h2 {
            color: var(--primary-color);
            margin-bottom: 1rem;
        }

        .welcome p {
            color: var(--text-color);
            max-width: 600px;
            margin: 0 auto;
        }

        @media (max-width: 992px) {
            body {
                padding-left: 0;
            }
            
            .drawer {
                left: -300px;
                transition: left 0.3s ease;
            }
            
            .drawer.active {
                left: 0;
            }
            
            .menu-btn {
                display: block;
                background: none;
                border: none;
                color: white;
                font-size: 1.5rem;
                cursor: pointer;
                padding: 0.5rem;
            }
            
            .close-btn {
                position: absolute;
                right: 1rem;
                top: 1rem;
                background: none;
                border: none;
                font-size: 1.5rem;
                cursor: pointer;
                color: white;
            }
        }

        @media (min-width: 993px) {
            .menu-btn, .close-btn {
                display: none;
            }
        }

        @media (max-width: 768px) {
            .header-content {
                flex-direction: column;
                text-align: center;
                gap: 1rem;
            }
            
            .admin-welcome {
                margin-top: 0.5rem;
            }
        }
        .logout-btn {
    display: flex;
    align-items: center;
    gap: 8px;
    background-color: var(--accent-color);
    color: white;
    border: none;
    border-radius: 5px;
    padding: 10px 15px;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    width: 100%;
}

.logout-btn i {
    font-size: 16px;
}

.logout-btn:hover {
    background-color: #c0392b;
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.logout-btn:active {
    transform: translateY(0);
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
    </style>
</head>
<body>
    <nav id="drawer" class="drawer">
        <div class="drawer-header">
            <h2>CHU El Jadida</h2>
            <button class="close-btn" onclick="toggleDrawer()">×</button>
        </div>
        <ul>
            <li><a href="AccueilAdmin.jsp"><i class="fas fa-home"></i> Accueil</a></li>
            <li><a href="RendezVousAdminServlet"><i class="fas fa-user-injured"></i> Gestion des Patients</a></li>
            <li><a href="MedecinServlet?action=list"><i class="fas fa-user-md"></i> Gestion des Médecins</a></li>
            <li><a href="BatimentServlet?action=list"><i class="fas fa-building"></i> Gestion des Bâtiments</a></li>
            <li><a href="ServiceServlet?action=group"><i class="fas fa-clinic-medical"></i> Service</a></li>
        </ul>
        <div class="logout">
        <form action="LogoutServlet" method="post">
           <button type="submit" class="logout-btn"><i class="fas fa-sign-out-alt"></i> Déconnexion</button>

        </form>
    </div>
    </nav>

    <header>
        <div class="header-content">
            <div class="logo-container">
                <button class="menu-btn" onclick="toggleDrawer()">☰</button>
                <div class="logo">
                    <img src="images/logo.png" alt="CHU Logo" onerror="this.src='data:image/svg+xml;utf8,<svg xmlns=\'http://www.w3.org/2000/svg\' width=\'50\' height=\'50\'><text x=\'10\' y=\'30\' font-size=\'20\' fill=\'%233498db\'>CHU</text></svg>'">
                </div>
                
            </div>
            <div class="admin-welcome">
                <i class="fas fa-user-shield"></i>
                <span>Bienvenue Administrateur</span>
            </div>
        </div>
    </header>

    <main>
        <section class="welcome">
            <h2>Bienvenue au CHU El Jadida</h2>
            <p>Gérez facilement les bâtiments, le personnel, les patients et les services à partir de cette application.</p>
        </section>
    </main>

    <script>
        function toggleDrawer() {
            const drawer = document.getElementById('drawer');
            drawer.classList.toggle('active');
        }

        // Pour les écrans mobiles seulement
        if (window.innerWidth <= 992) {
            // Fermer le drawer en cliquant en dehors
            document.addEventListener('click', function(event) {
                const drawer = document.getElementById('drawer');
                const menuBtn = document.querySelector('.menu-btn');
                
                if (!drawer.contains(event.target) && !menuBtn.contains(event.target) && drawer.classList.contains('active')) {
                    drawer.classList.remove('active');
                }
            });

            // Empêcher la propagation du clic dans le drawer
            drawer.addEventListener('click', function(event) {
                event.stopPropagation();
            });
        }
    </script>
</body>
</html>