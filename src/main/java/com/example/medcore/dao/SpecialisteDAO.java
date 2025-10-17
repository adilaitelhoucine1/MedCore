package com.example.medcore.dao;

import com.example.medcore.model.*;
import com.example.medcore.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class SpecialisteDAO {

    private EntityManager em;

    public void updateProfile(MedecinSpecialiste medecinSpecialiste) {
        em = JpaUtil.getEntityManager();
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

    public List<MedecinSpecialiste> getAllSpecialiste() {
        em = JpaUtil.getEntityManager();
        List<MedecinSpecialiste> medecinSpecialistes = new ArrayList<>();

        try {
            em.getTransaction().begin();


            List<Utilisateur> utilisateurs = em.createQuery(
                            "SELECT u FROM Utilisateur u WHERE u.role = :role",
                            Utilisateur.class
                    )
                    .setParameter("role", Utilisateur.Role.SPECIALISTE)
                    .getResultList();


            for (Utilisateur u : utilisateurs) {
                if (u instanceof MedecinSpecialiste) {
                    if (((MedecinSpecialiste) u).getSpecialite() != null) {


                        medecinSpecialistes.add((MedecinSpecialiste) u);
                    }
                }
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return medecinSpecialistes;
    }

//todo :testtsts
    public List<Creneau> getCreneau(int specialistId) {
        em = JpaUtil.getEntityManager();
        List<Creneau> creneausDispo = new ArrayList<>();
        try {
            em.getTransaction().begin();
            creneausDispo = em.createQuery(
                            "SELECT DISTINCT c FROM Creneau c " +
                                    "JOIN c.disponibilites d " +
                                    "WHERE d.medecinSpecialiste.id = :specialistId " +
                                    "AND d.status = :statut " +
                                    "ORDER BY c.dateHeureDebut",
                            Creneau.class)
                    .setParameter("specialistId", specialistId)
                    .setParameter("statut", DisponibiliteMedecin.Status.DISPONIBLE)
                    .getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return creneausDispo;
    }

}
