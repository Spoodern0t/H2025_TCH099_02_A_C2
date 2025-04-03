package com.example.multuscalendrius.modeles.entitees;

import android.util.Log;

import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.vuemodele.ApiCallback;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String email;
    private String password;
    private String username;
    private String token;
    private List<UserCalendar> userCalendars;
    private ApiService api;

    public User() {
        this.userCalendars = new ArrayList<>();
        this.api = new ApiService();
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
    // ----------- API WRAPPERS -----------

    public void syncUserCalendars(String token) {
        api.getUserCalendar(token, new ApiCallback<List<UserCalendar>>() {
            @Override
            public void onSuccess(List<UserCalendar> result) {
                if (result != null) {
                    setUserCalendars(result);
                    Log.d("User", "Récupération des userCalendars réussie: " + result.size() + " éléments");
                } else {
                    Log.e("User", "Réponse vide lors de la récupération des userCalendars");
                }
            }
            @Override
            public void onFailure(String errorMessage) {
                Log.e("User", "Erreur lors de la récupération des userCalendars: " + errorMessage);
            }
        });
    }


    public void syncLogin(String email, String password) {

        api.connexion(email, password, new ApiCallback<User>() {
            @Override
            public void onSuccess(User result) {
                if (result != null) {
                    setId(result.getId());
                    setEmail(result.getEmail());
                    setPassword(result.getPassword());
                    setUsername(result.getUsername());
                    setToken(result.getToken());
                    setUserCalendars(result.getUserCalendars());
                    Log.d("User", "Login réussi: ID=" + id);
                } else {
                    Log.e("User", "Réponse vide lors du login");
                }
            }
            @Override
            public void onFailure(String errorMessage) {
                Log.e("User", "Erreur lors du login: " + errorMessage);
            }
        });
    }


}


