package dao;

import models.Administration;
import models.Batiment;

public class AdministrationFactory implements BatimentFactory {
    @Override
    public Batiment createBatiment(int id, String type, String taille, String description) {
        return new Administration(id, type, taille, description);
    }
}