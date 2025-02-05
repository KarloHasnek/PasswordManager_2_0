package com.karlohasnek.Application;

import com.karlohasnek.controllers.HibernateUtil;
import com.karlohasnek.models.Credential;
import com.karlohasnek.models.PasswordEntry;
import com.karlohasnek.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTest {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        User user = session.createQuery("FROM User WHERE name = :name", User.class)
                .setParameter("name", "Karlo")
                .uniqueResult();

//        User user = new User("Test", "User", 42);

//        session.save(user);


        Credential credential = new Credential("testuser", "password123", user);

        PasswordEntry passwordEntry1 = new PasswordEntry("instagram.com", "karlohasnek2", "password123", user);
        PasswordEntry passwordEntry2 = new PasswordEntry("gmail.com", "karlohasnek2", "password123", user);
        PasswordEntry passwordEntry3 = new PasswordEntry("youtube.com", "karlohasnek2", "password123", user);
        PasswordEntry passwordEntry4 = new PasswordEntry("github.com", "karlohasnek2", "password123", user);

//        session.save(credential);
        session.save(passwordEntry1);
        session.save(passwordEntry2);
        session.save(passwordEntry3);
        session.save(passwordEntry4);

        transaction.commit();
        System.out.println("Credential and PasswordEntries saved successfully!");


        session.close();
        HibernateUtil.shutdown();
    }
}
