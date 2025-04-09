package com.example.multuscalendrius.modeles.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.modeles.entitees.User;
import com.example.multuscalendrius.modeles.entitees.UserCalendar;
import com.example.multuscalendrius.vuemodele.ApiCallback;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties({"instance", "id", "password", "api", "liveData", "operation", "errorMessage"})
public class UserDao implements Serializable{
    // Singleton instance
    private static UserDao instance;
    private User user;
    @JsonProperty("userCalendars")
    private List<UserCalendar> userCalendars;
    private ApiService api;


    // Enumération pour identifier l'opération effectuée sur l'utilisateur
    public enum Operation {
        LOGIN,
        FETCH_USER_CALENDARS,
        ERREUR,
        AUTRE
    }

    private Operation operation;
    private String errorMessage;

    // Constructeur privé pour le pattern Singleton
    private UserDao() {
        this.userCalendars = new ArrayList<>();
        this.api = new ApiService();
        this.operation = Operation.AUTRE;
    }

    // Méthode d'accès à l'instance unique
    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Operation getOperation() { return operation; }
    public String getErrorMessage() { return errorMessage; }
}



