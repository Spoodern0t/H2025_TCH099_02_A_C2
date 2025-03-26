package com.example.multuscalendrius.modeles.entitees;

import java.time.LocalDateTime;

public class Periode extends Element {
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;



    public Periode(String id,String calendrierId, String nom, String description, Evenement evenement, LocalDateTime dateDebut, LocalDateTime dateFin) {
        super(id, calendrierId, nom, description, evenement);
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }
}
