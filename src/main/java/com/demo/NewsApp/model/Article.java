package com.demo.NewsApp.model;

import jakarta.persistence.*;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    private Source source;
    private String title;
    private String content;
    private boolean isRead; // New field
    private boolean isFavorite; // New field


    public Article() {
    }

    public Article(int id,Source source, String title, String content, boolean isRead, boolean isFavorite) {
        this.id = id;
        this.source = source;
        this.title = title;
        this.content = content;
        this.isRead = isRead;
        this.isFavorite = isFavorite;
    }

    public Source getsource() {
        return source;
    }

    public void setId(Source source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "Article{" +
                "source=" + source +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isRead=" + isRead +
                ", isFavorite=" + isFavorite +
                '}';
    }
}

