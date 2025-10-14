package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/")
    public String hello() {
        return "Backend is working!";
    }
    
    @GetMapping("/api/user/profile")
    public String userProfile() {
        return "Access granted: You are logged in!";
    }
}