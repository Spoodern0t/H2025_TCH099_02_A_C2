package com.example.multuscalendrius.modeles.entitees;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties({"instance", "id"})
public class User {
    private Long id;
    @JsonProperty("email")
    private String email;
    private String password;
    @JsonProperty("username")
    private String username;
    @JsonProperty("token")
    private String token;
    @JsonProperty("userCalendars")
    private List<UserCalendar> userCalendars;


    @JsonIgnore
    private String errorMessage;

    public User() {
    }

    public User(Long id, String email, String password, String username, String token, List<UserCalendar> userCalendars) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.token = token;
        this.userCalendars = userCalendars;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() { return errorMessage; }
}
