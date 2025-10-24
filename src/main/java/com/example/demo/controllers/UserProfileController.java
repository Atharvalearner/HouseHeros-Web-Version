package com.example.demo.controllers;

import com.example.demo.models.UserProfile;
import com.example.demo.services.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController {

	private final UserProfileService userProfileService;

	public UserProfileController(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	@PostMapping
	public ResponseEntity<UserProfile> createProfile(Authentication authentication, @RequestBody UserProfile payload) {
		User userDetails = (User) authentication.getPrincipal();
		String email = userDetails.getUsername();
		UserProfile saved = userProfileService.createProfile(email, payload);
		return ResponseEntity.ok(saved);
	}
	
	@PutMapping
	public ResponseEntity<UserProfile> updateProfile(Authentication authentication, @RequestBody UserProfile payload) {
	    User userDetails = (User) authentication.getPrincipal();
	    String email = userDetails.getUsername();
	    UserProfile updated = userProfileService.updateProfile(email, payload);
	    return ResponseEntity.ok(updated);
	}


	@GetMapping("/me")
	public ResponseEntity<?> getMyProfile(Authentication authentication) {
		String email = (String) authentication.getPrincipal();
		return userProfileService.getByUserEmail(email).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
