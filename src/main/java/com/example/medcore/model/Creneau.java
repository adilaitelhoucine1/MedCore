package com.example.medcore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Creneau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "creneau")
    private List<DisponibiliteMedecin> disponibilites;

    private LocalDateTime dateHeureDebut;
    private LocalDateTime dateHeureFin;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        DISPONIBLE, RESERVE, ARCHIVE
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
   // public MedecinSpecialiste getMedecinSpecialiste() { return medecinSpecialiste; }
   // public void setMedecinSpecialiste(MedecinSpecialiste medecinSpecialiste) { this.medecinSpecialiste = medecinSpecialiste; }
    public LocalDateTime getDateHeureDebut() { return dateHeureDebut; }
    public void setDateHeureDebut(LocalDateTime dateHeureDebut) { this.dateHeureDebut = dateHeureDebut; }
    public LocalDateTime getDateHeureFin() { return dateHeureFin; }
    public void setDateHeureFin(LocalDateTime dateHeureFin) { this.dateHeureFin = dateHeureFin; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public List<DisponibiliteMedecin> getDisponibilites() { return disponibilites; }
    public void setDisponibilites(List<DisponibiliteMedecin> disponibilites) { this.disponibilites = disponibilites; }
}