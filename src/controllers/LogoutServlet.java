package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mediator.ConcreteChefServiceMediator;
import models.ChefService;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            try {
                // Verifier si l'utilisateur est un chef de service
                ChefService chef = (ChefService) session.getAttribute("chef");
                if (chef != null) {
                    // Informer le médiateur de la déconnexion
                    ConcreteChefServiceMediator mediator = new ConcreteChefServiceMediator();
                    mediator.removeChef(chef);
                }

                // Vérifier si l'utilisateur est un admin
                String adminUsername = (String) session.getAttribute("username");
                if (adminUsername != null) {
                    System.out.println("Deconnexion de l'admin : " + adminUsername);
                }

                // Invalider la session
                session.invalidate();
                
                // Ajouter un message de succes
                request.setAttribute("message", "Déconnexion réussie");
                
            } catch (Exception e) {
                e.printStackTrace();
                // En cas d'erreur, on continue quand même la déconnexion
            }
        }
        
        // Rediriger vers la page de connexion
        response.sendRedirect("LoginAdmin.jsp");
    }
}
