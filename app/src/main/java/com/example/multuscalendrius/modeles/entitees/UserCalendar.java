package com.example.multuscalendrius.modeles.entitees;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties({"inviteAccepted"})
public class UserCalendar implements Serializable {
    @JsonProperty("id_utilisateur")
    private Long userId;
    @JsonProperty("id_calendrier")
    private Long calendarId;
    @JsonProperty("nom_calendrier")
    private String nomCalendrier;
    @JsonProperty("est_membre")
    private Boolean estMembre;
    private Boolean inviteAccepted;
    @JsonProperty("nom_utilisateur")
    private String auteur;

    // Constructeur par défaut

    public UserCalendar() {}

    // Constructeur complet
    public UserCalendar(Long id, Long userId, Long calendarId) {
        this.userId = userId;
        this.calendarId = calendarId;
    }

    // Getters et Setters

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getCalendarId() {
        return calendarId;
    }
    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }
    public String getNomCalendrier() {
        return nomCalendrier;
    }
    public void setNomCalendrier(String nomCalendrier) {
        this.nomCalendrier = nomCalendrier;
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

