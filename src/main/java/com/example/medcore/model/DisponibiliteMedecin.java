package com.example.medcore.model;

import jakarta.persistence.*;

@Entity
public class DisponibiliteMedecin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Creneau creneau;

    @ManyToOne
    private MedecinSpecialiste medecinSpecialiste;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        DISPONIBLE, RESERVE, ARCHIVE
    }

    public DisponibiliteMedecin() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Creneau getCreneau() { return creneau; }
    public void setCreneau(Creneau creneau) { this.creneau = creneau; }

    public MedecinSpecialiste getMedecinSpecialiste() { return medecinSpecialiste; }
    public void setMedecinSpecialiste(MedecinSpecialiste medecinSpecialiste) { this.medecinSpecialiste = medecinSpecialiste; }

    @ManyToOne

    @JoinColumn(name = "consultation_id", nullable = true)
    private Consultation consultation;

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public  void setConsultation(Consultation consultation){this.consultation=consultation;}
    public Consultation getConsultation(){return this.consultation;}
}
