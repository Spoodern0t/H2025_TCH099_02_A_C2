package com.example.multuscalendrius.modeles.entitees;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {
    @JsonIgnore
    private int id;
    @JsonProperty("email")
    private String email;
    @JsonIgnore
    private String password;
    @JsonProperty("username")
    private String username;
    @JsonProperty("token")
    private String token;
    @JsonProperty("userCalendars")
    private List<UserCalendar> userCalendars;

    public User() {
    }

    public User(int id, String email, String password, String username, String token, List<UserCalendar> userCalendars) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.token = token;
        this.userCalendars = userCalendars;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public List<UserCalendar> getUserCalendars() { return userCalendars; }
    public void setUserCalendars(List<UserCalendar> userCalendars) { this.userCalendars = userCalendars; }

    public UserCalendar getUserCalendarById(long id) {
        for (UserCalendar usercalendar: userCalendars) {
            if (usercalendar.getCalendarId() == id) {
                return usercalendar;
            }
        }
        return null;
    }
}
