package models;

public class Medecin {
    private int id;
    private String nom;
    private String specialite;
    private String telephone;
    private String password;
    private Integer idSection;
    private Integer personnelId;

    // Constructeurs
    public Medecin() {
    }

    public Medecin(int id, String nom, String specialite, String telephone, String password) {
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
        this.telephone = telephone;
        this.password = password;
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

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIdSection() {
        return idSection;
    }

    public void setIdSection(Integer idSection) {
        this.idSection = idSection;
    }

    public Integer getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Integer personnelId) {
        this.personnelId = personnelId;
    }
}