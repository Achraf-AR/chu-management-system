package models;

public interface ServiceComponent {
    int getId();
    String getNom();
    String getDescription();
    String getType();
    void afficher();
    String toString();
}