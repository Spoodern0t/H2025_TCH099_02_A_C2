package com.example.multuscalendrius.modeles.entitees;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Calendrier {

    private int id;
    private String nom;
    private String description;
    private String auteur;
    private List<Element> elements;
    private List<Evenement> evenements;

    // Enumération pour identifier l'opération effectuée
    public enum Operation {
        CREATION,
        MISE_A_JOUR,
        SUPPRESSION,
        AJOUT_EVENEMENT,
        MISE_A_JOUR_EVENEMENT,
        SUPPRESSION_EVENEMENT,
        AJOUT_ELEMENT,
        MISE_A_JOUR_ELEMENT,
        SUPPRESSION_ELEMENT,
        ERREUR,
        AUTRE
    }

    @JsonIgnore
    private Calendrier.Operation operation;
    @JsonIgnore
    private String errorMessage;


    // Getters & Setters classiques pour le mapping JSON
    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public List<Element> getElements() { return elements; }
    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public List<Evenement> getEvenements() { return evenements; }
    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    public void setOperation(Calendrier.Operation operation) {
        this.operation = operation;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Calendrier.Operation getOperation() {
        return operation;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public Evenement getEvenementById(int id) {
        for (Evenement evenement: evenements) {
            if (evenement.getId() == id)
                return evenement;
        }
        return null;
    }

    public Element getElementById(int elementId) {
        for (Element element: elements) {
            if (element.getId() == id)
                return element;
        }
        return null;
    }
}

    /*
    calendrier = new Calendrier();

        // Observation du LiveData pour être notifié des mises à jour
        calendrier.getLiveData().observe(this, new Observer<Calendrier>() {
            @Override
            public void onChanged(Calendrier updatedCalendrier) {
                if (updatedCalendrier != null) {
                    switch (updatedCalendrier.getOperation()) {
                        case CREATION:
                            statusTextView.setText("Calendrier créé: " + updatedCalendrier.getNom());
                            break;
                        case MISE_A_JOUR:
                            statusTextView.setText("Calendrier mis à jour: " + updatedCalendrier.getNom());
                            break;
                        case SUPPRESSION:
                            statusTextView.setText("Calendrier supprimé");
                            break;
                        case AJOUT_EVENEMENT:
                            statusTextView.setText("Événement ajouté");
                            break;
                        case MISE_A_JOUR_EVENEMENT:
                            statusTextView.setText("Événement mis à jour");
                            break;
                        case SUPPRESSION_EVENEMENT:
                            statusTextView.setText("Événement supprimé");
                            break;
                        case AJOUT_ELEMENT:
                            statusTextView.setText("Élément ajouté");
                            break;
                        case MISE_A_JOUR_ELEMENT:
                            statusTextView.setText("Élément mis à jour");
                            break;
                        case SUPPRESSION_ELEMENT:
                            statusTextView.setText("Élément supprimé");
                            break;
                        case ERREUR:
                            statusTextView.setText("Erreur: " + updatedCalendrier.getErrorMessage());
                            break;
                        case AUTRE:
                        default:
                            statusTextView.setText("Opération non spécifiée");
                            break;
                    }
                } else {
                    statusTextView.setText("Aucun calendrier disponible");
                }
     */

