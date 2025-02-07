package com.karlohasnek.view.frames;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is responsible for the password generator window.
 */
public class PasswordGenerator extends JFrame {

    private JSlider slider;
    private JCheckBox lettersChbx;
    private JCheckBox numbersChbx;
    private JCheckBox symbolsChbx;
    private JButton generateButton;
    private JTextField passwordField;
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*_=+-/.?<>)";

    /**
     * Constructor for the password generator frame.
     */
    public PasswordGenerator() {
        super("Password Generator");
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        initComps();
        layoutComps();
        activateComps();
    }

    /**
     * This method initializes the components.
     */
    private void initComps() {
        lettersChbx = new JCheckBox("Letters", true);
        numbersChbx = new JCheckBox("Numbers");
        symbolsChbx = new JCheckBox("Symbols");
        generateButton = new JButton("Generate");
        passwordField = new JTextField();
        passwordField.setEditable(false);
        slider = new JSlider(JSlider.HORIZONTAL, 8, 20, 10);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setLabelTable(slider.createStandardLabels(4));

    }

    /**
     * This method sets the layout of the components.
     */
    private void layoutComps() {
        setLayout(new MigLayout("insets 20"));
        add(new JLabel("Password Length:"));
        add(slider, "growx, wrap, w 100%");
        add(lettersChbx, "span, align center, wrap");
        add(numbersChbx, "span, align center, wrap");
        add(symbolsChbx, "span, align center, wrap");
        add(passwordField, "split 2, width 75%, span");
        add(generateButton, "align right");

    }

    /**
     * This method activates the components.
     */
    private void activateComps() {

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String generatedPassword = "";
                String passwordChars = "";

                boolean letters = lettersChbx.isSelected();
                boolean numbers = numbersChbx.isSelected();
                boolean symbols = symbolsChbx.isSelected();
                int passwordLength = slider.getValue();

                if (letters) {
                    passwordChars += LETTERS;
                }
                if (numbers) {
                    passwordChars += NUMBERS;
                }
                if (symbols) {
                    passwordChars += SYMBOLS;
                }

                try {
                    for (int i = 0; i < passwordLength; i++) {
                        int randomIndex = (int) (Math.random() * passwordChars.length());
                        generatedPassword += passwordChars.charAt(randomIndex);
                    }
                } catch (StringIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Please select at least one option!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                passwordField.setText(generatedPassword);
            }
        });

    }
}
