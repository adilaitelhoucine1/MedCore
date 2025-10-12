package com.example.medcore.dao;

import com.example.medcore.model.Consultation;
import jakarta.persistence.EntityManager;
import com.example.medcore.util.JpaUtil;

import java.util.List;

public class ConsultationDAO {

    public  boolean  save(Consultation consultation){
        EntityManager em = JpaUtil.getEntityManager();

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
        EntityManager em = JpaUtil.getEntityManager();

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
        EntityManager em = JpaUtil.getEntityManager();
        Consultation consultation=null;
        try {
            consultation=em.find(Consultation.class,consultationId);
        }finally {
            em.close();
        }
        return consultation;
    }
}
