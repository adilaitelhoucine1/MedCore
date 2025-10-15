package com.example.medcore.model;

import com.example.medcore.listener.MedecinSpecialisteListener;
import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("SPECIALISTE")
//@EntityListeners(MedecinSpecialisteListener.class)

public class MedecinSpecialiste extends Utilisateur {
    @Enumerated(EnumType.STRING)
    private Specialite specialite;
    private Double tarif;
    private Integer dureeConsultation;

    protected  MedecinSpecialiste(){}
    public MedecinSpecialiste(String nom, String prenom, String email, String motDePasse,Specialite specialite,Double tarif,Integer dureeConsultation) {
        super(nom,prenom,email,motDePasse);
        this.specialite=specialite;
        this.tarif=tarif;
        this.dureeConsultation=dureeConsultation;
    }

    @OneToMany(mappedBy = "medecinSpecialiste", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisponibiliteMedecin> disponibilites;


    public enum Specialite {
        CARDIOLOGIE, PNEUMOLOGIE
    }

    public Specialite getSpecialite() { return specialite; }
    public void setSpecialite(Specialite specialite) { this.specialite = specialite; }
    public Double getTarif() { return tarif; }
    public void setTarif(Double tarif) { this.tarif = tarif; }
    public Integer getDureeConsultation() { return dureeConsultation; }
    public void setDureeConsultation(Integer dureeConsultation) { this.dureeConsultation = dureeConsultation; }
  //  public List<Creneau> getCreneauxDisponibles() { return creneauxDisponibles; }
   // public void setCreneauxDisponibles(List<Creneau> creneauxDisponibles) { this.creneauxDisponibles = creneauxDisponibles; }

    public List<DisponibiliteMedecin> getDisponibilites() { return disponibilites; }
    public void setDisponibilites(List<DisponibiliteMedecin> disponibilites) { this.disponibilites = disponibilites; }
}