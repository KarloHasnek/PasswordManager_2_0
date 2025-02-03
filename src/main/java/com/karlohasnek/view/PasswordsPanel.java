package com.karlohasnek.view;

import com.karlohasnek.models.PasswordEntry;
import com.karlohasnek.models.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * This class is used as a container for instances of PasswordPanel class.
 */
public class PasswordsPanel extends JPanel {

    private User user;
    private List<PasswordEntry> passwords;

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
        for (PasswordEntry password : passwords) {
            PasswordPanel pp = new PasswordPanel(password.getWebsite(), password.getUsername(), password.getPassword());
            add(pp, "wrap");
        }
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
}