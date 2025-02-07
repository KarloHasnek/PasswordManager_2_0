package com.karlohasnek.view.listeners;

import java.util.EventListener;

public interface LoginListener extends EventListener {

    void loginEventOccurred(LoginEvent e);
}
