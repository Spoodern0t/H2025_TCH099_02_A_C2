package com.example.multuscalendrius.modeles.entitees;


public class UserCalendar {
    private Long userId;
    private Long calendarId;
    private Boolean estMembre;
    private Boolean inviteAccepted;
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

