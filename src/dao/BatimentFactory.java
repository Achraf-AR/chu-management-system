package dao;

import models.Batiment;

public interface BatimentFactory {
    Batiment createBatiment(int id, String type, String taille, String description);
}