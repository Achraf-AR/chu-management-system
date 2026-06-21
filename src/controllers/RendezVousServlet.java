package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.RendezVousDAO;
import models.RendezVous;
import models.Medecin;

@WebServlet("/RendezVousServlet")
public class RendezVousServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public RendezVousServlet() {
        super();
    }
    
    private Timestamp convertToTimestamp(String dateStr, String heureStr) {
        try {
            String dateTimeStr = dateStr + " " + heureStr + ":00";
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date parsedDate = dateFormat.parse(dateTimeStr);
            return new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RendezVousDAO rendezVousDAO = new RendezVousDAO();
        
        // Récupérer toutes les spécialités
        List<String> specialites = rendezVousDAO.getSpecialites();
        request.setAttribute("specialites", specialites);
        
        // Vérifier si une spécialité est déjà sélectionnée
        String specialiteParam = request.getParameter("specialite");
        if (specialiteParam != null && !specialiteParam.isEmpty()) {
            // Récupérer les médecins pour cette spécialité
            List<Medecin> medecins = rendezVousDAO.getMedecinsBySpecialite(specialiteParam);
            
            // Préparer les attributs pour la JSP
            request.setAttribute("specialiteSelectionnee", specialiteParam);
            request.setAttribute("medecins", medecins);
        }
        
        // Ajouter une liste vide pour les créneaux occupés
        request.setAttribute("creneauxOccupes", "[]");
        
        // Rediriger vers la JSP
        request.getRequestDispatcher("/prendreRendezVous.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        RendezVousDAO rendezVousDAO = new RendezVousDAO();
        
        // Toujours récupérer les spécialités pour les avoir disponibles
        List<String> specialites = rendezVousDAO.getSpecialites();
        request.setAttribute("specialites", specialites);
        
        if ("chargerMedecins".equals(action)) {
            // Récupérer la spécialité sélectionnée
            String specialite = request.getParameter("specialite");
            
            // Récupérer les médecins pour cette spécialité
            List<Medecin> medecins = rendezVousDAO.getMedecinsBySpecialite(specialite);
            
            // Préparer les attributs pour la JSP
            request.setAttribute("specialiteSelectionnee", specialite);
            request.setAttribute("medecins", medecins);
            
            // Rediriger vers la JSP
            request.getRequestDispatcher("/prendreRendezVous.jsp").forward(request, response);
        }
        else if ("enregistrerRendezVous".equals(action)) {
            try {
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String telephone = request.getParameter("telephone");
                String medecinIdStr = request.getParameter("medecin_id");
                String dateRdv = request.getParameter("date_rdv");
                String heureRdv = request.getParameter("heure_rdv");

                if (nom == null || prenom == null || telephone == null || 
                    medecinIdStr == null || dateRdv == null || heureRdv == null ||
                    nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || 
                    medecinIdStr.isEmpty() || dateRdv.isEmpty() || heureRdv.isEmpty()) {

                    request.setAttribute("message", "Tous les champs sont obligatoires.");
                    request.setAttribute("typeMessage", "error");
                } else {
                    int medecinId = Integer.parseInt(medecinIdStr);

                    // Vérifier si le créneau est disponible
                    if (!rendezVousDAO.isCreneauDisponible(medecinId, dateRdv, heureRdv)) {
                        request.setAttribute("message", "Ce créneau est déjà pris. Veuillez choisir un autre horaire.");
                        request.setAttribute("typeMessage", "error");
                    } else {
                        // Insérer le rendez-vous si disponible
                        Timestamp dateHeure = convertToTimestamp(dateRdv, heureRdv);
                        RendezVous rendezVous = new RendezVous(nom, prenom, telephone, dateHeure, medecinId);
                        boolean success = rendezVousDAO.ajouterRendezVous(rendezVous);

                        if (success) {
                            request.setAttribute("message", "Rendez-vous enregistré avec succès !");
                            request.setAttribute("typeMessage", "success");
                        } else {
                            request.setAttribute("message", "Erreur lors de l'enregistrement du rendez-vous.");
                            request.setAttribute("typeMessage", "error");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Erreur lors du traitement.");
                request.setAttribute("typeMessage", "error");
            }
            
            // Recharger la page
            request.getRequestDispatcher("/prendreRendezVous.jsp").forward(request, response);
        }
    }
}