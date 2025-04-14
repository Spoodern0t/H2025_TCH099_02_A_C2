package com.example.multuscalendrius.modeles.dao;

import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.modeles.entitees.Evenement;
import com.example.multuscalendrius.vuemodele.ApiCallback;

// Créer un DAO pour tester la liste de calendrier
public class CalendrierDao {

    private static CalendrierDao instance = null;
    private final ApiService api;
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

    public void createEvenement(Evenement evenement, String token, ApiCallback<Boolean> apiCallback) {
        api.createEvenement(calendrier, evenement, token, apiCallback);
    }

    public void updateEvenement(Evenement evenement, String token, ApiCallback<Boolean> apiCallback) {
        api.updateEvenement(evenement, token, apiCallback);
    }

    public void deleteEvenement(Evenement evenement, String token, ApiCallback<Boolean> apiCallback) {
        api.deleteEvenement(evenement, token, apiCallback);
    }

    public void createElement(Element element, String token, ApiCallback<Boolean> apiCallback) {
        api.createElement(calendrier, element, token, apiCallback);
    }

    public void updateElement(Element element, String token, ApiCallback<Boolean> apiCallback) {
        api.updateElement(element, token, apiCallback);
    }

    public void deleteElement(Element element, String token, ApiCallback<Boolean> apiCallback) {
        api.deleteElement(element, token, apiCallback);
    }
}