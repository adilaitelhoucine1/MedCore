package com.example.medcore.dao;

import com.example.medcore.model.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PatientDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public void save(Patient patient) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(patient);
        em.getTransaction().commit();
        em.close();
    }
}