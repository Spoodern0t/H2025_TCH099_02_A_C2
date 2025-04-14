package com.example.multuscalendrius.modeles.entitees;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class Element {
    private int id;
    private int calendrierId;
    private String nom;
    private String description;
    private Evenement evenement;
    @JsonIgnore
    private Integer evenementId;
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

    public Evenement getEvenement() {
        return evenement;
    }
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
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

    public void setEvenementId(Integer evenementId) {
        this.evenementId = evenementId;
    }

    public Integer getEvenementId() {
        return evenementId;
    }
}

