package com.example.multuscalendrius.modeles.dao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DaoJour {

    private static DaoJour instance = null;
    private List<String> jours = new ArrayList<>();


    // Créer un singleton
    public static DaoJour getInstance() {
        if (instance == null)
            instance = new DaoJour();
        return instance;
    }

    // Création de la liste hardcodé
    private DaoJour() {
        for (int i=1; i < 31; i++) {
            DecimalFormat formatter = new DecimalFormat("00");
            String jour = formatter.format(i);
            jours.add(jour);
        }
    }

    public List<String> getJours() {
        return jours;
    }
}
