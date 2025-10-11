package com.example.medcore.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String numSecu;
    private String adresse;
    private String telephone;
    private String mutuelle;
    @Column(name = "file_attente", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean fileAttente = true;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consultation> consultations = new ArrayList<>();


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dossier_medical_id")
    private DossierMedical dossierMedical;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "signes_vitaux_id")
    private SignesVitaux signesVitaux;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getNumSecu() { return numSecu; }
    public void setNumSecu(String numSecu) { this.numSecu = numSecu; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getMutuelle() { return mutuelle; }
    public void setMutuelle(String mutuelle) { this.mutuelle = mutuelle; }

    public Boolean getFileAttente() { return fileAttente; }
    public void setFileAttente(Boolean fileAttente) { this.fileAttente = fileAttente; }

    public DossierMedical getDossierMedical() { return dossierMedical; }
    public void setDossierMedical(DossierMedical dossierMedical) { this.dossierMedical = dossierMedical; }

    public SignesVitaux getSignesVitaux() { return signesVitaux; }
    public void setSignesVitaux(SignesVitaux signesVitaux) { this.signesVitaux = signesVitaux; }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
