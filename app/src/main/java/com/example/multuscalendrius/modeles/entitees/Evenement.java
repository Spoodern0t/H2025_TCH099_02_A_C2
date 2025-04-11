package com.example.multuscalendrius.modeles.entitees;

public class Evenement {

    private int id;
    private int calendrierId;
    private String description;
    private String titre;

    public Evenement() {}

    // Getters et Setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public int getCalendrierId() {
        return calendrierId;
    }

    public void setCalendrierId(int calendrierId) {
        this.calendrierId = calendrierId;
    }
}
