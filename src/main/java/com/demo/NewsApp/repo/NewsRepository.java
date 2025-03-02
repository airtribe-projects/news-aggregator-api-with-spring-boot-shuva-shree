package com.demo.NewsApp.repo;

import com.demo.NewsApp.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<Article,Long> {

    List<Article> findByIsRead(boolean isRead);
    List<Article> findByIsFavorite(boolean isFavorite);
}

