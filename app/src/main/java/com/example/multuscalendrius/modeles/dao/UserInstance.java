package com.example.multuscalendrius.modeles.dao;

import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.modeles.entitees.User;
import com.example.multuscalendrius.modeles.entitees.UserCalendar;
import com.example.multuscalendrius.vuemodele.ApiCallback;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;


@JsonIgnoreProperties({"instance", "id", "password", "api", "liveData", "operation", "errorMessage"})
public class UserInstance implements Serializable{
    private static UserInstance instance = null;
    private User user;


    // Constructeur privé pour le pattern Singleton
    private UserInstance() {
        user = new User();
    }

    // Méthode d'accès à l'instance unique
    public static UserInstance getInstance() {
        if (instance == null) {
            instance = new UserInstance();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}





