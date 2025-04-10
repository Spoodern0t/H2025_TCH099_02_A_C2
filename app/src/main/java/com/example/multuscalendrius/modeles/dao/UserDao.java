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
    private static UserDao instance = null;
    private User user;
    private ApiService api;


    // Constructeur privé pour le pattern Singleton
    private UserDao() {
        this.api = new ApiService();
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

    public void connexion(String email, String password, ApiCallback<User> apiCallback) {
        api.connexion(email, password, apiCallback);
    }

    public void getUserCalendar(String token, ApiCallback<List<UserCalendar>> apiCallback) {
        api.getUserCalendar(token, apiCallback);
    }

    public void decoUser(String token, ApiCallback<Boolean> apiCallback) {
        api.decoUser(token, apiCallback);
    }
}



