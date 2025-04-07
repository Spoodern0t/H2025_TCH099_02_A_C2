package com.example.multuscalendrius.modeles.entitees;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.vuemodele.ApiCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User implements Serializable{
    // Singleton instance
    private static User instance;

    private Long id;
    private String email;
    private String password;
    private String username;
    private String token;
    private List<UserCalendar> userCalendars;
    private ApiService api;
    private MutableLiveData<User> liveData;

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
    private User() {
        this.userCalendars = new ArrayList<>();
        this.api = new ApiService();
        this.liveData = new MutableLiveData<>();
        this.operation = Operation.AUTRE;
        liveData.setValue(this);
    }

    // Méthode d'accès à l'instance unique
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
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

    public Operation getOperation() { return operation; }
    public String getErrorMessage() { return errorMessage; }

    // Exposition du LiveData pour l'observation depuis l'UI (Activity/Fragment)
    public LiveData<User> getLiveData() {
        return liveData;
    }

    // ----------- API WRAPPERS AVEC LIVE DATA -----------

    // Wrapper pour la connexion (login)
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
                    operation = Operation.LOGIN;
                    liveData.postValue(User.this);
                } else {
                    operation = Operation.ERREUR;
                    errorMessage = "Réponse vide lors du login";
                    liveData.postValue(User.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Operation.ERREUR;
                errorMessage = errorMsg;
                liveData.postValue(User.this);
            }
        });
    }

    // Wrapper pour récupérer les UserCalendar de l'utilisateur
    public void syncUserCalendars(String tokenParam) {
        api.getUserCalendar(tokenParam, new ApiCallback<List<UserCalendar>>() {
            @Override
            public void onSuccess(List<UserCalendar> result) {
                if (result != null) {
                    setUserCalendars(result);
                    operation = Operation.FETCH_USER_CALENDARS;
                    liveData.postValue(User.this);
                } else {
                    operation = Operation.ERREUR;
                    errorMessage = "Réponse vide lors de la récupération des userCalendars";
                    liveData.postValue(User.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Operation.ERREUR;
                errorMessage = errorMsg;
                liveData.postValue(User.this);
            }
        });
    }
}



