package com.example.multuscalendrius.vuemodele;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multuscalendrius.modeles.dao.CalendrierDao;
import com.example.multuscalendrius.modeles.dao.UserDao;
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.modeles.entitees.Evenement;

import java.time.LocalDateTime;

public class CalendrierVueModele extends ViewModel {

    private MutableLiveData<Calendrier> calendrierLiveData = new MutableLiveData<>();
    private MutableLiveData<Calendrier> succesLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> erreurLiveData = new MutableLiveData<>();
    private final String token;
    public CalendrierDao calendrierDao;

    public CalendrierVueModele() {
        calendrierDao = CalendrierDao.getInstance();
        token = UserDao.getInstance().getUser().getToken();
    }

    public LiveData<Calendrier> getCalendrier() {
        return calendrierLiveData;
    }
    public LiveData<String> getErreur() {
        return erreurLiveData;
    }

    // ----------- API WRAPPERS SANS notification du LiveData -----------
    public void fetchById(int id) {
        calendrierDao.chargerCalendrier(id, token, new ApiCallback<Calendrier>() {
            @Override
            public void onSuccess(Calendrier calendrier) {
                calendrierDao.setCalendrier(calendrier);
                calendrierLiveData.postValue(calendrier);
            }
            @Override
            public void onFailure(String errorMessage) {
                erreurLiveData.postValue(errorMessage);
            }
        });
    }

    // ----------- API WRAPPERS avec notification via LiveData -----------
    public void syncCreate(String leNom, String laDescription) {
        calendrierDao.createCalendrier(leNom, laDescription, token, new ApiCallback<Calendrier>() {
            @Override
            public void onSuccess(Calendrier calendrier) {
                if (calendrier != null) {
                    calendrier.setOperation(Calendrier.Operation.CREATION);
                    calendrierLiveData.postValue(calendrier);
                } /*else {
                    calendrier.setOperation(Calendrier.Operation.ERREUR);
                    errorMessage = "Réponse vide lors de la création";
                    liveData.postValue(Calendrier.this);
                }*/
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    /*
    public void syncUpdate(String leNom, String laDescription, String leAuteurString) {
        calendrierDao.updateCalendrier(calendrier, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    nom = leNom;
                    description = laDescription;
                    auteur = leAuteurString;
                    operation = Calendrier.Operation.MISE_A_JOUR;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Calendrier.Operation.ERREUR;
                    errorMessage = "Échec de la mise à jour";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Calendrier.Operation.ERREUR;
                errorMessage = errorMsg;
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void syncDelete() {
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
                    operation = Calendrier.Operation.SUPPRESSION;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Calendrier.Operation.ERREUR;
                    errorMessage = "Échec de la suppression";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Calendrier.Operation.ERREUR;
                errorMessage = errorMsg;
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void addEvenement(String titre, String description) {
        apiService.createEvenement(this, titre, description, token, new ApiCallback<Evenement>() {
            @Override
            public void onSuccess(Evenement evenement) {
                if (evenement != null) {
                    evenements.add(evenement);
                    operation = Calendrier.Operation.AJOUT_EVENEMENT;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Calendrier.Operation.ERREUR;
                    errorMessage = "Événement non créé";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Calendrier.Operation.ERREUR;
                errorMessage = errorMsg;
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void updateEvenement(Evenement evenement, String titre, String description) {
        apiService.updateEvenement(evenement, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    evenement.setTitre(titre);
                    evenement.setDescription(description);
                    operation = Calendrier.Operation.MISE_A_JOUR_EVENEMENT;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Calendrier.Operation.ERREUR;
                    errorMessage = "Échec de la mise à jour de l'événement";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Calendrier.Operation.ERREUR;
                errorMessage = errorMsg;
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void deleteEvenement(Evenement evenement) {
        apiService.deleteEvenement(evenement, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    evenements.remove(evenement);
                    operation = Calendrier.Operation.SUPPRESSION_EVENEMENT;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Calendrier.Operation.ERREUR;
                    errorMessage = "Échec de la suppression de l'événement";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Calendrier.Operation.ERREUR;
                errorMessage = errorMsg;
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void addElement(String nom, String description, Evenement evenement, LocalDateTime dateDebut,
                           LocalDateTime dateFin) {
        apiService.createElement(this, nom, description, evenement.getId(), dateDebut, dateFin, token, new ApiCallback<Element>() {
            @Override
            public void onSuccess(Element element) {
                if (element != null) {
                    elements.add(element);
                    operation = Calendrier.Operation.AJOUT_ELEMENT;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Calendrier.Operation.ERREUR;
                    errorMessage = "Élément non créé";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Calendrier.Operation.ERREUR;
                errorMessage = errorMsg;
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void updateElement(Element element, String nom, String description, Evenement evenement) {
        apiService.updateElement(element, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    element.setNom(nom);
                    element.setDescription(description);
                    element.setEvenement(evenement);
                    operation = Calendrier.Operation.MISE_A_JOUR_ELEMENT;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Calendrier.Operation.ERREUR;
                    errorMessage = "Échec de la mise à jour de l'élément";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Calendrier.Operation.ERREUR;
                errorMessage = errorMsg;
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void deleteElement(Element element) {
        apiService.deleteElement(element, token, new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success != null && success) {
                    elements.remove(element);
                    operation = Calendrier.Operation.SUPPRESSION_ELEMENT;
                    liveData.postValue(Calendrier.this);
                } else {
                    operation = Calendrier.Operation.ERREUR;
                    errorMessage = "Échec de la suppression de l'élément";
                    liveData.postValue(Calendrier.this);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                operation = Calendrier.Operation.ERREUR;
                errorMessage = errorMsg;
                erreurLiveData.postValue(errorMsg);
            }
        });
    }
     */
}
