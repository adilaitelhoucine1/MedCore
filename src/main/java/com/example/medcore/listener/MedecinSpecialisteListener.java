package com.example.medcore.listener;

import com.example.medcore.model.Creneau;
import com.example.medcore.model.DisponibiliteMedecin;
import com.example.medcore.model.MedecinSpecialiste;
import jakarta.persistence.*;

import java.util.List;

public class MedecinSpecialisteListener {

    @PersistenceContext
    private EntityManager em; // Injected by container

    @PostPersist
    public void assignCreneauxToMedecin(MedecinSpecialiste medecin) {
        List<Creneau> creneaux = em.createQuery("SELECT c FROM Creneau c", Creneau.class)
                .getResultList();

        for (Creneau c : creneaux) {
            DisponibiliteMedecin dispo = new DisponibiliteMedecin();
            dispo.setMedecinSpecialiste(medecin); // ID available
            dispo.setCreneau(c);
            dispo.setConsultation(null);
            dispo.setStatus(DisponibiliteMedecin.Status.DISPONIBLE);
            em.persist(dispo);
        }
        // No transaction handling — container manages it
    }
}
