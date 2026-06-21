package models;

public abstract  class Batiment {
    private int id;
    private String type;
    private String taille;
    private String description;

    public Batiment(int id, String type, String taille, String description) {
        this.id = id;
        this.type = type;
        this.taille = taille;
        this.description = description;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTaille() { return taille; }
    public void setTaille(String taille) { this.taille = taille; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Méthode abstraite pour afficher les détails spécifiques du bâtiment
    public abstract void afficherDetails();
}