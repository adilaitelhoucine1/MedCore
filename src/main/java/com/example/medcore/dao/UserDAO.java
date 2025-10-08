package com.example.medcore.dao;

import com.example.medcore.model.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UserDAO {
    private EntityManagerFactory em = Persistence.createEntityManagerFactory("default");

    public void save(Utilisateur user) {
        EntityManager entityManager = em.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
