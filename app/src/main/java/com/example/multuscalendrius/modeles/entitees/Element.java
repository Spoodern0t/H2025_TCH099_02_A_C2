package com.example.multuscalendrius.modeles.entitees;

import java.time.LocalDateTime;

public class Element {
    private int id;
    private int calendrierId;
    private String nom;
    private String description;
    private int evenementId;

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;



    public Element() {}


    public int getId() {
        return id;
    }
    public void setId(int id) {
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

    public int getEvenement() {
        return evenementId;
    }
    public void setEvenement(int evenementId) {
        this.evenementId = evenementId;
    }

    public int getCalendrierId() {
        return calendrierId;
    }

    public void setCalendrierId(int calendrierId) {
        this.calendrierId = calendrierId;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }
}

