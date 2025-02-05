package com.karlohasnek.controllers;

import com.karlohasnek.controllers.util.HibernateUtil;
import com.karlohasnek.models.PasswordEntry;
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

    public PasswordEntry getPasswordEntryById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(PasswordEntry.class, id);
        }
    }

    public List<PasswordEntry> getAllPasswordEntries(Integer userId) {
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

    public List<PasswordEntry> getPasswordEntriesByUserId(Integer userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from PasswordEntry where user.id = :userId", PasswordEntry.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }

    public void deletePasswordEntryById(Integer id) {
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

    public void updatePasswordEntry(PasswordEntry passwordEntry) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            PasswordEntry existingEntry = session.get(PasswordEntry.class, passwordEntry.getId());
            if (existingEntry != null) {
                existingEntry.setPassword(passwordEntry.getPassword());
                existingEntry.incrementTimesEdited();

                session.update(existingEntry);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deletePasswordEntry(String website, String username) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query query = session.createQuery("from PasswordEntry p where p.website = :website and p.username = :username", PasswordEntry.class);
            query.setParameter("website", website);
            query.setParameter("username", username);
            PasswordEntry passwordEntry = (PasswordEntry) query.getSingleResult();

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

    public PasswordEntry getPasswordEntry(String website, String username) {

        PasswordEntry passwordEntry = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from PasswordEntry p where p.website = :website and p.username = :username", PasswordEntry.class);
            query.setParameter("website", website);
            query.setParameter("username", username);
            passwordEntry = (PasswordEntry) query.getSingleResult();
        } catch (NullPointerException e) {
            System.err.println("Password entry not found.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return passwordEntry;
    }
}