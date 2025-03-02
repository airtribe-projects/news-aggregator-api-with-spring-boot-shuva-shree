package com.demo.NewsApp.service;


import com.demo.NewsApp.entity.User;
import com.demo.NewsApp.entity.UserPreference;
import com.demo.NewsApp.entity.VerificationToken;
import com.demo.NewsApp.model.UserPojo;
import com.demo.NewsApp.repo.UserRepository;
import com.demo.NewsApp.repo.VerificationTokenRepository;
import com.demo.NewsApp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public User registerUser(UserPojo userPojo) {
        User databaseUser = new User();
        databaseUser.setEmail(userPojo.getEmail());
        databaseUser.setPassword(passwordEncoder.encode(userPojo.getPassword()));

        UserPreference preferences = new UserPreference();
        preferences.setCategory("default_category");
        preferences.setKeyword("default_keyword");
        preferences.setLanguage("default_language");
        preferences.setCountry("default_country");

        databaseUser.setPreferences(preferences); // Set the UserPreference object


        databaseUser.setRole("USER");
        return userRepository.save(databaseUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return null;
    }


    public void createVerificationToken(User registeredUser, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(registeredUser);
        long expiryDate = (new Date().getTime() + 1000 * 60 * 60 * 24);
        verificationToken.setExpiryDate(new Date(expiryDate));
        verificationTokenRepository.save(verificationToken);

    }

    public boolean validateToken(String token) {
        VerificationToken storedToken = verificationTokenRepository.findByToken(token);
        if (storedToken == null) {
            return false;
        }
        long expiryTime = storedToken.getExpiryDate().getTime();
        if(expiryTime < System.currentTimeMillis()) {
            return false;
        }
        return true;

    }

    public void enableUser(String token) {
        VerificationToken storedToken = verificationTokenRepository.findByToken(token);
        User storedUser = storedToken.getUser();
        storedUser.setEnabled(true);
        userRepository.save(storedUser);
        verificationTokenRepository.delete(storedToken);
    }

    public String signInUser(String email, String password) {
        User storedUser = userRepository.findByEmail(email);
        if(storedUser == null || !storedUser.isEnabled()) {
            return "User not found 0or enabled";
        }
        boolean isPasswordMatch = passwordEncoder.matches(password, storedUser.getPassword());

        if (!isPasswordMatch) {
            return "Incorrect Password";
        }
        List<String> roles =  Collections.singletonList(storedUser.getRole());
        System.out.println("Roles being set: " + roles);

        String token = JwtUtil.generateToken(email,roles);
        System.out.println("Generated JWT Token"+token);
        return token;

    }


}
