package controllers;

import dao.BatimentDAO;
import dao.BatimentFactory;
import dao.AdministrationFactory;
import dao.LaboratoireFactory;
import dao.RadiologieFactory;
import models.Batiment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/BatimentServlet")
public class BatimentServlet extends HttpServlet {
    private BatimentDAO batimentDAO;

    @Override
    public void init() throws ServletException {
        batimentDAO = new BatimentDAO(new AdministrationFactory());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            switch (action == null ? "list" : action) {
                case "list":
                    listBatiments(session, response);
                    break;
                case "modifierForm":
                    showEditForm(request, session, response);
                    break;
                case "supprimer":
                    deleteBatiment(request, response);
                    break;
                default:
                    response.sendRedirect("error.jsp");
                    break;
            }
        } catch (SQLException e) {
            handleException(e, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("ajouter".equals(action)) {
                addBatiment(request, response);
            } else if ("modifier".equals(action)) {
                updateBatiment(request, response);
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            handleException(e, response);
        }
    }

    // Afficher la liste des bâtiments
    private void listBatiments(HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        List<Batiment> batiments = batimentDAO.getAllBatiments();
        session.setAttribute("batiments", batiments);
        response.sendRedirect("list_batiment.jsp");
    }

    // Afficher le formulaire de modification d'un bâtiment
    private void showEditForm(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Batiment batiment = batimentDAO.getBatimentById(id);

        if (batiment == null) {
            response.sendRedirect("error.jsp");
        } else {
            session.setAttribute("batiment", batiment);
            response.sendRedirect("modifier_batiment.jsp");
        }
    }

    // Supprimer un bâtiment
    private void deleteBatiment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        batimentDAO.deleteBatiment(id);
        response.sendRedirect("BatimentServlet?action=list");
    }

    // Ajouter un nouveau bâtiment
    private void addBatiment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String type = request.getParameter("type");
        String taille = request.getParameter("taille");
        String description = request.getParameter("description");

        if (isInputValid(type, taille, description)) {
            // Ajouter "m²" à la taille si ce n'est pas déjà fait
            if (!taille.endsWith("m²")) {
                taille += " m²";
            }

            // Choisir la fabrique appropriée en fonction du type
            BatimentFactory factory = getFactoryForType(type);
            Batiment newBatiment = factory.createBatiment(0, type, taille, description);
            batimentDAO.addBatiment(newBatiment);
            response.sendRedirect("BatimentServlet?action=list");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    // Mettre à jour un bâtiment existant
    private void updateBatiment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String type = request.getParameter("type");
        String taille = request.getParameter("taille");
        String description = request.getParameter("description");

        if (isInputValid(type, taille, description)) {
            if (!taille.endsWith("m²")) {
                taille += " m²";
            }

            Batiment updatedBatiment = batimentDAO.getBatimentById(id);
            updatedBatiment.setType(type);
            updatedBatiment.setTaille(taille);
            updatedBatiment.setDescription(description);
            batimentDAO.updateBatiment(updatedBatiment);
            response.sendRedirect("BatimentServlet?action=list");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    private boolean isInputValid(String type, String taille, String description) {
        return type != null && !type.isEmpty()
                && taille != null && !taille.isEmpty()
                && description != null && !description.isEmpty();
    }

    private void handleException(Exception e, HttpServletResponse response) throws IOException {
        e.printStackTrace();
        response.sendRedirect("error.jsp");
    }

    private BatimentFactory getFactoryForType(String type) {
        switch (type) {
            case "administration":
                return new AdministrationFactory();
            case "laboratoire":
                return new LaboratoireFactory();
            case "radiologie":
                return new RadiologieFactory();
            default:
                throw new IllegalArgumentException("Type de bâtiment non supporté : " + type);
        }
    }
}