package com.example.medcore.dao;

import com.example.medcore.model.DossierMedical;
import com.example.medcore.model.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DossierDAO {
    private final EntityManagerFactory  emf = Persistence.createEntityManagerFactory("default");

    public boolean addMedicalInfo(DossierMedical dossierMedical){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Patient patient = dossierMedical.getPatient();
            patient.setDossierMedical(dossierMedical);

            em.persist(dossierMedical);
            em.merge(patient);

            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }


}
