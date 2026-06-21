package models;

public class Radiologie extends Batiment {
    public Radiologie(int id, String type, String taille, String description) {
        super(id, type, taille, description);
    }

    @Override
    public void afficherDetails() {
        System.out.println("Bâtiment de radiologie : " + getDescription());
    }
}