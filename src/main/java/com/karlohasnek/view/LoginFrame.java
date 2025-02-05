package com.karlohasnek.view;

import com.karlohasnek.models.Credential;
import com.karlohasnek.models.User;
import jakarta.persistence.Query;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LoginFrame extends JFrame {

    private JLabel icon;
    private JLabel title;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton logIn;
    private JButton register;
    private List<User> users;
    private List<Credential> usersCredentials;
    private LoginListener loginListener;

    /**
     * Constructor for the login frame.
     */
    public LoginFrame() {
        super("Password Manager Login");
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/icon16.png");
        setIconImage(icon);

        initComps();
        layoutComps();
    }

    /**
     * Initializes the components for the login frame.
     * Reads user data from the database, creates labels, text fields, and buttons.
     */
    private void initComps() {
        users = new ArrayList<>();
        icon = new JLabel();
        icon.setIcon(new ImageIcon("src/main/resources/icon64.png"));
        title = new JLabel("Password Manager");
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        logIn = new JButton("Log In");
        register = new JButton("Register");
    }

//    private <E> void printAllResults(List<E> lista) {
//        for (E entry : lista) {
//            System.out.println(entry);
//        }
//    }

    /**
     * Configures the layout of the components in the login frame.
     * Adds labels, text fields, and buttons to the layout.
     */
    private void layoutComps() {
        setLayout(new MigLayout("insets 180 50 50 50, center", "", "[]20[][]"));
        add(icon, "split 2, center, span");
        add(title, "center, wrap");
        add(new JLabel("Username: "), "split 2, center");
        add(usernameField, "split 2, center, wrap");
        add(new JLabel("Password: "), "split 2, center, gapafter 10");
        add(passwordField, "split 2, center, wrap");
        add(logIn, "split 2, center, span, gapafter 40");
        add(register);
    }

    /**
     * Sets the login listener for the login frame.
     * The login listener is notified when the user successfully logs in.
     *
     * @param loginListener The LoginListener to be set.
     */
    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    /**
     * Activates the components by adding action listeners to buttons.
     * Defines the behavior of the buttons when clicked.
     */
    public void activateComps() {

        if (loginListener != null) {

            logIn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String username = usernameField.getText();
                    if (username.equals("")) {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Please enter a username!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String password = new String(passwordField.getPassword());
                    if (password.equals("")) {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Please enter a password!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (usernameField.getText().equals("admin") && passwordField.getText().equals("admin")) {
                        new AdminFrame().setAlwaysOnTop(true);
                        dispose();
                    } else {
                        loginListener.loginEventOccurred(new LoginEvent(this, username, password));
                    }

                }
            });
        }

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Register button pressed!");
                new RegisterFrame().setAlwaysOnTop(true);
            }
        });
    }
}