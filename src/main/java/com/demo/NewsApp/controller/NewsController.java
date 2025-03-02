package com.demo.NewsApp.controller;

import com.demo.NewsApp.entity.User;
import com.demo.NewsApp.entity.UserPreference;
import com.demo.NewsApp.model.Article;
import com.demo.NewsApp.model.Result;
import com.demo.NewsApp.service.NewsService;
import com.demo.NewsApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/api")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;


    @GetMapping("/preferences")
    public ResponseEntity<?> getPreferences(@AuthenticationPrincipal String username) {
        User user = (User) userService.loadUserByUsername(username);
        return ResponseEntity.ok(user.getPreferences());
    }

    @PutMapping("/preferences")
    public ResponseEntity<String> updatePreferences(@AuthenticationPrincipal String username, @RequestBody UserPreference preferences) {
        User user = (User) userService.loadUserByUsername(username);
        user.setPreferences(preferences);
        return ResponseEntity.ok("Preferences updated successfully");
    }
    @GetMapping("/news")
    @PreAuthorize("hasRole('USER')")
    public Mono<List<Article>> getNews(@RequestParam UserPreference preferences) {
        return newsService.getNews(preferences);
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        newsService.markAsRead(id);
        return ResponseEntity.ok("News article marked as read");
    }

    // Mark a news article as favorite
    @PostMapping("/{id}/favorite")
    public ResponseEntity<String> markAsFavorite(@PathVariable Long id) {
        newsService.markAsFavorite(id);
        return ResponseEntity.ok("News article marked as favorite");
    }

    // Retrieve all read news articles
    @GetMapping("/read")
    public ResponseEntity<List<Article>> getReadArticles() {
        List<Article> readArticles = newsService.getReadArticles();
        return ResponseEntity.ok(readArticles);
    }

    // Retrieve all favorite news articles
    @GetMapping("/favorites")
    public ResponseEntity<List<Article>> getFavoriteArticles() {
        List<Article> favoriteArticles = newsService.getFavoriteArticles();
        return ResponseEntity.ok(favoriteArticles);
    }




}
