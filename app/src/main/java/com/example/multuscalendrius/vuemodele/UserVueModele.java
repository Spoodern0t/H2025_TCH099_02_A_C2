package com.example.multuscalendrius.vuemodele;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.modeles.dao.UserInstance;
import com.example.multuscalendrius.modeles.entitees.User;
import com.example.multuscalendrius.modeles.entitees.UserCalendar;

import java.util.List;

public class UserVueModele extends ViewModel {

    //ATTRIBUTES//
    private final ApiService api = new ApiService();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<UserCalendar>> userCalendarsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> erreurLiveData = new MutableLiveData<>();
    private final UserInstance userInstance;

    //CONSTRUCTOR//
    public UserVueModele() {
        userInstance = UserInstance.getInstance();
    }

    //LIVE DATA'S//
    public LiveData<User> getUser() {
        return userLiveData;
    }

    public LiveData<List<UserCalendar>> getUserCalendars() {
        return userCalendarsLiveData;
    }

    public LiveData<String> getErreur() {return erreurLiveData;}


    //RETOURNE INSTANCE DU USER
    public User getUserInstance() {return userInstance.getUser();}

    // Wrapper pour la connexion (login)
    public void syncLogin(String email, String password) {
        api.connexion(email, password, new ApiCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    userInstance.setUser(user);
                    userLiveData.postValue(user);
                } else {
                    erreurLiveData.postValue("Réponse vide lors du login");
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    // Wrapper pour la de-connexion (decoUser)
    public void decoUser() {
        api.decoUser(userInstance.getUser().getToken(), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    User user = userInstance.getUser();
                    userInstance.setUser(user);
                    userLiveData.postValue(user);
                } else {
                    erreurLiveData.postValue("Réponse vide lors du login");
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    // Wrapper pour récupérer les UserCalendar de l'utilisateur
    public void syncUserCalendars() {
        api.getUserCalendar(userInstance.getUser().getToken(), new ApiCallback<List<UserCalendar>>() {
            @Override
            public void onSuccess(List<UserCalendar> calendriers) {
                if (!calendriers.isEmpty()) {
                    User user = userInstance.getUser();
                    user.setUserCalendars(calendriers);
                    userCalendarsLiveData.postValue(calendriers);
                } else {
                    erreurLiveData.postValue("Réponse vide lors de la récupération des userCalendars");
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

}
