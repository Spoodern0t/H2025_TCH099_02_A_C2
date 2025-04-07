package com.example.multuscalendrius.modeles;

import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.modeles.entitees.User;

public class Modele {

    private User user = User.getInstance();
    private Calendrier calendrier = new Calendrier();
    public void setUser(User user) {
        this.user = user;
    }

    public void setCalendrier(Calendrier calendrier) {
        this.calendrier = calendrier;
    }
}