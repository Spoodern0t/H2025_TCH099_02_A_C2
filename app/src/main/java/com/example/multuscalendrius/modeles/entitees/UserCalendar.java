package com.example.multuscalendrius.modeles.entitees;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"inviteAccepted"})
public class UserCalendar {
    @JsonProperty("id_utilisateur")
    private int userId;
    @JsonProperty("id_calendrier")
    private int calendarId;
    @JsonProperty("nom_calendrier")
    private String nomCalendrier;
    @JsonProperty("description")
    private String description;
    @JsonProperty("est_membre")
    private Boolean estMembre;
    private Boolean inviteAccepted;
    @JsonProperty("nom_utilisateur")
    private String auteur;

    // Constructeur par défaut

    public UserCalendar() {}

    // Constructeur complet
    public UserCalendar(int id, int userId, int calendarId) {
        this.userId = userId;
        this.calendarId = calendarId;
    }

    // Getters et Setters

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getCalendarId() {
        return calendarId;
    }
    public void setCalendarId(int calendarId) {
        this.calendarId = calendarId;
    }
    public String getNomCalendrier() {
        return nomCalendrier;
    }
    public void setNomCalendrier(String nomCalendrier) {
        this.nomCalendrier = nomCalendrier;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getEstMembre() {
        return estMembre;
    }
    public void setEstMembre(Boolean estMembre) {
        this.estMembre = estMembre;
    }
    public Boolean getInviteAccepted() {
        return inviteAccepted;
    }
    public void setInviteAccepted(Boolean inviteAccepted) {
        this.inviteAccepted = inviteAccepted;
    }
    public String getAuteur() {
        return auteur;
    }
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
}

