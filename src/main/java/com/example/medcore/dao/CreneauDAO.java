package com.example.medcore.dao;

import com.example.medcore.model.Creneau;
import com.example.medcore.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CreneauDAO {

    public List<Creneau> getCreneaux(){
        EntityManager em = JpaUtil.getEntityManager();
        List<Creneau> creneaus = List.of();
        try {
            creneaus = em.createQuery("select  c from Creneau  c",Creneau.class).getResultList();

        }catch (Exception e ){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return creneaus;
    }
}
