package com.example.demo.controllers;

import com.example.demo.models.WorkerProfile;
import com.example.demo.services.WorkerProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/worker/profile")
public class WorkerProfileController {

	private final WorkerProfileService workerProfileService;

	public WorkerProfileController(WorkerProfileService workerProfileService) {
		this.workerProfileService = workerProfileService;
	}

	// Create or update: only for authenticated workers
	@PostMapping
	public ResponseEntity<WorkerProfile> createOrUpdateProfile(Authentication authentication,@RequestBody WorkerProfile payload) {

		User userDetails = (User) authentication.getPrincipal();
		String email = userDetails.getUsername(); // ✅ get logged-in user’s email
		WorkerProfile saved = workerProfileService.createOrUpdateProfile(email, payload);
		return ResponseEntity.ok(saved);
	}

	@GetMapping("/me")
	public ResponseEntity<?> getMyProfile(Authentication authentication) {
		User userDetails = (User) authentication.getPrincipal();
		String email = userDetails.getUsername(); // ✅ fix applied here too
		return workerProfileService.getByUserEmail(email).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Public: list all approved workers (for customers)
	@GetMapping("/public")
	public ResponseEntity<List<WorkerProfile>> getPublicWorkers() {
		List<WorkerProfile> list = workerProfileService.getAllApproved();
		return ResponseEntity.ok(list);
	}
}
