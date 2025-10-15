package com.example.demo.controllers;

import com.example.demo.models.WorkerProfile;
import com.example.demo.services.WorkerProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminWorkerController {

	private final WorkerProfileService workerProfileService;

	public AdminWorkerController(WorkerProfileService workerProfileService) {
		this.workerProfileService = workerProfileService;
	}

	@GetMapping("/workers")
	public ResponseEntity<List<WorkerProfile>> listAllWorkers() {
		return ResponseEntity.ok(workerProfileService.getAllForAdmin());
	}

	@PutMapping("/workers/{id}/approve")
	public ResponseEntity<WorkerProfile> approveWorker(@PathVariable Long id, @RequestParam(name = "approve", defaultValue = "true") boolean approve) {
		WorkerProfile updated = workerProfileService.approveProfile(id, approve);
		return ResponseEntity.ok(updated);
	}
}
