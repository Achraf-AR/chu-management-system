package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RendezVousDAO;
import models.RendezVous;

@WebServlet("/RendezVousAdminServlet")
public class RendezVousAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RendezVousAdminServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RendezVousDAO rendezVousDAO = new RendezVousDAO();

        try {
            // Récupérer tous les rendez-vous avec les informations des médecins
            List<RendezVous> rendezVousList = rendezVousDAO.getAllRendezVousFromPatients();
            request.setAttribute("rendezVousList", rendezVousList);

            // Afficher la page de gestion des rendez-vous
            request.getRequestDispatcher("/listeRendezVous.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Erreur lors de la récupération des rendez-vous: " + e.getMessage());
            request.setAttribute("typeMessage", "error");
            request.getRequestDispatcher("/listeRendezVous.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        RendezVousDAO rendezVousDAO = new RendezVousDAO();
        String message = null;
        String typeMessage = null;

        try {
            if (action == null || action.trim().isEmpty()) {
                throw new IllegalArgumentException("Action non spécifiée.");
            }

            String rdvIdStr = request.getParameter("rdvId");
            if (rdvIdStr == null || rdvIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("L'identifiant du rendez-vous est manquant.");
            }

            int rdvId = Integer.parseInt(rdvIdStr);

            switch (action) {
                case "marquerConsulte":
                    boolean successConsulte = rendezVousDAO.updateRendezVousStatut(rdvId, true);
                    message = successConsulte ? "Le rendez-vous a été marqué comme consulté avec succès." : "Erreur lors de la mise à jour du statut.";
                    typeMessage = successConsulte ? "success" : "error";
                    break;

                case "marquerAttente":
                    boolean successAttente = rendezVousDAO.updateRendezVousStatut(rdvId, false);
                    message = successAttente ? "Le rendez-vous a été remis en attente avec succès." : "Erreur lors de la mise à jour du statut.";
                    typeMessage = successAttente ? "success" : "error";
                    break;

                case "supprimerRendezVous":
                    boolean successSupprimer = rendezVousDAO.supprimerRendezVous(rdvId);
                    message = successSupprimer ? "Le rendez-vous a été supprimé avec succès." : "Erreur lors de la suppression du rendez-vous.";
                    typeMessage = successSupprimer ? "success" : "error";
                    break;

                default:
                    throw new IllegalArgumentException("Action non reconnue: " + action);
            }
        } catch (NumberFormatException e) {
            message = "Erreur : l'identifiant du rendez-vous est invalide.";
            typeMessage = "error";
        } catch (IllegalArgumentException e) {
            message = "Erreur : " + e.getMessage();
            typeMessage = "error";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Une erreur est survenue lors du traitement de la demande: " + e.getMessage();
            typeMessage = "error";
        }

        // Définir les messages pour l'affichage
        if (message != null) {
            request.setAttribute("message", message);
            request.setAttribute("typeMessage", typeMessage);
        }

        // Recharger la liste des rendez-vous
        List<RendezVous> rendezVousList = rendezVousDAO.getAllRendezVousFromPatients();
        request.setAttribute("rendezVousList", rendezVousList);

        // Rediriger vers la page JSP
        request.getRequestDispatcher("/listeRendezVous.jsp").forward(request, response);
    }
}