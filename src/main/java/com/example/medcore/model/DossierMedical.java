package com.example.medcore.model;

import jakarta.persistence.*;

@Entity
public class DossierMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String antecedents;
    private String allergies;
    private String traitementEnCours;

    // Many-to-One / One-to-One with Patient (optional unidirectional)
    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Constructors
    public DossierMedical() {}

    public DossierMedical(Patient patient, String antecedents, String allergies, String traitementEnCours) {
        this.patient = patient;
        this.antecedents = antecedents;
        this.allergies = allergies;
        this.traitementEnCours = traitementEnCours;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAntecedents() { return antecedents; }
    public void setAntecedents(String antecedents) { this.antecedents = antecedents; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }

    public String getTraitementEnCours() { return traitementEnCours; }
    public void setTraitementEnCours(String traitementEnCours) { this.traitementEnCours = traitementEnCours; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
}
