package com.example.multuscalendrius.modeles.entitees;

import java.time.LocalDateTime;

public class Element {
    private String id;
    private String calendrierId;
    private String nom;
    private String description;
    private Evenement evenement;

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;



    public Element(int id, int calendrierId, String nom, String description,
                   Evenement evenement, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.id = id;
        this.calendrierId = calendrierId;
        this.nom = nom;
        this.description = description;
        this.evenement = evenement;
        this.dateDebut= dateDebut;
        this.dateFin=dateFin;
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

