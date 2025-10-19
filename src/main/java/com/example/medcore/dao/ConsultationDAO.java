package com.example.medcore.dao;

import com.example.medcore.model.Consultation;
import com.example.medcore.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class ConsultationDAO {
    private EntityManager em;
    public  boolean  save(Consultation consultation){
        em = JpaUtil.getEntityManager();

        try{
            em.getTransaction().begin();
            em.persist(consultation);
            em.getTransaction().commit();
            return  true;
        }catch (Exception e){
            em.getTransaction().rollback();
            return  false;
        }finally {
            em.close();
        }

    }


    public List<Consultation> getConsultationbyPatient(long patientId){
        em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT c FROM Consultation c WHERE c.patient.id = :id ORDER BY c.dateConsultation DESC",
                            Consultation.class
                    ).setParameter("id", patientId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public  Consultation findByID(Long consultationId){
        em = JpaUtil.getEntityManager();
        Consultation consultation=null;
        try {
            consultation=em.find(Consultation.class,consultationId);
        }finally {
            em.close();
        }
        return consultation;
    }

    public  boolean updateStatus(Consultation consultation){
        em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(consultation);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return  false;
        }finally {
            em.close();
        }
    }

    public List<Consultation> getAvailableConsultations(long patientId) {
        EntityManager em = JpaUtil.getEntityManager();
        List<Consultation> consultations = new ArrayList<>();

        try {
            consultations = em.createQuery(
                            "SELECT c FROM Consultation c WHERE c.status = :status AND c.patient.id = :patientId",
                            Consultation.class
                    )
                    .setParameter("status", Consultation.Status.EN_ATTENTE_AVIS_SPECIALISTE)
                    .setParameter("patientId", patientId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return consultations;
    }

    public  boolean updateCoutConsutation(Consultation consultation){
        EntityManager em  =JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(consultation);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            return false;
        }finally {
            em.close();
        }
    }


}
