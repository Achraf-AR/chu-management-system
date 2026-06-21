package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import database.Singleton;
import models.Patient;

@WebServlet("/MedecinLoginServlet")
public class MedecinLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String password = request.getParameter("password");

        try {
            Connection connection = Singleton.getInstance().getConnection();

            // Verifier les identifiants du medecin
            String sql = "SELECT * FROM medecins WHERE nom = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nom);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Stocker les informations du médecin
                HttpSession session = request.getSession();
                int medecinId = resultSet.getInt("id");
                session.setAttribute("nomMedecin", resultSet.getString("nom"));
                session.setAttribute("specialite", resultSet.getString("specialite"));
                session.setAttribute("telephone", resultSet.getString("telephone"));

                // Obtenir les rendez-vous associés à ce mdedecin
             // Requete pour recuperer les rendez-vous du medecin
                String sqlRdv = "SELECT nom, prenom, telephone, date_heure, statut FROM patients WHERE medecin_id = ?";
                PreparedStatement rdvStatement = connection.prepareStatement(sqlRdv);
                rdvStatement.setInt(1, medecinId);
                ResultSet rdvResultSet = rdvStatement.executeQuery();

                // Stocker les rendez-vous dans une liste
                List<Patient> rendezVousList = new ArrayList<>();
                while (rdvResultSet.next()) {
                    Patient patient = new Patient(
                        0, // Id non nécessaire ici
                        rdvResultSet.getString("nom"),
                        rdvResultSet.getString("prenom"),
                        null, // Médecin non nécessaire ici
                        rdvResultSet.getTimestamp("date_heure"),
                        rdvResultSet.getString("telephone"),
                        rdvResultSet.getBoolean("statut") // Récupération du statut boolean
                    );
                    rendezVousList.add(patient);
                }
                session.setAttribute("rendezVousList", rendezVousList);

                // Rediriger vers la page d'accueil
                response.sendRedirect("./acceuil_medecin.jsp");
            } else {
                request.setAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect !");
                request.getRequestDispatcher("./MedecinLogin.jsp").forward(request, response);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
