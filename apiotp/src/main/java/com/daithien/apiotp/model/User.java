package com.daithien.apiotp.model;

public class User {
    private String email;
    private String password;
    private boolean isActive;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.isActive = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}


