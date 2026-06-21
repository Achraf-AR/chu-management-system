<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.ChefServiceDAO" %>
<%@ page import="models.ChefService" %>
<%@ page import="database.Singleton" %>
<!DOCTYPE html>
<html>
<head>
    <title>Services Groupés - CHU</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        :root {
            --primary-color: #2563eb;
            --primary-hover: #1d4ed8;
            --secondary-color: #475569;
            --success-color: #16a34a;
            --danger-color: #dc2626;
            --background-color: #f1f5f9;
            --drawer-background: #ffffff;
            --border-color: #e2e8f0;
            --text-primary: #1e293b;
            --text-secondary: #64748b;
        }

        body {
            background-color: var(--background-color);
            color: var(--text-primary);
            font-family: system-ui, -apple-system, sans-serif;
        }

        .navbar {
            background: linear-gradient(135deg, var(--primary-color), var(--primary-hover)) !important;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.07);
            padding: 1rem 2rem;
        }

        .navbar-brand {
            font-weight: 600;
            letter-spacing: 0.5px;
        }

        .drawer {
            height: 100%;
            width: 0;
            position: fixed;
            z-index: 1050;
            top: 0;
            left: 0;
            background-color: var(--drawer-background);
            overflow-x: hidden;
            transition: 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            padding-top: 60px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            border-right: 1px solid var(--border-color);
        }

        .drawer.show {
            width: 320px;
        }

        .drawer-content {
            padding: 2rem;
        }

        .drawer-title {
            font-size: 1.25rem;
            font-weight: 600;
            color: var(--text-primary);
            padding-bottom: 1.25rem;
            margin-bottom: 1.5rem;
            border-bottom: 2px solid var(--border-color);
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }

        .drawer-btn {
            width: 100%;
            text-align: left;
            padding: 0.875rem 1.25rem;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.2s ease;
            margin-bottom: 0.75rem;
            display: flex;
            align-items: center;
            gap: 0.75rem;
            border: none;
        }

        .drawer-btn:hover {
            transform: translateX(5px);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
        }

        .drawer-btn.btn-success {
            background-color: var(--success-color);
            color: white;
        }

        .drawer-btn.btn-info {
            background-color: var(--primary-color);
            color: white;
        }

        .drawer-btn.btn-warning {
            background-color: #eab308;
            color: white;
        }

        .drawer-backdrop {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(0, 0, 0, 0.3);
            backdrop-filter: blur(4px);
            z-index: 1040;
            transition: opacity 0.3s ease;
        }

        .drawer-backdrop.show {
            display: block;
        }

        .control-buttons {
            position: fixed;
            top: 1.5rem;
            left: 1.5rem;
            z-index: 1030;
            display: flex;
            gap: 0.75rem;
        }

        .control-btn {
            width: 42px;
            height: 42px;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            background: white;
            border: 1px solid var(--border-color);
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
            transition: all 0.2s ease;
        }

        .control-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            color: var(--primary-color);
        }

        .service-group {
            background: white;
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
            border: 1px solid var(--border-color);
            transition: all 0.2s ease;
        }

        .service-group:hover {
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .nested-service {
            margin: 1rem 0 1rem 1.5rem;
            padding: 1.25rem;
            border-left: 3px solid var(--primary-color);
            background-color: rgba(37, 99, 235, 0.02);
            border-radius: 0 8px 8px 0;
        }

        .btn {
            font-weight: 500;
            padding: 0.5rem 1rem;
            border-radius: 6px;
            transition: all 0.2s ease;
        }

        .btn:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .group-title {
            margin-bottom: 1rem;
        }

        .fas {
            font-size: 1.1rem;
        }
    </style>
</head>
<body>
    <!-- Control Buttons -->
    <div class="control-buttons">
        <button onclick="goBack()" class="control-btn" title="Retour">
            <i class="fas fa-arrow-left"></i>
        </button>
        <button onclick="toggleDrawer()" class="control-btn" title="Menu">
            <i class="fas fa-bars"></i>
        </button>
    </div>

    <!-- Drawer -->
    <div id="drawer" class="drawer">
        <div class="drawer-content">
            <h4 class="drawer-title">
                <i class="fas fa-hospital-alt"></i>
                Menu Principal
            </h4>
            <a href="ServiceServlet?action=new&type=group" class="drawer-btn btn-success">
                <i class="fas fa-plus"></i>
                Nouveau Groupe
            </a>
            <a href="ServiceServlet?action=new" class="drawer-btn btn-info">
                <i class="fas fa-plus"></i>
                Nouveau Service
            </a>
            <a href="ServiceServlet?action=list" class="drawer-btn btn-warning">
                <i class="fas fa-list"></i>
                Liste des Services
            </a>
            <a href="ChefServiceServlet?action=new" class="drawer-btn btn-info">
                <i class="fas fa-user-md"></i>
                Nouveau Chef de Service
            </a>
        </div>
    </div>
    <div id="drawer-backdrop" class="drawer-backdrop" onclick="toggleDrawer()"></div>

    <!-- Navbar -->
    <nav class="navbar navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="<%=request.getContextPath()%>/ServiceServlet?action=group">
                <i class="fas fa-hospital-alt"></i> CHU El Jadida
            </a>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="container mt-4">
        <div class="row mb-3">
            <div class="col">
                <h2><i class="fas fa-layer-group"></i> Services Groupés</h2>
            </div>
        </div>

        <%
        List<ServiceComposite> groupes = (List<ServiceComposite>) request.getAttribute("groupes");
        ChefServiceDAO chefServiceDAO = new ChefServiceDAO(Singleton.getInstance().getConnection());
        if (groupes != null && !groupes.isEmpty()) {
            for (ServiceComposite groupe : groupes) {
        %>
        <div class="service-group">
            <div class="d-flex justify-content-between align-items-center group-title">
                <h4><%=groupe.getNom()%> (<%=groupe.getType()%>)</h4>
                <div>
                    <a href="ServiceServlet?action=edit&id=<%=groupe.getId()%>&type=group" 
                       class="btn btn-sm btn-primary">
                        <i class="fas fa-edit"></i>
                    </a>
                    <button onclick="if(confirm('Supprimer ce groupe?')) window.location='ServiceServlet?action=delete&id=<%=groupe.getId()%>&type=group'"
                            class="btn btn-sm btn-danger">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </div>
            <p><%=groupe.getDescription()%></p>
            
            <div class="mb-3">
                <strong>Chef de Groupe:</strong>
                <% if (groupe.getChefId() != null) { 
                    ChefService chef = chefServiceDAO.getChefServiceById(groupe.getChefId());
                    if (chef != null) { %>
                        <%= chef.getNom() %> <%= chef.getPrenom() %>
                    <% } else { %>
                        Chef non trouvé
                    <% }
                } else { %>
                    Aucun chef assigné
                <% } %>
            </div>

            <div class="nested-services">
                <%
                for (ServiceComponent service : groupe.getServices()) {
                    if (service instanceof Service) {
                        Service s = (Service) service;
                %>
                <div class="nested-service">
                    <div class="d-flex justify-content-between align-items-center">
                        <h5><%=s.getNom()%> (<%=s.getType()%>)</h5>
                        <div>
                            <a href="ServiceServlet?action=edit&id=<%=s.getId()%>" 
                               class="btn btn-sm btn-outline-primary">
                                <i class="fas fa-edit"></i>
                            </a>
                            <button onclick="if(confirm('Supprimer ce service?')) window.location='ServiceServlet?action=delete&id=<%=s.getId()%>'"
                                    class="btn btn-sm btn-outline-danger">
                                <i class="fas fa-trash"></i>
                            </button>
                        </div>
                    </div>
                    <p class="mb-0"><%=s.getDescription()%></p>
                    
                    <div class="mt-2">
                        <strong>Chef de Service:</strong>
                        <% if (s.getChefId() != null) { 
                            ChefService chef = chefServiceDAO.getChefServiceById(s.getChefId());
                            if (chef != null) { %>
                                <%= chef.getNom() %> <%= chef.getPrenom() %>
                            <% } else { %>
                                Chef non trouvé
                            <% }
                        } else { %>
                            Aucun chef assigné
                        <% } %>
                    </div>
                </div>
                <%
                    }
                }
                %>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <div class="alert alert-info">
            <i class="fas fa-info-circle"></i> Aucun groupe de services disponible
        </div>
        <%
        }
        %>
    </div>

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <script>
        function toggleDrawer() {
            const drawer = document.getElementById('drawer');
            const backdrop = document.getElementById('drawer-backdrop');
            drawer.classList.toggle('show');
            backdrop.classList.toggle('show');
        }
        
        function goBack() {
            window.location.href = 'AccueilAdmin.jsp';
        }
    </script>
</body>
</html>