package com.example.medcore.dao;

import com.example.medcore.model.*;
import com.example.medcore.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.List;

import static com.example.medcore.util.JpaUtil.getEntityManager;

public class UserDAO {
//private EntityManagerFactory em = Persistence.createEntityManagerFactory("default");

    public boolean save(Utilisateur user) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            if(user.getRole().equals(Utilisateur.Role.SPECIALISTE)){
                MedecinSpecialiste medecinSpecialiste=(MedecinSpecialiste)user;
                List<Creneau> creneaux = entityManager.createQuery("SELECT c FROM Creneau c", Creneau.class)
                        .getResultList();

                for (Creneau c : creneaux) {
                    DisponibiliteMedecin dispo = new DisponibiliteMedecin();
                    dispo.setMedecinSpecialiste(medecinSpecialiste);
                    dispo.setCreneau(c);
                    dispo.setConsultation(null);
                    dispo.setStatus(DisponibiliteMedecin.Status.DISPONIBLE);
                    entityManager.persist(dispo);
                }
            }
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
        }

    }

    public Utilisateur findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                            "SELECT u FROM Utilisateur u WHERE u.email = :email",
                            Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }


    public Utilisateur findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        Utilisateur user = null;
        try {
            user = em.find(Utilisateur.class, id);
        } finally {
            em.close();
        }
        return user;
    }


}
