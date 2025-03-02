package com.demo.NewsApp.entity;

import com.demo.NewsApp.model.Source;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @NotNull(message = "Categories cannot be null")
    private String category;

    @OneToOne
    @NotNull(message = "Sources cannot be null")
    private Source source;
    @NotNull(message = "Keyword cannot be null")
    private String keyword;
    @NotNull(message = "Language cannot be null")
    private String language;
    @NotNull(message = "Country cannot be null")
    private String country;

    public UserPreference() {}

    public UserPreference(int id, String category, Source source, String keyword, String language, String country) {
        this.id = id;
        this.category = category;
        this.source = source;
        this.keyword = keyword;
        this.language = language;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
