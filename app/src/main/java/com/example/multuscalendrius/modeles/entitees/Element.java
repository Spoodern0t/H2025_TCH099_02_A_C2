package com.example.multuscalendrius.modeles.entitees;

public abstract class Element {
    private String id;
    private String nom;
    private String description;
    private Evenement evenement;

    public Element() {
    }

    public Element(String id, String nom, String description, Evenement evenement) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.evenement = evenement;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Evenement getEvenement() {
        return evenement;
    }
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }
}

