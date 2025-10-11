package com.example.medcore.dao;

import com.example.medcore.model.Patient;
import com.example.medcore.model.SignesVitaux;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SignesDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public boolean addSigne(SignesVitaux signesVitaux) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Patient patient = signesVitaux.getpatient();
            patient.setSignesVitaux(signesVitaux);


            em.persist(signesVitaux);
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
