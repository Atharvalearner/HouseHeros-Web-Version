package com.example.demo.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@GetMapping("/")
	public String userDashboard() {
		return "Welcome to User Dashboard!";
	}
}
