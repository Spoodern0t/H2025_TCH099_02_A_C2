package com.example.multuscalendrius.modeles.entitees;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String email;
    private String password;
    private String username;
    private String token;
    private List<UserCalendar> userCalendars;

    public User() {
        this.userCalendars = new ArrayList<>();
    }

    public User(Long id, String email, String password, String name, String token, List<UserCalendar> userCalendars) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = name;
        this.token = token;
        this.userCalendars = userCalendars;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public List<UserCalendar> getUserCalendars() {
        return userCalendars;
    }
    public void setUserCalendars(List<UserCalendar> userCalendars) {
        this.userCalendars = userCalendars;
    }
}


