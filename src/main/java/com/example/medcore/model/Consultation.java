package com.example.medcore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private MedecinGeneraliste medecinGeneraliste;

    private LocalDateTime dateConsultation;
    private String motif;
    private String observations;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String diagnostic;
    private String traitement;
    private Double cout;

    @OneToMany(mappedBy = "consultation")
    private List<ActeTechnique> actesTechniques;

    public enum Status {
        EN_ATTENTE, TERMINEE, EN_ATTENTE_AVIS_SPECIALISTE
    }
    protected  Consultation(){}
    public  Consultation(Patient patient, MedecinGeneraliste medecinGeneraliste,String motif,String observations,String diagnostic,String traitement,Double cout){
        this.patient=patient;
        this.medecinGeneraliste=medecinGeneraliste;
        this.dateConsultation=LocalDateTime.now();
        this.motif=motif;
        this.observations=observations;
        this.diagnostic=diagnostic;
        this.traitement=traitement;
        this.cout=cout;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public MedecinGeneraliste getMedecinGeneraliste() { return medecinGeneraliste; }
    public void setMedecinGeneraliste(MedecinGeneraliste medecinGeneraliste) { this.medecinGeneraliste = medecinGeneraliste; }
    public LocalDateTime getDateConsultation() { return dateConsultation; }
    public void setDateConsultation(LocalDateTime dateConsultation) { this.dateConsultation = dateConsultation; }
    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }
    public String getTraitement() { return traitement; }
    public void setTraitement(String traitement) { this.traitement = traitement; }
    public Double getCout() { return cout; }
    public void setCout(Double cout) { this.cout = cout; }
    public List<ActeTechnique> getActesTechniques() { return actesTechniques; }
    public void setActesTechniques(List<ActeTechnique> actesTechniques) { this.actesTechniques = actesTechniques; }
}