package com.karlohasnek.view;

import com.karlohasnek.controllers.UserDAO;
import com.karlohasnek.models.Credential;
import com.karlohasnek.models.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Frame for creationg a new user for the application.
 */
public class RegisterFrame extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 450;
    private JLabel icon;
    private JLabel title;
    private JTextField nameField;
    private JTextField surnameField;
    private JSpinner ageSpinner;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private UserDAO userDAO;

    /**
     * Constructor for the register frame.
     */
    public RegisterFrame() {
        super("Registration");
        setSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/icon16.png");
        setIconImage(icon);
        setAlwaysOnTop(true);

        initSpinner();
        initComps();
        layoutComps();
        activateComps();
    }

    /**
     * Initializes the spinner for the age.
     */
    private void initSpinner() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1900);
        Date startDate = calendar.getTime();
        calendar.set(Calendar.YEAR, 2100);
        Date endDate = calendar.getTime();
        SpinnerDateModel model = new SpinnerDateModel(new Date(), startDate, endDate, Calendar.DAY_OF_MONTH);
        ageSpinner = new JSpinner(model);
        ageSpinner.setFont(new Font("Calibri", Font.PLAIN, 14));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(ageSpinner, "dd/MM/yyyy");
        SimpleDateFormat format = editor.getFormat();
        format.setLenient(false);
        ageSpinner.setEditor(editor);
        userDAO = new UserDAO();
    }

    /**
     * Initializes the components of the frame.
     */
    private void initComps() {
        icon = new JLabel();
        icon.setIcon(new ImageIcon("src/main/resources/user.png"));
        title = new JLabel("Register");
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        nameField = new JTextField(15);
        surnameField = new JTextField(15);
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        registerButton = new JButton("Register");
    }

    /**
     * Lays out the components of the frame.
     */
    private void layoutComps() {
        setLayout(new MigLayout("insets 50 50 50 50, center"));
        add(icon, "center, span, wrap");
        add(new JLabel("Name: "), "right");
        add(nameField, "wrap");
        add(new JLabel("Surname: "), "right");
        add(surnameField, "wrap");
        add(new JLabel("Age: "), "right");
        add(ageSpinner, "grow, wrap");
        add(new JLabel("Username: "), "right");
        add(usernameField, "wrap");
        add(new JLabel("Password: "), "right");
        add(passwordField, "wrap");
        add(new JLabel("Confirm password: "), "right");
        add(confirmPasswordField, "wrap");
        add(registerButton, "center, span");

    }

    /**
     * Activates the components of the frame.
     */
    private void activateComps() {

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("REGISTER BUTTON WAS PRESSSED");
                String name = nameField.getText();
                String surname = surnameField.getText();
                String age = new SimpleDateFormat("dd/MM/yyyy").format(ageSpinner.getValue());
                String username = usernameField.getText();

                if (name.isEmpty() || surname.isEmpty() || age.isEmpty() || username.isEmpty() || passwordField.getPassword().length == 0 || confirmPasswordField.getPassword().length == 0) {
                    System.out.println("Please fill all the fields!");
                    resetPasswordField();
                    String messageF = "Something went wrong!\nPlease fill all the fields.";
                    JOptionPane.showMessageDialog(RegisterFrame.this, messageF, "Error", JOptionPane.ERROR_MESSAGE);
                } else if (userDAO.checkUserExists(usernameField.getText())) {
                    System.out.println("Username already exists!");
                    usernameField.setText("");
                    resetPasswordField();
                    String message1 = "Username already exists!\nPlease choose another one.";
                    JOptionPane.showMessageDialog(RegisterFrame.this, message1, "Error", JOptionPane.ERROR_MESSAGE);
                } else if (Arrays.toString(passwordField.getPassword()).equals(Arrays.toString(confirmPasswordField.getPassword()))) {
                    System.out.println("Passwords match");
                    String password = convertPasswordToString(passwordField.getPassword());
                    Map<String, String> map = new HashMap<>();
                    map.put(username, password);
                    System.out.println("age: " + age);
                    int ageInt = calculateAge((Date) ageSpinner.getValue());
                    User newUser = new User(name, surname, ageInt);
                    newUser.setCredential(new Credential(username, password, newUser));
                    userDAO.saveUser(newUser);
                    String message2 = "You have successfully registered!";
                    JOptionPane.showMessageDialog(RegisterFrame.this, message2, "Registration Complete", JOptionPane.INFORMATION_MESSAGE);
                    if (JOptionPane.OK_OPTION == 0) {
                        dispose();
                    }
                } else {
                    System.out.println("Passwords don't match");
                    resetPasswordField();
                    String message = "Oops! Seems like passwords don't match.\nPlease try again";
                    JOptionPane.showMessageDialog(RegisterFrame.this, message, "Password missmatch", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    /**
     * Resets the password fields in case of error during registration.
     */
    private void resetPasswordField() {
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    /**
     * This method converts a char array to a String.
     *
     * @param password the char array to be converted
     * @return the converted String
     */
    private String convertPasswordToString(char[] password) {
        String sb = "";
        for (char c : password) {
            sb += c;
        }
        return sb;
    }

    /**
     * This method calculates the age of the user based on the birth date.
     *
     * @param birthDate the birth date of the user
     * @return the age of the user
     */
    private int calculateAge(Date birthDate) {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        Calendar currentCalendar = Calendar.getInstance();
        int age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        if (currentCalendar.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
                (currentCalendar.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) &&
                        currentCalendar.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }
}