package com.example.demo.controllers;

import com.example.demo.models.UpdateWorkerProfileRequest;
import com.example.demo.models.UpdateWorkerProfileResponse;
import com.example.demo.Entities.WorkerProfile;
import com.example.demo.services.WorkerProfileService;
import com.example.demo.utils.SecurityUtil;
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
	public String createProfile(@RequestBody WorkerProfile payload) {
		String email = SecurityUtil.getCurrentUserEmail();
		workerProfileService.createProfile(email, payload);
		return "Worker Profile created Successfully";
	}

	@PutMapping
	public UpdateWorkerProfileResponse updateProfile(@RequestBody UpdateWorkerProfileRequest updateWorkerProfileRequest) {
		String email = SecurityUtil.getCurrentUserEmail();
		return workerProfileService.updateProfile(email, updateWorkerProfileRequest);
	}

	@GetMapping("/me")
	public ResponseEntity<?> getMyProfile() {
		String email = SecurityUtil.getCurrentUserEmail();
		return workerProfileService.getByUserEmail(email).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Public: list all approved workers (for customers)
//	@GetMapping("/public")
//	public ResponseEntity<List<WorkerProfile>> getPublicWorkers() {
//		List<WorkerProfile> list = workerProfileService.getAllApproved();
//		return ResponseEntity.ok(list);
//	}

	// Public: list all workers (for customers)
//	@GetMapping("/public")
//	public ResponseEntity<List<WorkerProfile>> getPublicWorkers() {
//		List<WorkerProfile> list = workerProfileService.getAllWorkers();
//		return ResponseEntity.ok(list);
//	}

	@GetMapping("/search")
	public List<WorkerProfile> searchWorkers(@RequestParam(required = false) String city,
			@RequestParam(required = false) String skill, @RequestParam(required = false) Integer minExp,
			@RequestParam(required = false) Integer maxRate, @RequestParam(required = false) Double minRating,
			@RequestParam(required = false) String occupation) {

        return workerProfileService.searchWorkers(city, skill, minExp, maxRate, minRating, occupation);
	}
}
