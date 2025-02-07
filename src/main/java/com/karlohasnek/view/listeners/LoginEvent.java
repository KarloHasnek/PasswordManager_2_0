package com.karlohasnek.view.listeners;

import com.karlohasnek.models.User;

import java.util.EventObject;

public class LoginEvent extends EventObject {

    private User user;

    public LoginEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    @Override
    public String toString() {
        return "LoginEvent{" + "user=" + user + '}';
    }
}
