package com.example.multuscalendrius.modeles.entitees;

import java.time.LocalDateTime;


public class Evenement {
    // Identifiant unique de l'événement
    private String id;
    // Titre de l'événement (ex. "Réunion d'équipe")
    private String titre;
    // Description détaillée de l'événement
    private String description;
    // Date et heure de début de l'événement
    private LocalDateTime startTime;
    // Date et heure de fin de l'événement
    private LocalDateTime endTime;
    // Type d'événement (ex. "Réunion", "Cours", "Rendez-vous")
    private String type;
    // Niveau de priorité de l'événement (par exemple, de 1 à 9)
    private int priority;
    // Indique si l'événement est terminé (true) ou non (false)
    private boolean completed;

    // Constructeur par défaut (nécessaire pour la désérialisation JSON)
    public Evenement() {
    }

    // Constructeur complet
    public Evenement(String id, String titre, String description, LocalDateTime startTime, LocalDateTime endTime, String type, int priority, boolean completed) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.priority = priority;
        this.completed = completed;
    }

    // Getters et Setters

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
