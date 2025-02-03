package com.karlohasnek.controllers;

import com.karlohasnek.models.PasswordEntry;
import com.karlohasnek.models.User;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class PasswordEntryDAO {

    public void savePasswordEntry(PasswordEntry passwordEntry) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(passwordEntry);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public PasswordEntry getPasswordEntryById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(PasswordEntry.class, id);
        }
    }

    public List<PasswordEntry> getAllPasswordEntries(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Query for PasswordEntries associated with a specific userId
            Query query = session.createQuery("from PasswordEntry p where p.user.id = :userId", PasswordEntry.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PasswordEntry> getPasswordEntriesByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from PasswordEntry where user.id = :userId", PasswordEntry.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }

    public void updatePasswordEntry(PasswordEntry passwordEntry) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(passwordEntry);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deletePasswordEntryById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            PasswordEntry passwordEntry = session.get(PasswordEntry.class, id);
            if (passwordEntry != null) {
                session.delete(passwordEntry);
                System.out.println("Password entry deleted successfully.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


}
