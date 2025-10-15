package com.example.demo.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {
	@GetMapping("/")
	public String UserDashboard() {
		return "Welcome to Worker Panel";
	}
}
