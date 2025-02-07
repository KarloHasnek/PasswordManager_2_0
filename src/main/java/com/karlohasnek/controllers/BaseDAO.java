package com.karlohasnek.controllers;

import com.karlohasnek.controllers.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Base class for Data Access Objects (DAOs) providing common functionality for
 * executing database operations in transactions.
 */
public abstract class BaseDAO {

    /**
     * Retrieves a new Hibernate session.
     *
     * @return A new Hibernate session.
     */
    protected Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    /**
     * Executes an action in a transaction.
     *
     * @param action The action to be executed.
     */
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

    /**
     * Fetches data in a transaction.
     *
     * @param action The function to fetch data.
     * @param <T>    The type of data to be fetched.
     * @return The fetched data.
     */
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