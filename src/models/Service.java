package models;

public class Service implements ServiceComponent {
    private int id;
    private String nom;
    private String description;
    private String type;
    private ServiceComposite parent;
    private Integer chefId;

    public Service(int id, String nom, String description, String type) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.type = type;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public ServiceComposite getParent() { return parent; }
    public void setParent(ServiceComposite parent) { this.parent = parent; }

    public Integer getChefId() { return chefId; }
    public void setChefId(Integer chefId) { this.chefId = chefId; }

    @Override
    public void afficher() {
        System.out.println("Service: " + nom + " (" + type + ")");
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", nom, description, type);
    }
}