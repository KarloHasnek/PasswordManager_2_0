package com.karlohasnek.controllers;

import com.karlohasnek.controllers.util.HibernateUtil;
import com.karlohasnek.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDAO {

    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public User getUserByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (User) session.createQuery("select u from User u join u.credential c where c.username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println("Error getting user by username: " + username);
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkUserExists(String username) {
        User user = getUserByUsername(username);
//        System.out.println("User: " + user);
        return user != null;
    }

    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    public void updateUser(User newUserInfo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User existingUser = session.get(User.class, newUserInfo.getId());

            if (existingUser != null) {
                existingUser.setName(newUserInfo.getName());
                existingUser.setSurname(newUserInfo.getSurname());
                existingUser.setAge(newUserInfo.getAge());

                if (existingUser.getCredential() != null && newUserInfo.getCredential() != null) {
                    existingUser.getCredential().setUsername(newUserInfo.getCredential().getUsername());
                    existingUser.getCredential().setPassword(newUserInfo.getCredential().getPassword());
                }

//                if (existingUser.getPasswordEntries() != null && newUserInfo.getPasswordEntries() != null) {
//                    // Clear current PasswordEntries and add new ones (or modify them as required)
//                    existingUser.setPasswordEntries(newUserInfo.getPasswordEntries());
//                    // You can also iterate through existing password entries and update them if necessary
//                }

                // Commit the transaction to save the updated data
                session.update(existingUser);
                transaction.commit();
            } else {
                System.out.println("User not found for id: " + newUserInfo.getId());
            }

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error updating user: " + newUserInfo);
        }
    }


    public void deleteUserById(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.println("User deleted successfully.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public User getUserWithPasswordEntries(Integer userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User u left join fetch u.passwordEntries where u.id = :userId", User.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

