package com.example.madagascar.model;

public class Identification {

    private Utilisateur user;
    private String token;
    private String oldToken;

    public Identification(String token, String oldToken) {
        this.token = token;
        this.oldToken = oldToken;
    }

    public Identification() {
    }

    public Identification(Utilisateur user, String token) {
        this.user = user;
        this.token = token;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOldToken() {
        return oldToken;
    }

    public void setOldToken(String oldToken) {
        this.oldToken = oldToken;
    }
}
