package com.example.demo.controllers;

import com.example.demo.Entities.User;
import com.example.demo.repositories.*;
import com.example.demo.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorkerProfileRepository workerProfileRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public Map<String, String> register(@RequestBody Map<String, String> request) {
		String username = request.get("username");
		String password = request.get("password");
		String email = request.get("email");
		String role = request.getOrDefault("role", "USER");

		if (userRepository.findByEmail(email).isPresent()) {
			return Map.of("error", "Email already exists");
		}

		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(passwordEncoder.encode(password));
		newUser.setEmail(email);
		newUser.setRole(role.toUpperCase());
		userRepository.save(newUser);
		return Map.of("message", "User registered successfully");
	}
	
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Map<String, String> request) {
	    String password = request.get("password");
	    String email = request.get("email");
	    Authentication auth = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(email, password)
	    );

	    if (auth.isAuthenticated()) {
	        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
	        String token = jwtService.generateToken(email);
	        boolean onboarded = true;
	        Long profileId = null;

	        if ("WORKER".equalsIgnoreCase(user.getRole())) {
	            var workerProfileOpt = workerProfileRepository.findByUser(user);
	            if (workerProfileOpt.isEmpty()) {
	                onboarded = false;
	            } else {
	                profileId = workerProfileOpt.get().getId();
	            }
	        }
	        Map<String, Object> response = new HashMap<>();
	        response.put("token", token);
	        response.put("role", user.getRole());
	        response.put("userId", user.getId());
	        response.put("profileId", profileId);
	        response.put("onboarded", onboarded);
	        return response;
	    }
	    throw new RuntimeException("Invalid login credentials");
	}
}
