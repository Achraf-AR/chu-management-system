package models;

import java.util.Date;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private String medecin;
    private Date dateHeure;
    private String telephone;
    private boolean statut;

    public Patient(int id, String nom, String prenom, String medecin, Date dateHeure, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.medecin = medecin;
        this.dateHeure = dateHeure;
        this.telephone = telephone;
        
    }
    
    public Patient(int id, String nom, String prenom, String medecin, Date dateHeure, String telephone, boolean statut) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.medecin = medecin;
        this.dateHeure = dateHeure;
        this.telephone = telephone;
        this.statut = statut;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getMedecin() { return medecin; }
    public void setMedecin(String medecin) { this.medecin = medecin; }

    public Date getDateHeure() { return dateHeure; }
    public void setDateHeure(Date dateHeure) { this.dateHeure = dateHeure; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public boolean isStatut() { return statut; }
    public void setStatut(boolean statut) { this.statut = statut; }
}
