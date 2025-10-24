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

	@PostMapping
	public ResponseEntity<WorkerProfile> createProfile(Authentication authentication,@RequestBody WorkerProfile payload) {
		User userDetails = (User) authentication.getPrincipal();
		String email = userDetails.getUsername(); 
		WorkerProfile saved = workerProfileService.createProfile(email, payload);
		return ResponseEntity.ok(saved);
	}
	
	@PutMapping
	public ResponseEntity<WorkerProfile> updateProfile(Authentication authentication, @RequestBody WorkerProfile payload) {
	    User userDetails = (User) authentication.getPrincipal();
	    String email = userDetails.getUsername();
	    WorkerProfile updated = workerProfileService.updateProfile(email, payload);
	    return ResponseEntity.ok(updated);
	}

	@GetMapping("/me")
	public ResponseEntity<?> getMyProfile(Authentication authentication) {
		User userDetails = (User) authentication.getPrincipal();
		String email = userDetails.getUsername(); // 
		return workerProfileService.getByUserEmail(email).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Public: list all approved workers (for customers)
	@GetMapping("/public")
	public ResponseEntity<List<WorkerProfile>> getPublicWorkers() {
		List<WorkerProfile> list = workerProfileService.getAllApproved();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/search")
    public ResponseEntity<List<WorkerProfile>> searchWorkers(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String skill,
            @RequestParam(required = false) Integer minExp,
            @RequestParam(required = false) Integer maxRate,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) String occupation) {

        List<WorkerProfile> workers = workerProfileService.searchWorkers(city, skill, minExp, maxRate, minRating, occupation);
        return ResponseEntity.ok(workers);
    }
}
