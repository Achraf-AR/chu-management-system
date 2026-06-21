package dao;

import models.Radiologie;
import models.Batiment;

public class RadiologieFactory implements BatimentFactory {
    @Override
    public Batiment createBatiment(int id, String type, String taille, String description) {
        return new Radiologie(id, type, taille, description);
    }
}