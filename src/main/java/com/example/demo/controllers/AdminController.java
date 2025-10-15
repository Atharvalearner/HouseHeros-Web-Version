package com.example.demo.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@GetMapping("/dashboard")
	public String adminDashboard() {
		return "Welcome to Admin Dashboard!";
	}
}