package com.example.medcore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SignesVitaux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime dateSaisie;
    private String tension;
    private Integer frequenceCardiaque;
    private Double temperature;
    private Integer frequenceRespiratoire;
    private Double poids;
    private Double taille;

    protected   SignesVitaux(){}
    public SignesVitaux(Patient patient,String tension,Integer frequenceCardiaque,Double temperature,Integer frequenceRespiratoire,Double poids,Double taille){
        this.patient = patient;
        this.dateSaisie=LocalDateTime.now();
        this.tension=tension;
        this.frequenceCardiaque=frequenceCardiaque;
        this.temperature=temperature;
        this.poids=poids;
        this.taille=taille;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Patient getpatient() { return patient; }
    public void setDossierMedical(Patient patient) { this.patient = patient; }
    public LocalDateTime getDateSaisie() { return dateSaisie; }
    public void setDateSaisie(LocalDateTime dateSaisie) { this.dateSaisie = dateSaisie; }
    public String getTension() { return tension; }
    public void setTension(String tension) { this.tension = tension; }
    public Integer getFrequenceCardiaque() { return frequenceCardiaque; }
    public void setFrequenceCardiaque(Integer frequenceCardiaque) { this.frequenceCardiaque = frequenceCardiaque; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Integer getFrequenceRespiratoire() { return frequenceRespiratoire; }
    public void setFrequenceRespiratoire(Integer frequenceRespiratoire) { this.frequenceRespiratoire = frequenceRespiratoire; }
    public Double getPoids() { return poids; }
    public void setPoids(Double poids) { this.poids = poids; }
    public Double getTaille() { return taille; }
    public void setTaille(Double taille) { this.taille = taille; }
}