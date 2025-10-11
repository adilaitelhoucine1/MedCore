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
            em.close();
        }

        return patientList;
    }

    public List<Patient> listActivePatients() {
        EntityManager em = emf.createEntityManager();
        List<Patient> patientList;

        try {
            patientList = em.createQuery("SELECT p FROM Patient p where p.fileAttente=true ", Patient.class)
                    .getResultList();
            System.out.println("PatientDAO: fetched patients size = " + patientList.size());
        } finally {
            em.close();
        }

        return patientList;
    }


    public List<Patient> findPatientswithInfo() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT DISTINCT p FROM Patient p " +
                                    "LEFT JOIN FETCH p.consultations c " +
                                    "LEFT JOIN FETCH p.dossierMedical dm"+
                                    "LEFT JOIN FETCH p.signesVitaux",
                            Patient.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }




    public Patient findById(int id) {
        EntityManager em = emf.createEntityManager();
        Patient patient = null;
        try {
            patient = em.find(Patient.class, id);
        } finally {
            em.close();
        }
        return patient;
    }



}
