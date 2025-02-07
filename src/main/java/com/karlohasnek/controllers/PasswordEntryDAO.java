package com.karlohasnek.controllers;

import com.karlohasnek.controllers.util.HibernateUtil;
import com.karlohasnek.models.PasswordEntry;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object (DAO) for managing {@link PasswordEntry} entities.
 * Provides CRUD operations and utility methods for interacting with password-related data in the database.
 */
public class PasswordEntryDAO extends BaseDAO {

    /**
     * Saves a new password entry to the database.
     *
     * @param entry The {@link PasswordEntry} object to be saved.
     */
    public void savePasswordEntry(PasswordEntry entry) {
        executeInTransaction(session -> session.save(entry));
    }

    /**
     * Retrieves all password entries for a specific user.
     *
     * @param userId The ID of the user whose password entries are to be retrieved.
     * @return A list of {@link PasswordEntry} objects associated with the user.
     */
    public List<PasswordEntry> getAllPasswordEntries(Integer userId) {
        return fetchInTransaction(session -> {
            Query query = session.createQuery("from PasswordEntry p where p.user.id = :userId", PasswordEntry.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        });
    }

    /**
     * Updates an existing password entry in the database.
     * Increments the times edited counter for tracking changes.
     *
     * @param passwordEntry The {@link PasswordEntry} object containing updated information.
     */
    public void updatePasswordEntry(PasswordEntry passwordEntry) {
         executeInTransaction(session -> {
            PasswordEntry existingEntry = session.get(PasswordEntry.class, passwordEntry.getId());
            if (existingEntry != null) {
                existingEntry.setPassword(passwordEntry.getPassword());
                existingEntry.incrementTimesEdited();
                session.update(existingEntry);
            }
        });
    }

    /**
     * Deletes a password entry based on website and username.
     *
     * @param website  The website associated with the password entry.
     * @param username The username associated with the password entry.
     */
    public void deletePasswordEntry(String website, String username) {
        executeInTransaction(session -> {
            Query query = session.createQuery("from PasswordEntry p where p.website = :website and p.username = :username", PasswordEntry.class);
            query.setParameter("website", website);
            query.setParameter("username", username);
            PasswordEntry passwordEntry = (PasswordEntry) query.getSingleResult();

            if (passwordEntry != null) {
                session.delete(passwordEntry);
                System.out.println("Password entry deleted successfully.");
            }
        });
    }

    /**
     * Retrieves a specific password entry based on website and username.
     *
     * @param website  The website associated with the password entry.
     * @param username The username associated with the password entry.
     * @return The {@link PasswordEntry} object if found, otherwise {@code null}.
     */
    public PasswordEntry getPasswordEntry(String website, String username) {
        return fetchInTransaction(session -> {
            Query query = session.createQuery("from PasswordEntry p where p.website = :website and p.username = :username", PasswordEntry.class);
            query.setParameter("website", website);
            query.setParameter("username", username);
            return (PasswordEntry) query.getSingleResult();
        });
    }

    /**
     * Retrieves a map containing the number of times each user's passwords have been edited.
     *
     * @return A map where the key is the user's full name and the value is the total count of password edits.
     */
    public Map<String, Integer> getPasswordChangeCounts() {
        Map<String, Integer> passwordChangeCounts = new HashMap<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<PasswordEntry> passwordEntries = session.createQuery("from PasswordEntry", PasswordEntry.class).getResultList();

            for (PasswordEntry entry : passwordEntries) {
                String username = entry.getUser().getName() + " " + entry.getUser().getSurname();
                passwordChangeCounts.put(username, passwordChangeCounts.getOrDefault(username, 0) + entry.getTimesEdited());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return passwordChangeCounts;
    }
}
