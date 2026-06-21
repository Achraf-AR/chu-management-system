package models;

import java.util.ArrayList;
import java.util.List;

public class ServiceComposite implements ServiceComponent {
    private int id;
    private String nom;
    private String description;
    private String type;
    private List<ServiceComponent> services;
    private ServiceComposite parent;
    private Integer chefId;

    public ServiceComposite(int id, String nom, String description, String type) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.type = type;
        this.services = new ArrayList<>();
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

    public List<ServiceComponent> getServices() { return services; }
    public void ajouter(ServiceComponent service) {
        if (service instanceof Service) {
            ((Service) service).setParent(this);
        } else if (service instanceof ServiceComposite) {
            ((ServiceComposite) service).setParent(this);
        }
        services.add(service);
    }

    public void supprimer(ServiceComponent service) {
        services.remove(service);
    }

    public ServiceComposite getParent() { return parent; }
    public void setParent(ServiceComposite parent) { this.parent = parent; }

    public Integer getChefId() { return chefId; }
    public void setChefId(Integer chefId) { this.chefId = chefId; }

    @Override
    public void afficher() {
        System.out.println("Groupe de services: " + nom);
        for (ServiceComponent service : services) {
            service.afficher();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Groupe: %s - %s (%s)\n", nom, description, type));
        for (ServiceComponent service : services) {
            sb.append(" - ").append(service.toString()).append("\n");
        }
        return sb.toString();
    }
}