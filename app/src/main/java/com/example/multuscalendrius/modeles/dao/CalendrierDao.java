package com.example.multuscalendrius.modeles.dao;

import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.vuemodele.ApiCallback;
import com.example.multuscalendrius.vuemodele.CalendrierVueModele;

// Créer un DAO pour tester la liste de calendrier
public class CalendrierDao {

    private static CalendrierDao instance = null;
    private ApiService api;
    private Calendrier calendrier;


    public static CalendrierDao getInstance() {
        if (instance == null) {
            instance = new CalendrierDao();
        }
        return instance;
    }

    private CalendrierDao() {
        this.api = new ApiService();
    }

    public Calendrier getCalendrier() {
        return calendrier;
    }

    public void setCalendrier(Calendrier calendrier) {
        this.calendrier = calendrier;
    }

    public void chargerCalendrier(int id, String token, ApiCallback<Calendrier> apiCallback) {
        api.getCalendrier(id, token, apiCallback);
    }

    public void createCalendrier(String nom, String description, String token, ApiCallback<Calendrier> apiCallback) {
        api.createCalendrier(nom, description, token, apiCallback);
    }

    public void updateCalendrier(Calendrier calendrier, String token, ApiCallback<Boolean> apiCallback) {
        api.updateCalendrier(calendrier, token, apiCallback);
    }
}
