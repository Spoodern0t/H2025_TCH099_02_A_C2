package com.example.multuscalendrius.modeles.entitees;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.vuemodele.ApiCallback;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Calendrier {

    private int id;
    private String nom;
    private String description;
    private String auteur;
    private String token = null;
    private List<Element> elements;
    private List<Evenement> evenements;

    @JsonIgnore
    private ApiService apiService;

    @JsonIgnore
    private Operation operation;
    @JsonIgnore
    private String errorMessage;

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

    public Calendrier() {
        this.elements = new ArrayList<>();
        this.evenements = new ArrayList<>();
        this.apiService = new ApiService();
        this.operation = Operation.AUTRE;
    }

    // Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public List<Element> getElements() { return elements; }
    public void setElements(List<Element> elements) { this.elements = elements; }
    public List<Evenement> getEvenements() { return evenements; }
    public void setEvenements(List<Evenement> evenements) { this.evenements = evenements; }
    public Operation getOperation() { return operation; }
    public void setOperation(Operation operation) { this.operation = operation; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    // API wrappers avec callback de complétion

    public void fetchById(int id, Runnable onComplete) {
        apiService.getCalendrier(id, token, new ApiCallback<Calendrier>() {
            @Override
            public void onSuccess(Calendrier calendrier) {
                if (calendrier != null) {
                    setId(calendrier.getId());
                    setNom(calendrier.getNom());
                    setDescription(calendrier.getDescription());
                    setAuteur(calendrier.getAuteur());
                    setElements(calendrier.getElements());
                    setEvenements(calendrier.getEvenements());
                    setOperation(Operation.AUTRE);
                }
                onComplete.run();
            }
            @Override
            public void onFailure(String errorMsg) {
                setOperation(Operation.ERREUR);
                setErrorMessage(errorMsg);
                onComplete.run();
            }
        });
    }

    public void syncCreate(String leNom, String laDescription, Runnable onComplete) {
        apiService.createCalendrier(leNom, laDescription, token, new ApiCallback<Calendrier>() {
            @Override
            public void onSuccess(Calendrier result) {
                if (result != null) {
                    setId(result.getId());
                    setNom(result.getNom());
                    setDescription(result.getDescription());
                    setAuteur(result.getAuteur());
                    setOperation(Operation.CREATION);
                } else {
                    setOperation(Operation.ERREUR);
                    setErrorMessage("Réponse vide lors de la création");
                }
                onComplete.run();
            }
            @Override
            public void onFailure(String errorMsg) {
                setOperation(Operation.ERREUR);
                setErrorMessage(errorMsg);
                onComplete.run();
            }
        });
    }

    public void syncUpdate(String nom, String description, String auteur, Runnable onComplete) {
        apiService.updateCalendrier(this, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (Boolean.TRUE.equals(success)) {
                    setNom(nom);
                    setDescription(description);
                    setAuteur(auteur);
                    setOperation(Operation.MISE_A_JOUR);
                } else {
                    setOperation(Operation.ERREUR);
                    setErrorMessage("Échec de la mise à jour");
                }
                onComplete.run();
            }
            @Override
            public void onFailure(String errorMsg) {
                setOperation(Operation.ERREUR);
                setErrorMessage(errorMsg);
                onComplete.run();
            }
        });
    }

    public void syncDelete(Runnable onComplete) {
        apiService.deleteCalendrier(this, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (Boolean.TRUE.equals(success)) {
                    setId(0);
                    setNom(null);
                    setDescription(null);
                    setAuteur(null);
                    setElements(new ArrayList<>());
                    setEvenements(new ArrayList<>());
                    setOperation(Operation.SUPPRESSION);
                } else {
                    setOperation(Operation.ERREUR);
                    setErrorMessage("Échec de la suppression");
                }
                onComplete.run();
            }
            @Override
            public void onFailure(String errorMsg) {
                setOperation(Operation.ERREUR);
                setErrorMessage(errorMsg);
                onComplete.run();
            }
        });
    }

    public void addEvenement(String titre, String description, Runnable onComplete) {
        apiService.createEvenement(this, titre, description, token, new ApiCallback<Evenement>() {
            @Override
            public void onSuccess(Evenement evenement) {
                if (evenement != null) {
                    List<Evenement> liste = getEvenements();
                    liste.add(evenement);
                    setEvenements(liste);
                    setOperation(Operation.AJOUT_EVENEMENT);
                } else {
                    setOperation(Operation.ERREUR);
                    setErrorMessage("Événement non créé");
                }
                onComplete.run();
            }
            @Override
            public void onFailure(String errorMsg) {
                setOperation(Operation.ERREUR);
                setErrorMessage(errorMsg);
                onComplete.run();
            }
        });
    }

    public void updateEvenement(Evenement evenement, String titre, String description, Runnable onComplete) {
        apiService.updateEvenement(evenement, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (Boolean.TRUE.equals(success)) {
                    evenement.setTitre(titre);
                    evenement.setDescription(description);
                    setOperation(Operation.MISE_A_JOUR_EVENEMENT);
                } else {
                    setOperation(Operation.ERREUR);
                    setErrorMessage("Échec de la mise à jour de l'événement");
                }
                onComplete.run();
            }
            @Override
            public void onFailure(String errorMsg) {
                setOperation(Operation.ERREUR);
                setErrorMessage(errorMsg);
                onComplete.run();
            }
        });
    }

    public void deleteEvenement(Evenement evenement, Runnable onComplete) {
        apiService.deleteEvenement(evenement, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (Boolean.TRUE.equals(success)) {
                    List<Evenement> liste = getEvenements();
                    liste.remove(evenement);
                    setEvenements(liste);
                    setOperation(Operation.SUPPRESSION_EVENEMENT);
                } else {
                    setOperation(Operation.ERREUR);
                    setErrorMessage("Échec de la suppression de l'événement");
                }
                onComplete.run();
            }
            @Override
            public void onFailure(String errorMsg) {
                setOperation(Operation.ERREUR);
                setErrorMessage(errorMsg);
                onComplete.run();
            }
        });
    }

    public void addElement(String nom, String description, Evenement evenement, LocalDateTime dateDebut,
                           LocalDateTime dateFin, Runnable onComplete) {
        apiService.createElement(this, nom, description, evenement.getId(), dateDebut, dateFin, token, new ApiCallback<Element>() {
            @Override
            public void onSuccess(Element element) {
                if (element != null) {
                    List<Element> liste = getElements();
                    liste.add(element);
                    setElements(liste);
                    setOperation(Operation.AJOUT_ELEMENT);
                } else {
                    setOperation(Operation.ERREUR);
                    setErrorMessage("Élément non créé");
                }
                onComplete.run();
            }
            @Override
            public void onFailure(String errorMsg) {
                setOperation(Operation.ERREUR);
                setErrorMessage(errorMsg);
                onComplete.run();
            }
        });
    }

    public void updateElement(Element element, String nom, String description, int evenementId, Runnable onComplete) {
        apiService.updateElement(element, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (Boolean.TRUE.equals(success)) {
                    element.setNom(nom);
                    element.setDescription(description);
                    element.setEvenement(evenementId);
                    setOperation(Operation.MISE_A_JOUR_ELEMENT);
                } else {
                    setOperation(Operation.ERREUR);
                    setErrorMessage("Échec de la mise à jour de l'élément");
                }
                onComplete.run();
            }
            @Override
            public void onFailure(String errorMsg) {
                setOperation(Operation.ERREUR);
                setErrorMessage(errorMsg);
                onComplete.run();
            }
        });
    }

    public void deleteElement(Element element, Runnable onComplete) {
        apiService.deleteElement(element, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (Boolean.TRUE.equals(success)) {
                    List<Element> liste = getElements();
                    liste.remove(element);
                    setElements(liste);
                    setOperation(Operation.SUPPRESSION_ELEMENT);
                } else {
                    setOperation(Operation.ERREUR);
                    setErrorMessage("Échec de la suppression de l'élément");
                }
                onComplete.run();
            }
            @Override
            public void onFailure(String errorMsg) {
                setOperation(Operation.ERREUR);
                setErrorMessage(errorMsg);
                onComplete.run();
            }
        });
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

