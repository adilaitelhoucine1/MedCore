package com.example.medcore.dao;

import com.example.medcore.model.DemandeExpertise;
import com.example.medcore.util.JpaUtil;
import jakarta.persistence.EntityManager;

public class DemandeExpertiseDAO {
    EntityManager em= JpaUtil.getEntityManager(); ;
    public  boolean save(DemandeExpertise demandeExpertise){
        try {
            em.getTransaction().begin();
            em.persist(demandeExpertise);
            em.getTransaction().commit();
            return  true;
        }catch (Exception e){
            em.getTransaction().rollback();
            return  false;
        }finally {
            em.close();
        }

    }

}
