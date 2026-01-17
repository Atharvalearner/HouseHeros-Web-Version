package com.example.demo.controllers;

import com.example.demo.models.CreateUserProfileRequest;
import com.example.demo.models.UpdateUserProfileRequest;
import com.example.demo.models.UpdateUserProfileResponse;
import com.example.demo.services.UserProfileService;

import com.example.demo.utils.SecurityUtil;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController {

	private final UserProfileService userProfileService;

	public UserProfileController(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	@PostMapping
	public String createProfile(@Valid @RequestBody CreateUserProfileRequest createUserProfileRequest, BindingResult result) {
		String email = SecurityUtil.getCurrentUserEmail();
		userProfileService.createProfile(email, createUserProfileRequest);
		return "User Profile created Successfully";
	}

	@PutMapping
	public UpdateUserProfileResponse updateProfile(@RequestBody UpdateUserProfileRequest updateUserProfileRequest) throws Exception {
		String email = SecurityUtil.getCurrentUserEmail();
		return userProfileService.updateProfile(email, updateUserProfileRequest);
	}

	@GetMapping("/me")
	public ResponseEntity<?> getMyProfile() {
		String email = SecurityUtil.getCurrentUserEmail();
		return userProfileService.getUserByUserEmail(email).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/test")
	public ResponseEntity<String> test(Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body("No auth");
		}
		return ResponseEntity.ok("Authenticated as " + authentication.getName());
	}
}
