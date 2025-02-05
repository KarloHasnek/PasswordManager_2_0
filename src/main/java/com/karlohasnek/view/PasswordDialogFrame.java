package com.karlohasnek.view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Frame for adding new password for the existing user.
 */
public class PasswordDialogFrame extends JFrame {

    private JTextField websiteField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton addButton;
    private MainEvent mainActionListener;

    /**
     * Constructor for the password dialog frame.
     */
    public PasswordDialogFrame() {
        super("Password Manager");
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(false);
        setResizable(false);
        setLocationRelativeTo(null);
        initComps();
        layoutComps();
    }

    /**
     * Initialize components.
     */
    private void initComps() {
        websiteField = new JTextField(15);
        usernameField = new JTextField(15);
        passwordField = new JTextField(15);
        addButton = new JButton("Add");
    }

    /**
     * Layout components.
     */
    private void layoutComps() {
        setLayout(new MigLayout("center, insets 25, gapy 15"));
        add(new JLabel("Website:"));
        add(websiteField, "wrap");
        add(new JLabel("Username:"));
        add(usernameField, "wrap");
        add(new JLabel("Password:"));
        add(passwordField, "wrap");
        add(addButton, "span, center");
    }

    /**
     * Activate components.
     */
    public void activateComps() {
        addButton.addActionListener(mainActionListener);
        System.out.println("ADD BUTTON ACTIVATED");
    }

    /**
     * Getter for add button.
     * @return JButton - add button
     */
    public JButton getAddButton() {
        return addButton;
    }

    /**
     * Getter for website field.
     * @return JTextField - website field
     */
    public JTextField getWebsiteField() {
        return websiteField;
    }

    /**
     * Getter for username field.
     * @return JTextField - username field
     */
    public JTextField getUsernameField() {
        return usernameField;
    }

    /**
     * Getter for password field.
     * @return JTextField - password field
     */
    public JTextField getPasswordField() {
        return passwordField;
    }

    /**
     * Setter for main action listener.
     * @param mainActionListener - main action listener
     */
    public void setMainActionListener(MainEvent mainActionListener) {
        this.mainActionListener = mainActionListener;
    }
}