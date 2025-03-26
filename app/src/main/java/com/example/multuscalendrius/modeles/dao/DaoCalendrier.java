package com.example.multuscalendrius.modeles.dao;

import com.example.multuscalendrius.modeles.entitees.Calendrier;

import java.util.*;

// Créer un DAO pour tester la liste de calendrier
public class DaoCalendrier {

    private static DaoCalendrier instance = null;
    private Calendrier[] calendriersPersonnels, calendriersPartages;


    // Créer un singleton
    public static DaoCalendrier getInstance() {
        if (instance == null)
            instance = new DaoCalendrier();
        return instance;
    }

    // Création de la liste hardcodé
    private DaoCalendrier() {
        List<Calendrier> calendrierListe = new ArrayList<>();
        long[] id = {0, 1, 2, 3, 4, 5};
        String[] noms = {"Calendrier 1", "Calendrier 2", "Calendrier 3", "Calendrier 4", "Calendrier 5", "Calendrier 6"};
        String[] description = {"1", "2", "3", "4", "5", "6"};

        for (int i = 0; i < noms.length; i++) {
            //calendrierListe.add(new Calendrier(id[i], noms[i], description[i], null, null));
        }

        calendriersPersonnels = calendrierListe.toArray(new Calendrier[0]);

        calendriersPartages = calendrierListe.toArray(new Calendrier[0]);
    }

    public Calendrier[] getCalendriersPersonnels() {
        return calendriersPersonnels;
    }

    public Calendrier[] getCalendriersPartages() {
        return calendriersPartages;
    }
}
