package dao;

import models.Laboratoire;
import models.Batiment;

public class LaboratoireFactory implements BatimentFactory {
    @Override
    public Batiment createBatiment(int id, String type, String taille, String description) {
        return new Laboratoire(id, type, taille, description);
    }
}