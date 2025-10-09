package com.example.medcore.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class DossierMedical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Patient patient;

    private String antecedents;
    private String allergies;
    private String traitementEnCours;

    @OneToMany(mappedBy = "dossierMedical", cascade = CascadeType.ALL)
    private List<SignesVitaux> signesVitaux;

    @OneToMany
    private List<Consultation> historiqueConsultations;

    @OneToMany
    private List<ActeTechnique> actesTechniques;

protected DossierMedical(){}

    public DossierMedical(Patient patient, String antecedents, String allergies, String traitementEnCours) {
        this.patient=patient;
        this.antecedents=antecedents;
        this.allergies=allergies;
        this.traitementEnCours=traitementEnCours;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public String getAntecedents() { return antecedents; }
    public void setAntecedents(String antecedents) { this.antecedents = antecedents; }
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public String getTraitementEnCours() { return traitementEnCours; }
    public void setTraitementEnCours(String traitementEnCours) { this.traitementEnCours = traitementEnCours; }
    public List<SignesVitaux> getSignesVitaux() { return signesVitaux; }
    public void setSignesVitaux(List<SignesVitaux> signesVitaux) { this.signesVitaux = signesVitaux; }
    public List<Consultation> getHistoriqueConsultations() { return historiqueConsultations; }
    public void setHistoriqueConsultations(List<Consultation> historiqueConsultations) { this.historiqueConsultations = historiqueConsultations; }
    public List<ActeTechnique> getActesTechniques() { return actesTechniques; }
    public void setActesTechniques(List<ActeTechnique> actesTechniques) { this.actesTechniques = actesTechniques; }
}