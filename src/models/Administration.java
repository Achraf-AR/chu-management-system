package models;

public class Administration extends Batiment {
    public Administration(int id, String type, String taille, String description) {
        super(id, type, taille, description);
    }

    @Override
    public void afficherDetails() {
        System.out.println("Bâtiment d'administration : " + getDescription());
    }
}