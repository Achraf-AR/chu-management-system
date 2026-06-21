package controllers;

import dao.MedecinDAO;

import models.Medecin;




import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/MedecinServlet")
public class MedecinServlet extends HttpServlet {
    private MedecinDAO medecinDAO;

    @Override
    public void init() throws ServletException {
        medecinDAO = new MedecinDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            switch (action == null ? "list" : action) {
                case "list":
                    listMedecins(session, response);
                    break;
                case "modifierForm":
                    showEditForm(request, session, response);
                    break;
                case "supprimer":
                    deleteMedecin(request, response);
                    break;
                default:
                    response.sendRedirect("error.jsp");
                    break;
            }
        } catch (SQLException e) {
            handleException(e, response);
        }
    }

    private void listMedecins(HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        List<Medecin> medecins = medecinDAO.getAllMedecins();
        session.setAttribute("medecins", medecins);
        response.sendRedirect("./liste_medecin.jsp");
    }

    private void showEditForm(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Medecin medecin = medecinDAO.getMedecinById(id);

        if (medecin == null) {
            response.sendRedirect("error.jsp");
        } else {
            session.setAttribute("medecin", medecin);
            response.sendRedirect("./modifier_medecin.jsp");
        }
    }

    private void deleteMedecin(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        medecinDAO.deleteMedecin(id);
        response.sendRedirect("MedecinServlet?action=list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("ajouter".equals(action)) {
                addMedecin(request, response);
            } else if ("modifier".equals(action)) {
                updateMedecin(request, response);
            } else if ("login".equals(action)) {
                login(request, response);
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            handleException(e, response);
        }
    }

    private void addMedecin(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String nom = request.getParameter("nom");
        String specialite = request.getParameter("specialite");
        String telephone = request.getParameter("telephone");
        String password = request.getParameter("password");

        if (isInputValid(nom, specialite, telephone)) {
            Medecin newMedecin = new Medecin(0, nom, specialite, telephone, password);
            medecinDAO.addMedecin(newMedecin);
            response.sendRedirect("MedecinServlet?action=list");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    private void updateMedecin(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String specialite = request.getParameter("specialite");
        String telephone = request.getParameter("telephone");
        String password = request.getParameter("password");


        if (isInputValid(nom, specialite, telephone)) {
            Medecin updatedMedecin = new Medecin(id, nom, specialite, telephone, password);
            medecinDAO.updateMedecin(updatedMedecin);
            response.sendRedirect("MedecinServlet?action=list");
        } else {
            response.sendRedirect("error.jsp?message=Invalid input");
        }
    }
    private boolean isInputValid(String nom, String specialite, String telephone) {
        return nom != null && !nom.isEmpty()
                && specialite != null && !specialite.isEmpty()
                && telephone != null && !telephone.isEmpty();
    }

    private void handleException(Exception e, HttpServletResponse response) throws IOException {
        e.printStackTrace();
        response.sendRedirect("error.jsp");
    }
    
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nom = request.getParameter("nom");
        String password = request.getParameter("password");

        Medecin medecin = medecinDAO.authenticateMedecin(nom, password);

        if (medecin != null) {
            // Connexion reussie : stockez le médecin dans la session et redirigez
            request.getSession().setAttribute("medecin", medecin);
            response.sendRedirect("acceuil_medecin.jsp");
        } else {
            // Connexion echouee : redirigez avec un message d'erreur
            response.sendRedirect("error.jsp");
        }
    }

    
    
}
