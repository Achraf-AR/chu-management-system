package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.ChefServiceDAO;
import models.ChefService;
import database.Singleton;

@WebServlet("/ChefLoginServlet")
public class ChefLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String motpasse = request.getParameter("motpasse");

        // Validation des entrées
        if (email == null || email.trim().isEmpty() || motpasse == null || motpasse.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Veuillez remplir tous les champs.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            // Authentifier le chef de service
            ChefServiceDAO chefServiceDAO = new ChefServiceDAO(Singleton.getInstance().getConnection());
            ChefService chef = chefServiceDAO.authenticate(email, motpasse);

            if (chef != null) {
                // Créer une session pour l'utilisateur
                HttpSession session = request.getSession();
                session.setAttribute("chef", chef);

                // Rediriger vers le tableau de bord
                response.sendRedirect("dashboard.jsp");
            } else {
                // L'authentification a échoué
                request.setAttribute("errorMessage", "Email ou mot de passe incorrect.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Log de l'erreur
            e.printStackTrace();

            // Rediriger vers une page d'erreur
            request.setAttribute("errorMessage", "Une erreur s'est produite lors de la connexion. Veuillez réessayer.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}