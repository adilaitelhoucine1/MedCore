package com.example.medcore.dao;

import com.example.medcore.model.Consultation;
import jakarta.persistence.EntityManager;
import com.example.medcore.util.JpaUtil;

public class ConsultationDAO {

    public  void  save(Consultation consultation){
        EntityManager em = JpaUtil.getEntityManager();

        try{
            em.getTransaction().begin();
            em.persist(consultation);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }

    }
}
