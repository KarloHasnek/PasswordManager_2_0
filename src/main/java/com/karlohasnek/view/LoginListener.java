package com.karlohasnek.view;

import java.util.EventListener;

public interface LoginListener extends EventListener {

    void loginEventOccurred(LoginEvent e);
}
