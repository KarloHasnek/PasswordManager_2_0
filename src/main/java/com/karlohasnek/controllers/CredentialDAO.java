package com.karlohasnek.controllers;

import com.karlohasnek.models.Credential;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CredentialDAO {

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

    public Credential getCredentialById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Credential.class, id);
        }
    }

    public List<Credential> getAllCredentials() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Credential", Credential.class).list();
        }
    }

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
