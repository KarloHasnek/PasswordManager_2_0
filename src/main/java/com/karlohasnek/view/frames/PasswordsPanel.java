package com.karlohasnek.view.frames;

import com.karlohasnek.controllers.PasswordEntryDAO;
import com.karlohasnek.models.PasswordEntry;
import com.karlohasnek.models.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.List;

/**
 * This class is used as a container for instances of PasswordEntryPanel class.
 */
public class PasswordsPanel extends JPanel {

    private User user;
    private List<PasswordEntry> passwords;
    private PasswordEntryDAO passwordEntryDAO = new PasswordEntryDAO();

    /**
     * Constructor for the passwords panel.
     * Initializes the panel with a border and layout.
     */
    public PasswordsPanel() {
        setBorder(BorderFactory.createTitledBorder("My passwords"));
        setLayout(new MigLayout("w 680"));
    }

    /**
     * Updates the panel with the latest passwords.
     * Clears the existing content and re-adds password entries dynamically.
     */
    public void updatePasswords() {
        removeAll();
        revalidate();
        repaint();

        System.out.println("Updating passwords");

        for (PasswordEntry password : passwords) {
            PasswordEntryPanel pp = new PasswordEntryPanel(password.getWebsite(), password.getUsername(), password.getPassword(), this);
            add(pp, "wrap");
        }

        revalidate();
        repaint();
    }

    /**
     * Sets the user for this panel.
     * @param user the user whose passwords will be displayed.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the list of password entries to be displayed.
     * @param passwords the list of password entries to be shown in the panel.
     */
    public void setPasswords(List<PasswordEntry> passwords) {
        this.passwords = passwords;
    }

    /**
     * Removes a password entry from the database and updates the panel.
     * @param website  the website associated with the password.
     * @param username the username associated with the password.
     */
    public void removePassword(String website, String username) {
        passwordEntryDAO.deletePasswordEntry(website, username);
        passwords.remove(passwords.stream().filter(p -> p.getWebsite().equals(website) && p.getUsername().equals(username)).findFirst().get());
        updatePasswords();
    }

    /**
     * Edits an existing password entry in the database and updates the panel.
     * @param website     the website associated with the password.
     * @param username    the username associated with the password.
     * @param newPassword the new password to be updated.
     */
    public void editPassword(String website, String username, String newPassword) {
        PasswordEntry passwordEntry = passwordEntryDAO.getPasswordEntry(website, username);
        passwordEntry.setPassword(newPassword);
        passwordEntryDAO.updatePasswordEntry(passwordEntry);
        passwords.stream().filter(p -> p.getWebsite().equals(website) && p.getUsername().equals(username)).findFirst().get().setPassword(newPassword);
        updatePasswords();
    }
}
