package com.example.multuscalendrius.modeles.entitees;

import java.time.LocalDateTime;


public class Evenement {

    private String id;

    private String titre;

    private String description;

    public Evenement() {
    }

    // Constructeur complet
    public Evenement(String id, String titre, String description, LocalDateTime startTime, LocalDateTime endTime, String type, int priority, boolean completed) {
        this.id = id;
        this.titre = titre;
        this.description = description;
    }

    // Getters et Setters

    public String getId() {
        return id;
    }
    public void setId(String id) {
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


}
