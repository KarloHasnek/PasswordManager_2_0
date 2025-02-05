package com.karlohasnek.controllers;

import com.karlohasnek.controllers.util.HibernateUtil;
import com.karlohasnek.models.Credential;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Data Access Object (DAO) for managing {@link Credential} entities.
 * Provides CRUD operations for handling credentials in the database.
 */
public class CredentialDAO {

    /**
     * Saves a new credential to the database.
     *
     * @param credential The {@link Credential} object to be saved.
     */
    public void saveCredential(Credential credential) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(credential);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a credential by its unique ID.
     *
     * @param id The unique identifier of the credential.
     * @return The {@link Credential} object if found, otherwise {@code null}.
     */
    public Credential getCredentialById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Credential.class, id);
        }
    }

    /**
     * Retrieves a list of all credentials stored in the database.
     *
     * @return A list of {@link Credential} objects.
     */
    public List<Credential> getAllCredentials() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Credential", Credential.class).list();
        }
    }

    /**
     * Updates an existing credential in the database.
     *
     * @param credential The {@link Credential} object containing updated information.
     */
    public void updateCredential(Credential credential) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(credential);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Deletes a credential from the database based on its unique ID.
     *
     * @param id The unique identifier of the credential to be deleted.
     */
    public void deleteCredentialById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Credential credential = session.get(Credential.class, id);
            if (credential != null) {
                session.delete(credential);
                System.out.println("Credential deleted successfully.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
