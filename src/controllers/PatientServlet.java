package controllers;

import dao.PatientDAO;
import models.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/PatientServlet")
public class PatientServlet extends HttpServlet {
    private PatientDAO patientDAO;

    @Override
    public void init() throws ServletException {
        patientDAO = new PatientDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            switch (action == null ? "list" : action) {
                case "list":
                    listPatients(session, response);
                    break;
                case "modifierForm":
                    showEditForm(request, session, response);
                    break;
                case "supprimer":
                    deletePatient(request, response);
                    break;
                default:
                    response.sendRedirect("error.jsp");
                    break;
            }
        } catch (SQLException e) {
            handleException(e, response);
        }
    }

    private void listPatients(HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        List<Patient> patients = patientDAO.getAllPatients();
        session.setAttribute("patients", patients);
        response.sendRedirect("./list_patient.jsp");
    }

    private void showEditForm(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Patient patient = patientDAO.getPatientById(id);

        if (patient == null) {
            response.sendRedirect("error.jsp");
        } else {
            session.setAttribute("patient", patient);
            response.sendRedirect("./modifier_patient.jsp");
        }
    }

    private void deletePatient(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        patientDAO.deletePatient(id);
        response.sendRedirect("PatientServlet?action=list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("ajouter".equals(action)) {
                addPatient(request, response);
            } else if ("modifier".equals(action)) {
                updatePatient(request, response);
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            handleException(e, response);
        }
    }

    private void addPatient(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String medecin = request.getParameter("medecin");
        String dateHeure = request.getParameter("date_heure");
        String telephone = request.getParameter("telephone");

        if (isInputValid(nom, prenom, medecin, dateHeure, telephone)) {
            Patient newPatient = new Patient(0, nom, prenom, medecin, java.sql.Timestamp.valueOf(dateHeure), telephone);
            patientDAO.addPatient(newPatient);
            response.sendRedirect("PatientServlet?action=list");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    private void updatePatient(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String medecin = request.getParameter("medecin");
        String dateHeure = request.getParameter("date_heure");
        String telephone = request.getParameter("telephone");

        if (isInputValid(nom, prenom, medecin, dateHeure, telephone)) {
            Patient updatedPatient = new Patient(id, nom, prenom, medecin, java.sql.Timestamp.valueOf(dateHeure), telephone);
            patientDAO.updatePatient(updatedPatient);
            response.sendRedirect("PatientServlet?action=list");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    private boolean isInputValid(String nom, String prenom, String medecin, String dateHeure, String telephone) {
        return nom != null && !nom.isEmpty()
                && prenom != null && !prenom.isEmpty()
                && medecin != null && !medecin.isEmpty()
                && dateHeure != null && !dateHeure.isEmpty()
                && telephone != null && !telephone.isEmpty();
    }

    private void handleException(Exception e, HttpServletResponse response) throws IOException {
        e.printStackTrace();
        response.sendRedirect("error.jsp");
    }
}
