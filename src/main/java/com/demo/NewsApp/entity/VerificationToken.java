package com.demo.NewsApp.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String token;

    @OneToOne
    private User user;

    private Date expiryDate;

    public VerificationToken() {}

    public VerificationToken(Date expiryDate, User user, String token, int id) {
        this.expiryDate = expiryDate;
        this.user = user;
        this.token = token;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
