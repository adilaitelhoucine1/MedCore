package com.example.medcore.dao;

import com.example.medcore.model.Consultation;
import com.example.medcore.model.Creneau;
import com.example.medcore.model.DisponibiliteMedecin;
import com.example.medcore.model.MedecinSpecialiste;
import com.example.medcore.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CreneauDAO {
   // EntityManager em ;
    public List<Creneau> getCreneaux(){
        EntityManager   em = JpaUtil.getEntityManager();
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

    public  Creneau findById(int creneauId){
        EntityManager em = JpaUtil.getEntityManager();
        Creneau creneau =null;
        try {
            em.getTransaction().begin();
            creneau=em.find(Creneau.class,creneauId);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return creneau;
    }

    public  boolean updateStatus(Creneau creneau , MedecinSpecialiste medecinSpecialiste, Consultation consultation){
        EntityManager em = JpaUtil.getEntityManager();
        DisponibiliteMedecin disponibiliteMedecin;

        try {
            em.getTransaction().begin();
            disponibiliteMedecin=  em.createQuery("SELECT dispo FROM DisponibiliteMedecin  dispo" +
                    " where dispo.creneau=:creneau " +
                    "and dispo.medecinSpecialiste =:medecinSpecialiste", DisponibiliteMedecin.class).
                    setParameter("creneau",creneau).
                    setParameter("medecinSpecialiste",medecinSpecialiste).getSingleResult();
            disponibiliteMedecin.setStatus(DisponibiliteMedecin.Status.RESERVE);
            disponibiliteMedecin.setConsultation(consultation);
            em.merge(disponibiliteMedecin);
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
