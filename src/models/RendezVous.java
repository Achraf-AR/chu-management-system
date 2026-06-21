package models;

import java.sql.Timestamp;

public class RendezVous {
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private Timestamp dateHeure;
    private int medecinId;
    private boolean statut;
    private Integer sectionId;
    private String medecinNom;
    
    // Constructeur vide
    public RendezVous() {
    }
    
    // Constructeur avec paramètres
    public RendezVous(String nom, String prenom, String telephone, Timestamp dateHeure, int medecinId) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.dateHeure = dateHeure;
        this.medecinId = medecinId;
        this.statut = false; // Par défaut, le rendez-vous n'est pas encore effectué
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public Timestamp getDateHeure() {
        return dateHeure;
    }
    
    public void setDateHeure(Timestamp dateHeure) {
        this.dateHeure = dateHeure;
    }
    
    public int getMedecinId() {
        return medecinId;
    }
    
    public void setMedecinId(int medecinId) {
        this.medecinId = medecinId;
    }
    
    public boolean isStatut() {
        return statut;
    }
    
    public void setStatut(boolean statut) {
        this.statut = statut;
    }
    
    public Integer getSectionId() {
        return sectionId;
    }
    
    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
 // Getter et Setter pour medecinNom
    public String getMedecinNom() {
        return medecinNom;
    }

    public void setMedecinNom(String medecinNom) {
        this.medecinNom = medecinNom;
    }
    
    @Override
    public String toString() {
        return "RendezVous [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", telephone=" + telephone
                + ", dateHeure=" + dateHeure + ", medecinId=" + medecinId + ", statut=" + statut 
                + ", sectionId=" + sectionId + "]";
    }
}
