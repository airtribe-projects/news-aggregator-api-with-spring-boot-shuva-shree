package com.demo.NewsApp.entity;
import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "preference_id", referencedColumnName = "id")
    public static UserPreference preferences;
    private String role;
    private boolean isEnabled;

    public User() {}

    public User(int id, String email, String password, UserPreference preferences,String role,boolean isEnabled) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.preferences = preferences;
        this.role=role;
        this.isEnabled=isEnabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public UserPreference getPreferences() {
        return preferences;
    }

    public void setPreferences(UserPreference preferences) {
        this.preferences = preferences;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}

