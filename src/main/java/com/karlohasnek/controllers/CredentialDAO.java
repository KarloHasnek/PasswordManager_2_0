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
public class CredentialDAO extends BaseDAO {

    /**
     * Saves a new credential to the database.
     *
     * @param credential The {@link Credential} object to be saved.
     */
    public void saveCredential(Credential credential) {
        executeInTransaction(session -> session.save(credential));
    }

    /**
     * Retrieves a credential by its unique ID.
     *
     * @param id The unique identifier of the credential.
     * @return The {@link Credential} object if found, otherwise {@code null}.
     */
    public Credential getCredentialById(int id) {
        return fetchInTransaction(session -> session.get(Credential.class, id));
    }


    /**
     * Retrieves a list of all credentials stored in the database.
     *
     * @return A list of {@link Credential} objects.
     */
    public List<Credential> getAllCredentials() {
        return fetchInTransaction(session ->
                session.createQuery("from Credential", Credential.class).list()
        );
    }

    /**
     * Updates an existing credential in the database.
     *
     * @param credential The {@link Credential} object containing updated information.
     */
    public void updateCredential(Credential credential) {
        executeInTransaction(session -> {
            session.update(credential);
        });
    }

    /**
     * Deletes a credential from the database based on its unique ID.
     *
     * @param id The unique identifier of the credential to be deleted.
     */
    public void deleteCredential(int id) {
        executeInTransaction(session -> {
            Credential credential = session.get(Credential.class, id);
            if (credential != null) {
                session.delete(credential);
            }
        });
    }
}
