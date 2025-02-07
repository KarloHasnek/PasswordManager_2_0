package com.karlohasnek.controllers;

public class PasswordService {
    public String maskPassword(String password) {
        return password.replaceAll(".", "*");
    }
}
