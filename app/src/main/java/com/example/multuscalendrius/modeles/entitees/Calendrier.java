package com.example.multuscalendrius.modeles.entitees;

import android.util.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Calendrier {
    private Long id;
    private String nom;
    private String description;
    private String auteur;
    private List<Element> elements;
    private List<Evenement> evenements;
    private ApiService apiService;

    public Calendrier() {
        this.elements = new ArrayList<>();
        this.evenements = new ArrayList<>();
        this.apiService = new ApiService();
    }


    // Getters & Setters
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

    // ----------- API WRAPPERS -----------

    public static void fetchById(int id, ApiCallback<Calendrier> callback) {
        ApiService api = new ApiService();
        api.getCalendrier(id, new ApiCallback<Calendrier>() {
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

    /* COMMENT UTILISER LA FCT EN HAUT

    Calendrier.fetchById(123L, new ApiCallback<Calendrier>() {
        @Override
        public void onSuccess(Calendrier calendrier) {
            // Utilise le calendrier ici
        }

        @Override
        public void onFailure(String errorMessage) {
            // Gère l'erreur
        }
    });

     */





    // ----------- API WRAPPERS -----------

    public void syncCreate(String leNom, String laDescription, String leAuteur) {


        apiService.createCalendrier(leNom, laDescription, leAuteur, new ApiCallback<Calendrier>() {
            @Override
            public void onSuccess(Calendrier result) {
                if (result != null) {
                    id = result.getId();
                    nom = result.getNom();
                    description = result.getDescription();
                    auteur = result.getAuteur();
                    Log.d("Calendrier", "Création réussie: ID=" + id);
                } else {
                    Log.e("Calendrier", "Réponse vide lors de la création");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Calendrier", "Erreur lors de la création: " + errorMessage);
            }
        });
    }

    public void syncUpdate(String leNom, String laDescription, String leAuteurString) {
        this.setAuteur(leAuteurString);
        this.setDescription(laDescription);
        this.setNom(leNom);
        apiService.updateCalendrier(this, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    Log.d("Calendrier", "Mise à jour réussie");
                } else {
                    Log.e("Calendrier", "Échec de la mise à jour");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Calendrier", "Erreur API: " + errorMessage);
            }
        });
    }

    public void syncDelete() {
        apiService.deleteCalendrier(this, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    id = null;
                    nom = null;
                    description = null;
                    auteur = null;
                    elements.clear();
                    evenements.clear();
                    Log.d("Calendrier", "Suppression réussie");

                } else {
                    Log.e("Calendrier", "Échec de la suppression");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Calendrier", "Erreur API: " + errorMessage);
            }
        });
    }

    public void addEvenement(String titre, String description) {
        apiService.createEvenement(this, titre, description, new ApiCallback<Evenement>() {
            @Override
            public void onSuccess(Evenement evenement) {
                if (evenement != null) {
                    evenements.add(evenement);
                    Log.d("Calendrier", "Événement ajouté");
                } else {
                    Log.e("Calendrier", "Événement non créé");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Calendrier", "Erreur lors de l'ajout de l'événement: " + errorMessage);
            }
        });
    }

    public void updateEvenement(Evenement evenement, String titre, String description) {
        evenement.setTitre(titre);
        evenement.setDescription(description);
        apiService.updateEvenement(evenement, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    Log.d("Calendrier", "Événement mis à jour");
                } else {
                    Log.e("Calendrier", "Échec de la mise à jour de l'événement");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Calendrier", "Erreur API: " + errorMessage);
            }
        });
    }

    public void deleteEvenement(Evenement evenement) {
        apiService.deleteEvenement(evenement, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    evenements.remove(evenement);
                    Log.d("Calendrier", "Événement supprimé");
                } else {
                    Log.e("Calendrier", "Échec de la suppression de l'événement");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Calendrier", "Erreur API: " + errorMessage);
            }
        });
    }

    public void addElement(String nom, String description, Evenement evenement, LocalDateTime dateDebut,
                           LocalDateTime dateFin) {
        apiService.createElement(this, nom, description, evenement, dateDebut, dateFin, new ApiCallback<Element>() {
            @Override
            public void onSuccess(Element element) {
                if (element != null) {
                    elements.add(element);
                    Log.d("Calendrier", "Élément ajouté");
                } else {
                    Log.e("Calendrier", "Élément non créé");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Calendrier", "Erreur lors de la création de l'élément: " + errorMessage);
            }
        });
    }

    public void updateElement(Element element,String nom, String description, Evenement evenement ) {
        element.setNom(nom);
        element.setDescription(description);
        element.setEvenement(evenement);
        apiService.updateElement(element, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    Log.d("Calendrier", "Élément mis à jour");
                } else {
                    Log.e("Calendrier", "Échec de la mise à jour de l'élément");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Calendrier", "Erreur API: " + errorMessage);
            }
        });
    }

    public void deleteElement(Element element) {
        apiService.deleteElement(element, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    elements.remove(element);
                    Log.d("Calendrier", "Élément supprimé");
                } else {
                    Log.e("Calendrier", "Échec de la suppression de l'élément");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Calendrier", "Erreur API: " + errorMessage);
            }
        });
    }
}
