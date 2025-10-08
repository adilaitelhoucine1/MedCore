package com.example.medcore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ActeTechnique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeActe typeActe;

    private LocalDateTime date;
    private String resultat;

    @ManyToOne
    private Consultation consultation;

    public enum TypeActe {
        RADIOGRAPHIE, ECHOGRAPHIE, IRM, ELECTROCARDIOGRAMME, LASER_DERMATO, FOND_OEIL, ANALYSE_SANG, ANALYSE_URINE
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TypeActe getTypeActe() { return typeActe; }
    public void setTypeActe(TypeActe typeActe) { this.typeActe = typeActe; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getResultat() { return resultat; }
    public void setResultat(String resultat) { this.resultat = resultat; }
    public Consultation getConsultation() { return consultation; }
    public void setConsultation(Consultation consultation) { this.consultation = consultation; }
}