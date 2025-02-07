package com.karlohasnek.controllers;

import com.karlohasnek.controllers.util.HibernateUtil;
import com.karlohasnek.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Data Access Object (DAO) for managing {@link User} entities.
 * Provides CRUD operations and utility methods for interacting with the database.
 */
public class UserDAO extends BaseDAO {

    /**
     * Saves a new user to the database.
     *
     * @param user The user entity to be saved.
     */
    public void saveUser(User user) {
        executeInTransaction(session -> session.save(user));
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user.
     * @return The {@link User} object if found, otherwise {@code null}.
     */
    public User getUserByUsername(String username) {
            return fetchInTransaction(session ->
                    session.createQuery("select u from User u join u.credential c where c.username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult());
    }

    /**
     * Checks if a user exists in the database by their username.
     *
     * @param username The username to check.
     * @return {@code true} if the user exists, otherwise {@code false}.
     */
    public boolean checkUserExists(String username) {
        User user = getUserByUsername(username);
        return user != null;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all {@link User} entities.
     */
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    /**
     * Updates an existing user with new information.
     *
     * @param newUserInfo The {@link User} object containing updated information.
     */
    public void updateUser(User newUserInfo) {
        executeInTransaction(session -> {
            User existingUser = session.get(User.class, newUserInfo.getId());

            if (existingUser != null) {
                existingUser.setName(newUserInfo.getName());
                existingUser.setSurname(newUserInfo.getSurname());
                existingUser.setAge(newUserInfo.getAge());

                if (existingUser.getCredential() != null && newUserInfo.getCredential() != null) {
                    existingUser.getCredential().setUsername(newUserInfo.getCredential().getUsername());
                    existingUser.getCredential().setPassword(newUserInfo.getCredential().getPassword());
                }

                session.update(existingUser);
            } else {
                System.out.println("User not found for id: " + newUserInfo.getId());
            }
        });
    }

    /**
     * Retrieves a user along with their password entries.
     *
     * @param userId The ID of the user.
     * @return The {@link User} object with password entries if found, otherwise {@code null}.
     */
    public User getUserWithPasswordEntries(Integer userId) {
        return fetchInTransaction(session -> {
            return session.createQuery("from User u left join fetch u.passwordEntries where u.id = :userId", User.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
        });
    }
}
