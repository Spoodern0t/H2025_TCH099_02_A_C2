package com.example.multuscalendrius.modeles.entitees;


import java.util.ArrayList;
import java.util.List;

public class Calendrier {
    private Long id;
    private String nom;
    private String description;
    private String auteur;
    private List<Element> elements;
    private List<Evenement> evenements;

    public Calendrier() {
        elements = new ArrayList<>();
        evenements = new ArrayList<>();
    }

    public Calendrier(Long id, String nom, String description, String auteur, List<Element> elements, List<Evenement> evenements) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.auteur = auteur;
        this.elements = elements;
        this.evenements = evenements;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAuteur() {
        return auteur;
    }
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    public List<Element> getElements() {
        return elements;
    }
    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
    public List<Evenement> getEvenements() {
        return evenements;
    }
    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }
}



