package com.karlohasnek.view.frames;

import com.karlohasnek.controllers.UserDAO;
import com.karlohasnek.controllers.util.PasswordUtil;
import com.karlohasnek.models.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * AccountDetailsFrame class used for displaying and editing the account details of the user.
 */
public class AccountDetailsFrame extends JFrame {

    private User user;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField ageField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton editButton;
    private UserDAO userDAO = new UserDAO();

    /**
     * Constructor for the AccountDetailsFrame class.
     * @param user The user whose account details are being displayed.
     */
    public AccountDetailsFrame(User user) {
        super("Account Details");
        setSize(300, 270);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        this.user = user;

        initComps();
        layoutComps();
        activateComps();
    }

    /**
     * Initializes the components for the user account form.
     * Sets the initial values for the fields and creates the necessary UI components.
     */
    private void initComps() {
        // Initialize text fields with user information
        nameField = new JTextField(user.getName());
        surnameField = new JTextField(user.getSurname());
        ageField = new JTextField(String.valueOf(user.getAge()));
        usernameField = new JTextField(user.getCredential().getUsername());
        passwordField = new JTextField(user.getPlainPassword() != null ? user.getPlainPassword() : "");

        // Set initial editable state and create buttons
        changeEditable();
        editButton = new JButton("Edit");
        cancelButton = new JButton("Exit");
        cancelButton.setVisible(false);
        saveButton = new JButton("Save");
        saveButton.setVisible(false);
    }

    /**
     * Configures the layout of the user account form.
     * Adds labels, text fields, and buttons to the layout.
     */
    private void layoutComps() {
        setLayout(new MigLayout("insets 20, gapy 10"));

        add(new JLabel("Name:"));
        add(nameField, "wrap, w 100%, span");

        add(new JLabel("Surname:"));
        add(surnameField, "wrap, w 100%, span");

        add(new JLabel("Age:"));
        add(ageField, "wrap, w 100%, span");

        add(new JLabel("Username:"));
        add(usernameField, "wrap, w 100%, span");

        add(new JLabel("Password:"));
        add(passwordField, "wrap, w 100%, span");

        add(cancelButton, "align left");
        add(saveButton, "align center");
        add(editButton, "align right");
    }

    /**
     * Activates the components by adding action listeners to buttons.
     * Defines the behavior of the buttons when clicked.
     */
    private void activateComps() {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.isEditable()) {
                    changeEditable();
                    cancelButton.setVisible(false);
                    saveButton.setVisible(false);
                } else {
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit your account details?", "Edit Account Details", JOptionPane.YES_NO_OPTION);
                    if (option == 0) {
                        changeEditable();
                        cancelButton.setVisible(true);
                        saveButton.setVisible(true);
                    }
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (nameField.getText().isEmpty() || surnameField.getText().isEmpty() || ageField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (userDAO.checkUserExists(usernameField.getText()) && !usernameField.getText().equals(user.getCredential().getUsername())) {
                    JOptionPane.showMessageDialog(null, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("Old user: " + user);
                    user.setName(nameField.getText());
                    user.setSurname(surnameField.getText());
                    user.setAge(Integer.parseInt(ageField.getText()));
                    user.getCredential().setUsername(usernameField.getText());
                    user.getCredential().setPassword(PasswordUtil.hashPassword(passwordField.getText()));
                    System.out.println("User: " + user);
                    System.out.println("Credentials: " + user.getCredential());
                    userDAO.updateUser(user);
                    JOptionPane.showMessageDialog(null, "Account details saved successfully!");
                    dispose();
                }
            }
        });
    }

    /**
     * Changes the editable state of the text fields.
     * If the fields are editable, they will be set to read-only.
     * If the fields are read-only, they will be set to editable.
     */
    private void changeEditable() {
        if (nameField.isEditable()) {
            nameField.setEditable(false);
            surnameField.setEditable(false);
            ageField.setEditable(false);
            usernameField.setEditable(false);
            passwordField.setEditable(false);
        } else {
            nameField.setEditable(true);
            surnameField.setEditable(true);
            ageField.setEditable(true);
            usernameField.setEditable(true);
            passwordField.setEditable(true);
        }
    }
}
