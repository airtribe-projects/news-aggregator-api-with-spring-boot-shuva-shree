package com.demo.NewsApp.service;

import com.demo.NewsApp.entity.UserPreference;
import com.demo.NewsApp.model.Article;
import com.demo.NewsApp.model.Result;
import com.demo.NewsApp.repo.NewsRepository;
import com.demo.NewsApp.repo.VerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class NewsService {


    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    public WebClient _webClient;
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    public Mono<List<Article>> getNews(UserPreference preferences) {
        String apiUrl = buildNewsApiUrl(preferences);


        return _webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(Result.class)
                .map(Result::getnewsItems) // Extract the list of articles from the Result object
                .doOnNext(articles -> logger.info("Fetched articles: " + articles));
    }

    private String buildNewsApiUrl(UserPreference preferences) {
        String baseUrl = "https://newsapi.org/v2/everything";
        String apiKey = "79cfe87d34b04cad912f7e6aae8158a8"; // Store securely

        // Build query parameters
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append("?apiKey=").append(apiKey);

        if (preferences.getKeyword() != null && !preferences.getKeyword().isEmpty()) {
            urlBuilder.append("&q=").append(preferences.getKeyword());
        }
        if (preferences.getCategory() != null && !preferences.getCategory().isEmpty()) {
            urlBuilder.append("&category=").append(preferences.getCategory());
        }
        if (preferences.getCountry() != null && !preferences.getCountry().isEmpty()) {
            urlBuilder.append("&country=").append(preferences.getCountry());
        }
        if (preferences.getLanguage() != null && !preferences.getLanguage().isEmpty()) {
            urlBuilder.append("&language=").append(preferences.getLanguage());
        }

        return urlBuilder.toString();
    }

    public void markAsRead(Long id) {
        Article article = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News article not found"));
        article.setRead(true);
        newsRepository.save(article);
    }

    // Mark a news article as favorite
    public void markAsFavorite(Long id) {
        Article article = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News article not found"));
        article.setFavorite(true);
        newsRepository.save(article);
    }

    // Retrieve all read news articles
    public List<Article> getReadArticles() {
        return newsRepository.findByIsRead(true);
    }

    // Retrieve all favorite news articles
    public List<Article> getFavoriteArticles() {
        return newsRepository.findByIsFavorite(true);
    }
}




