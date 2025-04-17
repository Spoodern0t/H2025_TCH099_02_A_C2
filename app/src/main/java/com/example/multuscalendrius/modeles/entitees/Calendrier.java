package com.example.multuscalendrius.modeles.entitees;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Calendrier {

    @JsonProperty("id")
    private int id;
    @JsonProperty("nom")
    private String nom;
    @JsonProperty("description")
    private String description;
    @JsonProperty("auteur")
    private String auteur;
    @JsonProperty("element")
    private List<Element> elements = new ArrayList<>();
    @JsonProperty("evenement")
    private List<Evenement> evenements = new ArrayList<>();

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

    public Evenement getEvenementById(int id) {
        for (Evenement evenement: evenements) {
            if (evenement.getId() == id)
                return evenement;
        }
        return null;
    }

    public Element getElementById(int id) {
        for (Element element: elements) {
            if (element.getId() == id)
                return element;
        }
        return null;
    }

    public List<Element> getElementsByDate(LocalDate date) {
        List<Element> elementsFiltres = new ArrayList<>();
        for (Element element: elements) {
            boolean debutEgal = false;
            if (element.getDateDebut() != null) {
                LocalDate debut = element.getDateDebut().toLocalDate();
                debutEgal = debut.equals(date);
            }
            LocalDate fin = element.getDateFin().toLocalDate();
            if (debutEgal || fin.equals(date)) {
                elementsFiltres.add(element);
            }
        }
        return elementsFiltres;
    }
}