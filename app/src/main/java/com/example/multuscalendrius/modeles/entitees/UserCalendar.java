package com.example.multuscalendrius.modeles.entitees;


public class UserCalendar {
    private Long id;
    private Long userId;
    private Long calendarId;
    private UserRole role;

    // Constructeur par défaut
    public UserCalendar() {}

    // Constructeur complet
    public UserCalendar(Long id, Long userId, Long calendarId, UserRole role) {
        this.id = id;
        this.userId = userId;
        this.calendarId = calendarId;
        this.role = role;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
}

