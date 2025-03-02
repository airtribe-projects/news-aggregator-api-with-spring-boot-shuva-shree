package com.demo.NewsApp.controller;


import com.demo.NewsApp.entity.User;
import com.demo.NewsApp.model.UserPojo;
import com.demo.NewsApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody UserPojo userPojo) {
        User registeredUser = userService.registerUser(userPojo);
        String token = UUID.randomUUID().toString();
        String applicationUrl = "http://localhost:8080/verifyRegistration?token=" + token;
        userService.createVerificationToken(registeredUser,token);
        System.out.println("Verification token created for user: "+registeredUser.getEmail());
        System.out.println("Verification url please click this url to verify your email"+applicationUrl);

        return registeredUser;
    }

    @PostMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam String token) {
        boolean isValid = userService.validateToken(token);
        if(isValid){
            userService.enableUser(token);
            return "User enable successfully";
        }else
            return "Invalid token";
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestParam String email , @RequestParam String password) {
        return userService.signInUser(email,password);
    }
}
