package com.example.multuscalendrius.modeles.entitees;
import androidx.annotation.Nullable;

import java.util.Date;

public class Calendrier {
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    // Constructeur par défaut
    public Calendrier() {}

    // Constructeur complet
    public Calendrier(Long id, String name, String description, @Nullable Date createdAt, @Nullable Date updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}

/*


public class Calendrier {
    // Identifiant unique du calendrier
    private Long id;
    // Nom du calendrier
    private String nom;
    // Description du calendrier
    private String description;
    // Identifiant de l'utilisateur qui a créé le calendrier (auteur)
    private Long auteurId;
    // Liste des événements associés au calendrier
    private List<Evenement> evenements;
    // Liste des accès utilisateurs sous forme d'objets UserCalendar
    private List<UserCalendar> userCalendars;

    // Constructeur par défaut (initialisation des listes)
    public Calendrier() {
        evenements = new ArrayList<>();
        userCalendars = new ArrayList<>();
    }

    // Constructeur complet
    public Calendrier(Long id, String nom, String description, Long auteurId, List<Evenement> evenements, List<UserCalendar> userCalendars) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.auteurId = auteurId;
        this.evenements = evenements;
        this.userCalendars = userCalendars;
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    public Long getAuteurId() {
        return auteurId;
    }
    public void setAuteurId(Long auteurId) {
        this.auteurId = auteurId;
    }
    public List<Evenement> getEvenements() {
        return evenements;
    }
    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }
    public List<UserCalendar> getUserCalendars() {
        return userCalendars;
    }
    public void setUserCalendars(List<UserCalendar> userCalendars) {
        this.userCalendars = userCalendars;
    }
}
*/



