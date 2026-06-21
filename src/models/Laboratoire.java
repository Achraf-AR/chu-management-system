package models;

public class Laboratoire extends Batiment {
    public Laboratoire(int id, String type, String taille, String description) {
        super(id, type, taille, description);
    }

    @Override
    public void afficherDetails() {
        System.out.println("Bâtiment de laboratoire : " + getDescription());
    }
}