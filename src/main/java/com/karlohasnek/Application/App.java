package com.karlohasnek.Application;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.karlohasnek.view.MainFrame;

import javax.swing.*;

/**
 * Main class of the application.
 */
public class App {

    /**
     * Main method of the application that starts the GUI and sets the look and feel of the application.
     * @param args
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}
