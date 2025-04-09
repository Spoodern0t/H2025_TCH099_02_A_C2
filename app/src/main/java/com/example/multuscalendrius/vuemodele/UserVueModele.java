package com.example.multuscalendrius.vuemodele;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.modeles.dao.UserDao;
import com.example.multuscalendrius.modeles.entitees.User;
import com.example.multuscalendrius.modeles.entitees.UserCalendar;

import java.util.List;

public class UserVueModele extends ViewModel {

    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<UserCalendar>> userCalendarsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> erreurLiveData = new MutableLiveData<>();
    private final UserDao userDao;
    private final ApiService api;

    public UserVueModele() {
        this.api = new ApiService();
        userDao = UserDao.getInstance();
    }

    public LiveData<User> getUser() {
        return userLiveData;
    }

    public LiveData<List<UserCalendar>> getUserCalendars() {
        return userCalendarsLiveData;
    }

    public LiveData<String> getErreur() {
        return erreurLiveData;
    }


    // Wrapper pour la connexion (login)
    public void syncLogin(String email, String password) {
        api.connexion(email, password, new ApiCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    userDao.setUser(user);
                    userDao.setOperation(UserDao.Operation.LOGIN);
                    userLiveData.postValue(user);
                } else {
                    userDao.setOperation(UserDao.Operation.ERREUR);
                    userDao.setErrorMessage("Réponse vide lors du login");
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                userDao.setOperation(UserDao.Operation.ERREUR);
                userDao.setErrorMessage(errorMsg);
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    // Wrapper pour récupérer les UserCalendar de l'utilisateur
    public void syncUserCalendars() {
        api.getUserCalendar(userDao.getUser().getToken(), new ApiCallback<List<UserCalendar>>() {
            @Override
            public void onSuccess(List<UserCalendar> result) {
                if (result != null) {
                    userDao.getUser().setUserCalendars(result);
                    userDao.setOperation(UserDao.Operation.FETCH_USER_CALENDARS);
                    userCalendarsLiveData.postValue(result);
                } else {
                    userDao.setOperation(UserDao.Operation.ERREUR);
                    userDao.setErrorMessage("Réponse vide lors de la récupération des userCalendars");
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                userDao.setOperation(UserDao.Operation.ERREUR);
                userDao.setErrorMessage(errorMsg);
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public String getUsername() {
        return userDao.getUser().getUsername();
    }
}
