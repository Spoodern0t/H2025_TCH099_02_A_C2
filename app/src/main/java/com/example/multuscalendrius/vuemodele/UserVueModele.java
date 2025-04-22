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
    private final MutableLiveData<Boolean> succesLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> erreurLiveData = new MutableLiveData<>();
    private final UserDao userDao = UserDao.getInstance();

    public LiveData<User> getUser() {
        return userLiveData;
    }

    public LiveData<List<UserCalendar>> getUserCalendars() {
        return userCalendarsLiveData;
    }

    public LiveData<Boolean> getSucces() {
        return succesLiveData;
    }

    public LiveData<String> getErreur() {
        return erreurLiveData;
    }

    public User getCurrentUser() {
        return userDao.getUser();
    }


    // Wrapper pour la connexion (login)
    public void syncLogin(String email, String password) {
        userDao.connexion(email, password, new ApiCallback<>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    userDao.setUser(user);
                    userLiveData.postValue(user);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    // Wrapper pour la connexion (login)
    public void decoUser() {
        userDao.decoUser(userDao.getUser().getToken(), new ApiCallback<>() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    succesLiveData.postValue(true);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    // Wrapper pour récupérer les UserCalendar de l'utilisateur
    public void chargerUserCalendars() {

        userDao.getUserCalendar(userDao.getUser().getToken(), new ApiCallback<>() {
            @Override
            public void onSuccess(List<UserCalendar> calendriers) {
                if (!calendriers.isEmpty()) {
                    User user = userDao.getUser();
                    user.setUserCalendars(calendriers);
                    userCalendarsLiveData.postValue(calendriers);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void creerCalendrier(UserCalendar userCalendar) {
        userDao.createCalendrier(userCalendar, userDao.getUser().getToken(), new ApiCallback<>() {
            @Override
            public void onSuccess(Boolean succes) {
                if (succes) {
                    succesLiveData.postValue(true);
                }
            }
            @Override
            public void onFailure(String errorMsg) {
                erreurLiveData.postValue(errorMsg);
            }
        });
    }

    public void updateCalendrier(UserCalendar userCalendar) {
        userDao.updateCalendrier(userCalendar, userDao.getUser().getToken(), new ApiCallback<>() {
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

    public void deleteCalendrier(UserCalendar userCalendar) {
        userDao.deleteCalendrier(userCalendar, userDao.getUser().getToken(), new ApiCallback<>() {
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
