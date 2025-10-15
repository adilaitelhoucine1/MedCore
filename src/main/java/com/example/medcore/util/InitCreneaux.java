package com.example.medcore.util;

import com.example.medcore.model.Creneau;
import com.example.medcore.model.DisponibiliteMedecin;
import com.example.medcore.model.MedecinSpecialiste;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class InitCreneaux {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default"); // your PU name
        EntityManager em = emf.createEntityManager();

        List<MedecinSpecialiste> medecins = em.createQuery("FROM MedecinSpecialiste", MedecinSpecialiste.class)
                .getResultList();

        em.getTransaction().begin();

        LocalDate today = LocalDate.now();
        LocalTime[] times = {
                LocalTime.of(9, 0),
                LocalTime.of(9, 30),
                LocalTime.of(10, 0),
                LocalTime.of(10, 30),
                LocalTime.of(11, 0),
                LocalTime.of(11, 30)
        };

        for (LocalTime start : times) {
            LocalDateTime debut = LocalDateTime.of(today, start);
            LocalDateTime fin = debut.plusMinutes(30);

            // Check if this créneau already exists
            Creneau creneau = em.createQuery(
                            "SELECT c FROM Creneau c WHERE c.dateHeureDebut = :debut AND c.dateHeureFin = :fin",
                            Creneau.class)
                    .setParameter("debut", debut)
                    .setParameter("fin", fin)
                    .getResultStream()
                    .findFirst()
                    .orElseGet(() -> {
                        Creneau newCreneau = new Creneau();
                        newCreneau.setDateHeureDebut(debut);
                        newCreneau.setDateHeureFin(fin);
                        em.persist(newCreneau);
                        return newCreneau;
                    });

            // For each doctor, create DisponibiliteMedecin if not exists
            for (MedecinSpecialiste med : medecins) {
                long count = em.createQuery(
                                "SELECT COUNT(d) FROM DisponibiliteMedecin d " +
                                        "WHERE d.medecinSpecialiste = :med AND d.creneau = :creneau",
                                Long.class)
                        .setParameter("med", med)
                        .setParameter("creneau", creneau)
                        .getSingleResult();

                if (count == 0) {
                    DisponibiliteMedecin dispo = new DisponibiliteMedecin();
                    dispo.setMedecinSpecialiste(med);
                    dispo.setCreneau(creneau);
                    dispo.setStatus(DisponibiliteMedecin.Status.DISPONIBLE);
                    em.persist(dispo);
                }
            }
        }

        em.getTransaction().commit();
        em.close();
        emf.close();

        System.out.println("✅ Default créneaux initialized for all specialists using DisponibiliteMedecin.");
    }
}
