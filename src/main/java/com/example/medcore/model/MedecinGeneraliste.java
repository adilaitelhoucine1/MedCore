package com.example.medcore.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class MedecinGeneraliste extends Utilisateur {
    @OneToMany(mappedBy = "medecinGeneraliste")
    private List<Consultation> consultations;

    public List<Consultation> getConsultations() { return consultations; }
    public void setConsultations(List<Consultation> consultations) { this.consultations = consultations; }
}