package com.example.multuscalendrius.vuemodele;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multuscalendrius.modeles.dao.CalendrierDao;
import com.example.multuscalendrius.modeles.dao.UserDao;
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.modeles.entitees.Evenement;

public class CalendrierVueModele extends ViewModel {

    private final MutableLiveData<Calendrier> calendrierLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> succesLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> erreurLiveData = new MutableLiveData<>();
    private final String token = UserDao.getInstance().getUser().getToken();;
    public final CalendrierDao calendrierDao = CalendrierDao.getInstance();

    public LiveData<Calendrier> getCalendrier() {
        return calendrierLiveData;
    }

    public LiveData<Boolean> getSucces() {
        return succesLiveData;
    }

    public LiveData<String> getErreur() {
        return erreurLiveData;
    }

    public Calendrier getCurrentCalendrier() {
        return calendrierDao.getCalendrier();
    }

    // ----------- API WRAPPERS avec notification via LiveData -----------
    public void chargerCalendrier(int id) {
        calendrierDao.chargerCalendrier(id, token, new ApiCallback<>() {
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

    public void addEvenement(Evenement evenement) {
        calendrierDao.createEvenement(evenement, token, new ApiCallback<>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success) {
                    succesLiveData.postValue(true);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void updateEvenement(Evenement evenement) {
        calendrierDao.updateEvenement(evenement, token, new ApiCallback<>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success) {
                    succesLiveData.postValue(true);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void deleteEvenement(Evenement evenement) {
        calendrierDao.deleteEvenement(evenement, token, new ApiCallback<>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success) {
                    succesLiveData.postValue(true);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void addElement(Element element) {
        calendrierDao.createElement(element, token, new ApiCallback<>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success) {
                    succesLiveData.postValue(true);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void updateElement(Element element) {
        calendrierDao.updateElement(element, token, new ApiCallback<>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success) {
                    succesLiveData.postValue(true);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void deleteElement(Element element) {
        calendrierDao.deleteElement(element, token, new ApiCallback<>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success) {
                    succesLiveData.postValue(true);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }
}