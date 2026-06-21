package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.ServiceDAO;
import database.Singleton;
import models.*;

@WebServlet("/ServiceServlet")
public class ServiceServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {

            if (action == null) action = "group";

            switch (action) {

                case "group":
                    viewGroups(request, response);
                    break;

                case "list":
                    viewServices(request, response);
                    break;

                case "new":
                    showForm(request, response);
                    break;

                case "edit":
                    editForm(request, response);
                    break;

                case "delete":
                    delete(request, response);
                    break;

                default:
                    viewGroups(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {

            if ("create".equals(action)) {
                create(request, response);
            }

            if ("update".equals(action)) {
                update(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    // ================= CREATE =================
    private void create(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Connection conn = Singleton.getInstance().getConnection();
        ServiceDAO dao = new ServiceDAO(conn);

        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        String type = request.getParameter("type");

        boolean isGroup = Boolean.parseBoolean(request.getParameter("isGroup"));

        Integer chefId = request.getParameter("chefId") != null &&
                !request.getParameter("chefId").isEmpty()
                ? Integer.parseInt(request.getParameter("chefId"))
                : null;

        if (isGroup) {

            ServiceComposite g = new ServiceComposite(0, nom, description, type);
            dao.creerGroupe(g, chefId);

            response.sendRedirect("ServiceServlet?action=group");

        } else {

            Service s = new Service(0, nom, description, type);
            dao.creerService(s, chefId);

            response.sendRedirect("ServiceServlet?action=list");
        }

        conn.close();
    }

    // ================= UPDATE =================
    private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Connection conn = Singleton.getInstance().getConnection();
        ServiceDAO dao = new ServiceDAO(conn);

        int id = Integer.parseInt(request.getParameter("id"));

        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        String type = request.getParameter("type");

        boolean isGroup = Boolean.parseBoolean(request.getParameter("isGroup"));

        Integer chefId = request.getParameter("chefId") != null &&
                !request.getParameter("chefId").isEmpty()
                ? Integer.parseInt(request.getParameter("chefId"))
                : null;

        if (isGroup) {

            ServiceComposite g = new ServiceComposite(id, nom, description, type);
            dao.updateGroupe(g, chefId);

            response.sendRedirect("ServiceServlet?action=group");

        } else {

            Service s = new Service(id, nom, description, type);
            dao.updateService(s, chefId);

            response.sendRedirect("ServiceServlet?action=list");
        }

        conn.close();
    }

    // ================= DELETE =================
    private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Connection conn = Singleton.getInstance().getConnection();
        ServiceDAO dao = new ServiceDAO(conn);

        int id = Integer.parseInt(request.getParameter("id"));
        boolean isGroup = Boolean.parseBoolean(request.getParameter("isGroup"));

        if (isGroup) {
            dao.deleteGroupe(id);
            response.sendRedirect("ServiceServlet?action=group");
        } else {
            dao.deleteService(id);
            response.sendRedirect("ServiceServlet?action=list");
        }

        conn.close();
    }

    // ================= VIEW =================
    private void viewGroups(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Connection conn = Singleton.getInstance().getConnection();
        ServiceDAO dao = new ServiceDAO(conn);

        List<ServiceComposite> groupes = dao.getAllGroupes();

        request.setAttribute("groupes", groupes);
        request.getRequestDispatcher("service-groups.jsp").forward(request, response);

        conn.close();
    }

    private void viewServices(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Connection conn = Singleton.getInstance().getConnection();
        ServiceDAO dao = new ServiceDAO(conn);

        List<Service> services = dao.getAllServices();

        request.setAttribute("services", services);
        request.getRequestDispatcher("service-list.jsp").forward(request, response);

        conn.close();
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        request.getRequestDispatcher("service-form.jsp").forward(request, response);
    }

    private void editForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Connection conn = Singleton.getInstance().getConnection();
        ServiceDAO dao = new ServiceDAO(conn);

        int id = Integer.parseInt(request.getParameter("id"));

        boolean isGroup = Boolean.parseBoolean(request.getParameter("isGroup"));

        if (isGroup) {
            request.setAttribute("groupe", dao.getGroupeById(id));
        } else {
            request.setAttribute("service", dao.getServiceById(id));
        }

        request.getRequestDispatcher("service-form.jsp").forward(request, response);

        conn.close();
    }
}