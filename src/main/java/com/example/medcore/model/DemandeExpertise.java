package com.example.medcore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DemandeExpertise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Consultation consultation;

    @ManyToOne
    private MedecinSpecialiste medecinSpecialiste;

    private String question;

    @Enumerated(EnumType.STRING)
    private Priorite priorite;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime dateDemande;
    private String avisMedecin;
    private String recommandations;


    public enum Priorite {
        URGENTE, NORMALE, NON_URGENTE
    }

    public enum Status {
        EN_ATTENTE, TERMINEE
    }

    protected DemandeExpertise(){}
    public DemandeExpertise(Consultation consultation , MedecinSpecialiste medecinSpecialiste,String question,Priorite priorite){
            this.dateDemande=LocalDateTime.now();
            this.status=Status.EN_ATTENTE;
            this.consultation=consultation;
            this.medecinSpecialiste=medecinSpecialiste;
            this.question=question;
            this.priorite=priorite;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Consultation getConsultation() { return consultation; }
    public void setConsultation(Consultation consultation) { this.consultation = consultation; }
    public MedecinSpecialiste getMedecinSpecialiste() { return medecinSpecialiste; }
    public void setMedecinSpecialiste(MedecinSpecialiste medecinSpecialiste) { this.medecinSpecialiste = medecinSpecialiste; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public Priorite getPriorite() { return priorite; }
    public void setPriorite(Priorite priorite) { this.priorite = priorite; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getDateDemande() { return dateDemande; }
    public void setDateDemande(LocalDateTime dateDemande) { this.dateDemande = dateDemande; }
    public String getAvisMedecin() { return avisMedecin; }
    public void setAvisMedecin(String avisMedecin) { this.avisMedecin = avisMedecin; }
    public String getRecommandations() { return recommandations; }
    public void setRecommandations(String recommandations) { this.recommandations = recommandations; }
}