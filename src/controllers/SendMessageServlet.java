package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ChefService;
import dao.ChefServiceDAO;
import mediator.ConcreteChefServiceMediator;
import database.Singleton;

@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Récupérer le chef de service connecté
        ChefService sender = (ChefService) request.getSession().getAttribute("chef");
        if (sender == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Récupérer les données du formulaire
            int receiverId = Integer.parseInt(request.getParameter("receiver"));
            String content = request.getParameter("content");

            // Initialiser les objets nécessaires
            ChefServiceDAO chefServiceDAO = new ChefServiceDAO(Singleton.getInstance().getConnection());
            ConcreteChefServiceMediator mediator = new ConcreteChefServiceMediator();

            // Récupérer le destinataire
            ChefService receiver = chefServiceDAO.getChefServiceById(receiverId);

            if (receiver != null) {
                // Envoyer le message via le médiateur
                mediator.sendMessage(sender, receiver, content);
                response.sendRedirect("dashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Destinataire non trouvé");
                request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de l'envoi du message");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }
    }
}