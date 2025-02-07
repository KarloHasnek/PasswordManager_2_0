package com.karlohasnek.controllers;

import com.karlohasnek.controllers.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BaseDAO {

    protected Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    protected void executeInTransaction(Consumer<Session> action) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            action.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to execute in transaction", e);
        }
    }

    protected <T> T fetchInTransaction(Function<Session, T> action) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            T result = action.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to fetch in Transaction", e);
        }
    }
}