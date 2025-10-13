package com.example.medcore.dao;

import com.example.medcore.model.MedecinSpecialiste;
import com.example.medcore.util.JpaUtil;
import jakarta.persistence.EntityManager;

public class SpecialisteDAO {


    public void updateProfile(MedecinSpecialiste medecinSpecialiste) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(medecinSpecialiste);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

}
