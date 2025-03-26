package com.example.multuscalendrius.modeles.entitees;

import java.time.LocalDateTime;


public class Deadline extends Element {
    private LocalDateTime deadlineDateTime;


    public Deadline(String id, String calendrierId, String nom, String description, Evenement evenement, LocalDateTime deadlineDateTime) {
        super(id, calendrierId, nom, description, evenement);
        this.deadlineDateTime = deadlineDateTime;
    }

    public LocalDateTime getDeadlineDateTime() {
        return deadlineDateTime;
    }

    public void setDeadlineDateTime(LocalDateTime deadlineDateTime) {
        this.deadlineDateTime = deadlineDateTime;
    }
}


