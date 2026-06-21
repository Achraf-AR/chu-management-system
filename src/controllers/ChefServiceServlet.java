package controllers;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import models.*;
import dao.*;
import database.Singleton;
import java.util.List;

@WebServlet("/ChefServiceServlet")
public class ChefServiceServlet extends HttpServlet {

    // ❌ حذفنا init و DAO global

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            switch (action == null ? "list" : action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteChef(request, response);
                    break;
                default:
                    listChefs(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    createChef(request, response);
                    break;
                case "update":
                    updateChef(request, response);
                    break;
                default:
                    listChefs(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void createChef(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Connection conn = Singleton.getInstance().getConnection();
        ChefServiceDAO chefDAO = new ChefServiceDAO(conn);

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motpasse = request.getParameter("motpasse");
        String telephone = request.getParameter("telephone");
        String dateNomination = request.getParameter("dateNomination");
        String status = request.getParameter("status");

        String entityId = request.getParameter("entityId");
        Integer serviceId = null;
        Integer groupeId = null;

        if (entityId != null && !entityId.isEmpty()) {
            String[] parts = entityId.split("_");
            if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);
                if ("service".equals(parts[0])) serviceId = id;
                else if ("groupe".equals(parts[0])) groupeId = id;
            }
        }

        ChefService chef = new ChefService(0, nom, prenom, email, motpasse,
                telephone, dateNomination, status);

        chef.setServiceId(serviceId);
        chef.setGroupeId(groupeId);

        chefDAO.creerChefService(chef);

        conn.close();
        response.sendRedirect("ServiceServlet?action=group");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Connection conn = Singleton.getInstance().getConnection();
        ServiceDAO serviceDAO = new ServiceDAO(conn);

        List<Service> services = serviceDAO.getAllServices();
        List<ServiceComposite> groupes = serviceDAO.getAllGroupes();

        request.setAttribute("services", services);
        request.setAttribute("groupes", groupes);

        request.getRequestDispatcher("chef-service-form.jsp").forward(request, response);

        conn.close();
    }

    private void listChefs(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Connection conn = Singleton.getInstance().getConnection();
        ChefServiceDAO chefDAO = new ChefServiceDAO(conn);

        List<ChefService> chefs = chefDAO.getAllChefServices();

        request.setAttribute("chefs", chefs);
        request.getRequestDispatcher("service-groups.jsp").forward(request, response);

        conn.close();
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Connection conn = Singleton.getInstance().getConnection();
        ChefServiceDAO chefDAO = new ChefServiceDAO(conn);
        ServiceDAO serviceDAO = new ServiceDAO(conn);

        int id = Integer.parseInt(request.getParameter("id"));

        ChefService chef = chefDAO.getChefServiceById(id);
        List<Service> services = serviceDAO.getAllServices();
        List<ServiceComposite> groupes = serviceDAO.getAllGroupes();

        request.setAttribute("chef", chef);
        request.setAttribute("services", services);
        request.setAttribute("groupes", groupes);

        request.getRequestDispatcher("chef-service-form.jsp").forward(request, response);

        conn.close();
    }

    private void updateChef(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Connection conn = Singleton.getInstance().getConnection();
        ChefServiceDAO chefDAO = new ChefServiceDAO(conn);

        int id = Integer.parseInt(request.getParameter("id"));

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motpasse = request.getParameter("motpasse");
        String telephone = request.getParameter("telephone");
        String dateNomination = request.getParameter("dateNomination");
        String status = request.getParameter("status");

        ChefService chef = new ChefService(id, nom, prenom, email, motpasse,
                telephone, dateNomination, status);

        chefDAO.updateChefService(chef);

        conn.close();
        response.sendRedirect("ServiceServlet?action=group");
    }

    private void deleteChef(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Connection conn = Singleton.getInstance().getConnection();
        ChefServiceDAO chefDAO = new ChefServiceDAO(conn);

        int id = Integer.parseInt(request.getParameter("id"));

        chefDAO.deleteChefService(id);

        conn.close();
        response.sendRedirect("ServiceServlet?action=group");
    }
}