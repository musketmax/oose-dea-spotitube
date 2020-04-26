package com.thomas.spotitube.domain;

public class User {
    private int id;
    private String user;
    private String token;
    private String password;

    public User(int id, String user, String token) {
        this.id = id;
        this.user = user;
        this.token = token;
    }

    public void setId(int id) { this.id = id; }

    public int getId() { return id; }

    public void setUser(String user) { this.user = user; }

    public String getUser() { return user; }

    public void setToken() { this.token = token; }

    public String getToken() { return token; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
