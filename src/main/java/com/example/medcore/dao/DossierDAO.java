package com.example.medcore.dao;

import com.example.medcore.model.DossierMedical;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DossierDAO {
    private final EntityManagerFactory  em = Persistence.createEntityManagerFactory("default");

    public void addMedicalInfo(DossierMedical dossierMedical){
        EntityManager entityManager = em.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(dossierMedical);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();

        } finally {
            entityManager.close();
        }

    }

}
