package com.example.multuscalendrius.modeles.dao;

import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.modeles.entitees.User;
import com.example.multuscalendrius.modeles.entitees.UserCalendar;
import com.example.multuscalendrius.vuemodele.ApiCallback;
import java.util.List;

public class UserDao {
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

    public void decoUser(String token, ApiCallback<Boolean> apiCallback) {
        api.decoUser(token, apiCallback);
    }

    public void getUserCalendar(String token, ApiCallback<List<UserCalendar>> apiCallback) {
        api.getUserCalendar(token, apiCallback);
    }

    public void createCalendrier(UserCalendar calendrier, String token, ApiCallback<Boolean> apiCallback) {
        api.createCalendrier(calendrier, token, apiCallback);
    }

    public void updateCalendrier(UserCalendar calendrier, String token, ApiCallback<Boolean> apiCallback) {
        api.updateCalendrier(calendrier, token, apiCallback);
    }

    public void deleteCalendrier(UserCalendar userCalendar, String token, ApiCallback<Boolean> apiCallback) {
        api.deleteCalendrier(userCalendar, token, apiCallback);
    }
}