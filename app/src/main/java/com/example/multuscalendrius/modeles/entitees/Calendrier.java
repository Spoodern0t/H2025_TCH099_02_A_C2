package com.example.multuscalendrius.modeles.entitees;


import android.util.Log;

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
    private List<Element> elements;
    private List<Evenement> evenements;
    private ApiService apiService;

    // Champs intégrant la logique du wrapper (ignorés lors du mapping JSON)
    @JsonIgnore
    private Operation operation;
    @JsonIgnore
    private String errorMessage;

    // LiveData pour notifier l'UI de l'état et des opérations sur ce Calendrier
    private MutableLiveData<Calendrier> liveData;

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

    public Calendrier() {
        this.elements = new ArrayList<>();
        this.evenements = new ArrayList<>();
        this.apiService = new ApiService();
        this.liveData = new MutableLiveData<>();
        // Par défaut, aucune opération particulière n'est en cours
        this.operation = Operation.AUTRE;
        liveData.setValue(this);
    }

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

    // Getters pour la logique du wrapper (non mappés en JSON)
    public Operation getOperation() {
        return operation;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    // Exposition du LiveData pour l'observation depuis l'UI (Activity/Fragment)
    public LiveData<Calendrier> getLiveData() {
        return liveData;
    }

    // ----------- API WRAPPERS SANS notification du LiveData -----------
    public static void fetchById(int id, String token, ApiCallback<Calendrier> callback) {
        ApiService api = new ApiService();
        api.getCalendrier(id, token, new ApiCallback<Calendrier>() {
            @Override
            public void onSuccess(Calendrier calendrier) {
                callback.onSuccess(calendrier);
            }
            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    // ----------- API WRAPPERS avec notification via LiveData -----------
    public void syncCreate(String leNom, String laDescription, String leAuteur) {
        apiService.createCalendrier(leNom, laDescription, leAuteur, new ApiCallback<Calendrier>() {
            @Override
            public void onSuccess(Calendrier result) {
                if (result != null) {
                    id = result.getId();
                    nom = result.getNom();
                    description = result.getDescription();
                    auteur = result.getAuteur();
                    operation = Operation.CREATION;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Operation.ERREUR;
                    errorMessage = "Réponse vide lors de la création";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Operation.ERREUR;
                errorMessage = errorMsg;
                liveData.postValue(Calendrier.this);
            }
        });
    }

    public void syncUpdate(String leNom, String laDescription, String leAuteurString, String token) {
        apiService.updateCalendrier(this, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    nom = leNom;
                    description = laDescription;
                    auteur = leAuteurString;
                    operation = Operation.MISE_A_JOUR;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Operation.ERREUR;
                    errorMessage = "Échec de la mise à jour";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Operation.ERREUR;
                errorMessage = errorMsg;
                liveData.postValue(Calendrier.this);
            }
        });
    }

    public void syncDelete(String token) {
        apiService.deleteCalendrier(this, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    id = 0;
                    nom = null;
                    description = null;
                    auteur = null;
                    elements.clear();
                    evenements.clear();
                    operation = Operation.SUPPRESSION;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Operation.ERREUR;
                    errorMessage = "Échec de la suppression";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Operation.ERREUR;
                errorMessage = errorMsg;
                liveData.postValue(Calendrier.this);
            }
        });
    }

    public void addEvenement(String titre, String description, String token) {
        apiService.createEvenement(this, titre, description, token, new ApiCallback<Evenement>() {
            @Override
            public void onSuccess(Evenement evenement) {
                if (evenement != null) {
                    evenements.add(evenement);
                    operation = Operation.AJOUT_EVENEMENT;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Operation.ERREUR;
                    errorMessage = "Événement non créé";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Operation.ERREUR;
                errorMessage = errorMsg;
                liveData.postValue(Calendrier.this);
            }
        });
    }

    public void updateEvenement(Evenement evenement, String titre, String description, String token) {
        apiService.updateEvenement(evenement, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    evenement.setTitre(titre);
                    evenement.setDescription(description);
                    operation = Operation.MISE_A_JOUR_EVENEMENT;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Operation.ERREUR;
                    errorMessage = "Échec de la mise à jour de l'événement";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Operation.ERREUR;
                errorMessage = errorMsg;
                liveData.postValue(Calendrier.this);
            }
        });
    }

    public void deleteEvenement(Evenement evenement, String token) {
        apiService.deleteEvenement(evenement, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    evenements.remove(evenement);
                    operation = Operation.SUPPRESSION_EVENEMENT;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Operation.ERREUR;
                    errorMessage = "Échec de la suppression de l'événement";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Operation.ERREUR;
                errorMessage = errorMsg;
                liveData.postValue(Calendrier.this);
            }
        });
    }

    public void addElement(String nom, String description, Evenement evenement, LocalDateTime dateDebut,
                           LocalDateTime dateFin, String token) {
        apiService.createElement(this, nom, description, evenement, dateDebut, dateFin, token, new ApiCallback<Element>() {
            @Override
            public void onSuccess(Element element) {
                if (element != null) {
                    elements.add(element);
                    operation = Operation.AJOUT_ELEMENT;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Operation.ERREUR;
                    errorMessage = "Élément non créé";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Operation.ERREUR;
                errorMessage = errorMsg;
                liveData.postValue(Calendrier.this);
            }
        });
    }

    public void updateElement(Element element, String nom, String description, Evenement evenement, String token) {
        apiService.updateElement(element, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    element.setNom(nom);
                    element.setDescription(description);
                    element.setEvenement(evenement);
                    operation = Operation.MISE_A_JOUR_ELEMENT;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Operation.ERREUR;
                    errorMessage = "Échec de la mise à jour de l'élément";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Operation.ERREUR;
                errorMessage = errorMsg;
                liveData.postValue(Calendrier.this);
            }
        });
    }

    public void deleteElement(Element element, String token) {
        apiService.deleteElement(element, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    elements.remove(element);
                    operation = Operation.SUPPRESSION_ELEMENT;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Operation.ERREUR;
                    errorMessage = "Échec de la suppression de l'élément";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Operation.ERREUR;
                errorMessage = errorMsg;
                liveData.postValue(Calendrier.this);
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

