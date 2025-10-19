package com.example.medcore.dao;

import com.example.medcore.model.Consultation;
import com.example.medcore.model.DemandeExpertise;
import com.example.medcore.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

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

    public List<Object[]> getAllDemande(long specialist_id) {
        EntityManager em = JpaUtil.getEntityManager();
        List<Object[]> demandes = new ArrayList<>();
        try {
            em.getTransaction().begin();
            demandes = em.createQuery(
                    "SELECT d.id, d.priorite,d.status,d.dateDemande, p.nom, p.prenom " +
                            "FROM DemandeExpertise d " +
                            "JOIN d.consultation c " +
                            "JOIN c.patient p where d.medecinSpecialiste.id =:id",
                    Object[].class
            ).setParameter("id",specialist_id)
            .getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return demandes;
    }

    public  DemandeExpertise findById(Long id){
        em = JpaUtil.getEntityManager();
        DemandeExpertise demandeExpertise=null;
        try {
            demandeExpertise=em.find(DemandeExpertise.class,id);
        }finally {
            em.close();
        }
        return demandeExpertise;
    }

    public  boolean updateDemande(DemandeExpertise demandeExpertise){
        em=JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(demandeExpertise);
            em.getTransaction().commit();
            return   true;
        } catch (Exception e) {
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            return  false;
        }finally {
            em.close();
        }
    }
}
