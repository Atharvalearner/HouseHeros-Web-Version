package com.example.demo.controllers;

import com.example.demo.models.CreateUserProfileRequest;
import com.example.demo.models.UpdateUserProfileRequest;
import com.example.demo.models.UpdateUserProfileResponse;
import com.example.demo.models.UserProfile;
import com.example.demo.services.UserProfileService;

import jakarta.validation.Valid;

import java.util.*;

import org.springframework.context.support.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
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
	public String createProfile(Authentication authentication, @Valid @RequestBody CreateUserProfileRequest createUserProfileRequest, BindingResult result) {
		User userDetails = (User) authentication.getPrincipal();
		String email = userDetails.getUsername();
		userProfileService.createProfile(email, createUserProfileRequest);
		return "User Profile created Successfully";
	}

	@PutMapping
	public UpdateUserProfileResponse updateProfile(Authentication authentication, @RequestBody UpdateUserProfileRequest updateUserProfileRequest) throws Exception {
		User userDetails = (User) authentication.getPrincipal();
		String email = userDetails.getUsername();
		return userProfileService.updateProfile(email, updateUserProfileRequest);
	}

//	@GetMapping("/me")
//	public ResponseEntity<?> getMyProfile(Authentication authentication) {
//		User userDetails = (User) authentication.getPrincipal();
//		String email = userDetails.getUsername();
//		return userProfileService.getByUserEmail(email).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//	}

	@GetMapping("/me")
	public ResponseEntity<?> getMyProfile(Authentication authentication) {
		System.out.println(">>> getMyProfile called <<<");
		User userDetails = (User) authentication.getPrincipal();
		String email = userDetails.getUsername();
		return userProfileService.getUserByUserEmail(email).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/test")
	public ResponseEntity<String> test(Authentication authentication) {
		if (authentication == null)
			return ResponseEntity.status(401).body("No auth");
		return ResponseEntity.ok("Authenticated as " + authentication.getName());
	}

}
