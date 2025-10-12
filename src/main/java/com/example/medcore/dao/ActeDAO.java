package com.example.medcore.dao;

import com.example.medcore.model.ActeTechnique;
import com.example.medcore.util.JpaUtil;
import jakarta.persistence.EntityManager;

public class ActeDAO {


    public  boolean save(ActeTechnique acteTechnique){
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(acteTechnique);
            em.getTransaction().commit();
            return  true;
        }catch (Exception e){
            em.getTransaction().rollback();
            return false;
        }finally {
            em.close();
        }

    }
}
