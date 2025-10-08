package com.example.medcore.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("GENERALISTE")

public class MedecinGeneraliste extends Utilisateur {
    @OneToMany(mappedBy = "medecinGeneraliste")
    private List<Consultation> consultations;

    protected  MedecinGeneraliste(){}
    public MedecinGeneraliste(String nom, String prenom, String email, String motDePasse, List<Consultation> consultations) {
        super(nom,prenom,email,motDePasse);
      this.consultations = new ArrayList<>();
    }
    public List<Consultation> getConsultations() { return consultations; }
    public void setConsultations(List<Consultation> consultations) { this.consultations = consultations; }
}