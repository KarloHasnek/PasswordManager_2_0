package com.karlohasnek.view.frames;

import com.karlohasnek.controllers.PasswordService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is used for storing the credentials for a website of a user.
 */
public class PasswordEntryPanel extends JPanel {

    private JLabel website;
    private JLabel username;
    private JLabel password;
    private String passwordString;
    private JButton showPasswordButton;
    private JButton editPasswordButton;
    private JButton deletePasswordButton;
    private PasswordsPanel passwordsPanel;
    private PasswordService passwordService;


    /**
     *
     * Constructor for the password panel.
     * @param website string representing the website adress
     * @param username string representing the username
     * @param password string representing the password
     */
    public PasswordEntryPanel(String website, String username, String password, PasswordsPanel passwordsPanel) {
        this.website = new JLabel(website);
        this.username = new JLabel(username);
        this.password = new JLabel(password);
        this.passwordString = password;
        this.passwordsPanel = passwordsPanel;
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
        editPasswordButton = new JButton("Edit");
        deletePasswordButton = new JButton("Delete");
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
        passwordService = new PasswordService();
    }

    /**
     * Lays out the components.
     */
    private void layoutComps() {
        setLayout(new MigLayout("fillx, insets 0, gapx 10, wrap 6, w 675:675:675"));
        add(website, "gapbefore 5, growx, width 25%");
        add(username, "growx, width 25%");
        add(password, "growx, width 25%");
        add(showPasswordButton, "growx, width 25%, w 70:70:70");
        add(editPasswordButton, "growx, width 25%, w 70:70:70");
        add(deletePasswordButton, "growx, width 25%, w 70:70:70, align right");
    }

    /**
     * Hides the given password.
     */
    private void hidePassword() {
        password.setText(passwordService.maskPassword(password.getText()));
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
                    password.setText(passwordService.maskPassword(passwordString));
                } else {
                    showPasswordButton.setText("Hide");
                    password.setText(passwordString);
                }
            }
        });

        deletePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this password?", "Delete Password", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    passwordsPanel.removePassword(website.getText(), username.getText());
                }
            }
        });

        editPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = JOptionPane.showInputDialog(null, "Enter new password for: " + website.getText(), "Change Password", JOptionPane.PLAIN_MESSAGE);

                if (newPassword != null && !newPassword.isEmpty()) {
                    passwordsPanel.editPassword(website.getText(), username.getText(), newPassword);
                    JOptionPane.showMessageDialog(null, "Password for " + website.getText() + " successfully changed.", "Password Changed", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Password not changed.", "Password Not Changed", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
    }
}