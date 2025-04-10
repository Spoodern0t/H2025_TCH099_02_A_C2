package com.example.multuscalendrius.vuemodele;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multuscalendrius.modeles.dao.UserDao;
import com.example.multuscalendrius.modeles.entitees.User;
import com.example.multuscalendrius.modeles.entitees.UserCalendar;

import java.util.List;

public class UserVueModele extends ViewModel {

    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<UserCalendar>> userCalendarsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> erreurLiveData = new MutableLiveData<>();
    private final UserDao userDao;

    public UserVueModele() {
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
        userDao.connexion(email, password, new ApiCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    userDao.setUser(user);
                    user.setOperation(User.Operation.LOGIN);
                    userLiveData.postValue(user);
                } /* else {
                    userDao.setOperation(UserDao.Operation.ERREUR);
                    userDao.setErrorMessage("Réponse vide lors du login");
                }*/
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    // Wrapper pour la connexion (login)
    public void decoUser() {
        userDao.decoUser(userDao.getUser().getToken(), new ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    User user = userDao.getUser();
                    userDao.setUser(user);
                    user.setOperation(User.Operation.LOGIN);
                    userLiveData.postValue(user);
                } /* else {
                    userDao.setOperation(UserDao.Operation.ERREUR);
                    userDao.setErrorMessage("Réponse vide lors du login");
                }*/
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    // Wrapper pour récupérer les UserCalendar de l'utilisateur
    public void syncUserCalendars() {
        userDao.getUserCalendar(userDao.getUser().getToken(), new ApiCallback<List<UserCalendar>>() {
            @Override
            public void onSuccess(List<UserCalendar> calendriers) {
                if (!calendriers.isEmpty()) {
                    User user = userDao.getUser();
                    user.setUserCalendars(calendriers);
                    user.setOperation(User.Operation.FETCH_USER_CALENDARS);
                    userCalendarsLiveData.postValue(calendriers);
                } /* else {
                    userDao.setOperation(UserDao.Operation.ERREUR);
                    userDao.setErrorMessage("Réponse vide lors de la récupération des userCalendars");
                } */
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public String getUsername() {
        return userDao.getUser().getUsername();
    }
}
