package models;

public class Personnel {
    private int id;
    private String nom;
    private String prenom;
    private String fonction;
    private String telephone;
    private String email;

    // Constructeur avec les nouveaux champs
    public Personnel(int id, String nom, String prenom, String fonction, String telephone, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.telephone = telephone;
        this.email = email;
    }

    // Getters et setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getFonction() { return fonction; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }
}

