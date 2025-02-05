package com.karlohasnek.view;

import com.karlohasnek.controllers.PasswordEntryDAO;
import com.karlohasnek.models.PasswordEntry;
import com.karlohasnek.models.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.List;

/**
 * This class is used as a container for instances of PasswordPanel class.
 */
public class PasswordsPanel extends JPanel {

    private User user;
    private List<PasswordEntry> passwords;
    private PasswordEntryDAO passwordEntryDAO = new PasswordEntryDAO();

    /**
     * Constructor for the passwords panel.
     */
    public PasswordsPanel() {
        setBorder(BorderFactory.createTitledBorder("My passwords"));
        setLayout(new MigLayout("w 680"));

    }

    /**
     * This method is used to update the panel with the passwords.
     */
    public void updatePasswords() {
        removeAll();
        revalidate();
        repaint();

        System.out.println("Updating passwords");

        for (PasswordEntry password : passwords) {
            PasswordPanel pp = new PasswordPanel(password.getWebsite(), password.getUsername(), password.getPassword(), this);
            add(pp, "wrap");
        }

        revalidate();
        repaint();
    }

    /**
     * Setter for the user.
     * @param user the user to be set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter for the credentials and web adresses.
     * @param passwords a map containing String web adresses as keys and maps containing String usernames and passwords as values
     */
    public void setPasswords(List<PasswordEntry> passwords) {
        this.passwords = passwords;
    }

    public void removePassword(String website, String username) {
        passwordEntryDAO.deletePasswordEntry(website, username);
        passwords.remove(passwords.stream().filter(p -> p.getWebsite().equals(website) && p.getUsername().equals(username)).findFirst().get());
        updatePasswords();
    }

    public void editPassword(String website, String username, String newPassword) {
        PasswordEntry passwordEntry = passwordEntryDAO.getPasswordEntry(website, username);
        passwordEntry.setPassword(newPassword);
        passwordEntryDAO.updatePasswordEntry(passwordEntry);
        passwords.stream().filter(p -> p.getWebsite().equals(website) && p.getUsername().equals(username)).findFirst().get().setPassword(newPassword);
        updatePasswords();
    }
}