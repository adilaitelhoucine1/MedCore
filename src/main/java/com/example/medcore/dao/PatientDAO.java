package com.example.medcore.dao;

import com.example.medcore.model.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    List<Patient> patientList ;
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("default");

    public void save(Patient patient) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(patient);
        em.getTransaction().commit();
        em.close();
    }

    public List<Patient> listPatients() {
        EntityManager em = emf.createEntityManager();
        List<Patient> patientList;

        try {
            patientList = em.createQuery("SELECT p FROM Patient p", Patient.class)
                    .getResultList();
            System.out.println("PatientDAO: fetched patients size = " + patientList.size());
        } finally {
            System.out.println("akhnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            em.close();
        }

        return patientList;
    }


}
