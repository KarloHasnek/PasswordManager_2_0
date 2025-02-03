package com.karlohasnek.models;

import jakarta.persistence.*;

@Entity
@Table(name = "PasswordEntry")
public class PasswordEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "website")
    private String website;

    @Column(name = "username")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public PasswordEntry() {
    }

    public PasswordEntry(String website, String username, String password, User user) {
        this.website = website;
        this.username = username;
        this.password = password;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PasswordEntry{" +
                "id=" + id +
                ", website='" + website + '\'' +
                ", username='" + username + '\'' +
                ", password='" + (password != null ? password : "N/A") + '\'' +
                ", userId=" + (user != null ? user.getId() : "N/A") +
                '}';
    }
}

