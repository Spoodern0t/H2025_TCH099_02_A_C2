package com.example.multuscalendrius.modeles.entitees;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Evenement {

    @JsonProperty("id_evenement")
    private int id;
    @JsonProperty("id_calendrier")
    private int calendrierId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("nom")
    private String titre;
    @JsonProperty("couleur")
    private String couleur;

    public Evenement() {}

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
    public String getCouleur() {
        return couleur;
    }
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
}
