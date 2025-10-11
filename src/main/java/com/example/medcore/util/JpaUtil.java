package com.example.medcore.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static final String PERSISTENCE_UNIT_NAME = "default"; // name in persistence.xml
    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("EntityManagerFactory creation failed: " + e);
        }
    }

    // ✅ Get the EntityManagerFactory
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    // ✅ Get a new EntityManager
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // ✅ Close when application stops (optional)
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
