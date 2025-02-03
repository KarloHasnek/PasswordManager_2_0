package com.karlohasnek.view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is used for storing the credentials for a website of a user.
 */
public class PasswordPanel extends JPanel {

    private JLabel website;
    private JLabel username;
    private JLabel password;
    private String passwordString;
    private JButton showPasswordButton;

    /**
     * Constructor for the password panel.
     * @param website string representing the website adress
     * @param username string representing the username
     * @param password string representing the password
     */
    public PasswordPanel(String website, String username, String password) {
        this.website = new JLabel(website);
        this.username = new JLabel(username);
        this.password = new JLabel(password);
        this.passwordString = password;
        initComps();
        layoutComps();
        activateComps();
        hidePassword();
    }

    /**
     * Initializes the components.
     */
    private void initComps() {
        showPasswordButton = new JButton("Show");
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
    }

    /**
     * Lays out the components.
     */
    private void layoutComps() {
        setLayout(new MigLayout("fillx, insets 0, gapx 20, wrap 4, w 675:675:675"));
        add(website, "gapbefore 5, growx, width 25%");
        add(username, "growx, width 25%");
        add(password, "growx, width 25%");
        add(showPasswordButton, "growx, width 25%, w 75:75:75, align right");
    }

    /**
     * Hides the given password.
     */
    private void hidePassword() {
        password.setText(password.getText().replaceAll(".", "*"));
    }

    /**
     * Activates the components.
     */
    private void activateComps() {
        showPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordButton.getText().equals("Hide")) {
                    showPasswordButton.setText("Show");
                    password.setText(password.getText().replaceAll(".", "*"));
                } else {
                    showPasswordButton.setText("Hide");
                    password.setText(passwordString);
                }
            }
        });
    }
}