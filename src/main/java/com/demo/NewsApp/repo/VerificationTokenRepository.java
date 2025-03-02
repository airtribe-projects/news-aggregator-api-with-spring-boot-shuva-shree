package com.demo.NewsApp.repo;

import com.demo.NewsApp.entity.VerificationToken;
import com.demo.NewsApp.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerificationTokenRepository  extends JpaRepository<VerificationToken,Integer> {
    VerificationToken findByToken(String token);

}
