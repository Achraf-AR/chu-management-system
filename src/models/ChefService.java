package models;

public class ChefService {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motpasse;
    private String telephone;
    private Integer serviceId;
    private Integer groupeId;
    private String dateNomination;
    private String status;
    public ChefService(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
    public ChefService(int id, String nom, String prenom, String email, String motpasse, 
                      String telephone, String dateNomination, String status) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motpasse = motpasse;
        this.telephone = telephone;
        this.dateNomination = dateNomination;
        this.status = status;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getMotpasse() { return motpasse; }
    public void setMotpasse(String motpasse) { this.motpasse = motpasse; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }
    
    public Integer getGroupeId() { return groupeId; }
    public void setGroupeId(Integer groupeId) { this.groupeId = groupeId; }
    
    public String getDateNomination() { return dateNomination; }
    public void setDateNomination(String dateNomination) { this.dateNomination = dateNomination; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
