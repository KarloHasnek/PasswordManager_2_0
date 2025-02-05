package com.karlohasnek.controllers.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utility class for managing Hibernate session factory.
 */
public class HibernateUtil {

    /**
     * Static method of session factory for creating Hibernate sessions.
     */
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Builds the session factory from the hibernate configuration file.
     *
     * @return The session factory.
     */
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * returns the session factory.
     * @return
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
