package com.example.medcore.dao;

import com.example.medcore.model.Consultation;
import com.example.medcore.model.Creneau;
import com.example.medcore.model.DisponibiliteMedecin;
import com.example.medcore.model.MedecinSpecialiste;
import com.example.medcore.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreneauDAO {
   // EntityManager em ;

   public List<Creneau> getCreneaux(Long medecinId) {
       EntityManager em = JpaUtil.getEntityManager();
       List<Creneau> creneaux = new ArrayList<>();

       try {
           creneaux = em.createQuery(
                           "SELECT d.status, c.dateHeureDebut, c.dateHeureFin " +
                                   "FROM DisponibiliteMedecin d " +
                                   "JOIN d.creneau c " +
                                   "WHERE d.medecinSpecialiste.id = :medecinId",
                           Creneau.class
                   )
                   .setParameter("medecinId", medecinId)
                   .getResultList();

       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           em.close();
       }

       return creneaux;
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

    public List<Object[]> getAllCreneau(Long medecinId) {
        EntityManager em = JpaUtil.getEntityManager();
        List<Object[]> results = new ArrayList<>();
        try {
            results = em.createQuery(
                            "SELECT d.status, c.dateHeureDebut, c.dateHeureFin " +
                                    "FROM DisponibiliteMedecin d " +
                                    "JOIN d.creneau c " +
                                    "WHERE d.medecinSpecialiste.id = :medecinId",
                            Object[].class  )
                    .setParameter("medecinId", medecinId)
                    .getResultList();

            for (Object[] row : results) {
                System.out.println(row[0] + " | " + row[1] + " | " + row[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return results;
    }

}
