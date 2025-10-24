package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.UserProfile;
import com.example.demo.repositories.UserProfileRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserProfileService {

	private final UserProfileRepository userProfileRepository;
	private final UserRepository userRepository;

	public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
		this.userProfileRepository = userProfileRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public UserProfile createProfile(String userEmail, UserProfile payload) {
		User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
		Optional<UserProfile> existing = userProfileRepository.findByUser(user);
		UserProfile profile = existing.orElseGet(UserProfile::new);

		profile.setUser(user);
		profile.setFullName(payload.getFullName());
		profile.setPhone(payload.getPhone());
		profile.setAddress(payload.getAddress());
		profile.setCity(payload.getCity());

		return userProfileRepository.save(profile);
	}
	
	@Transactional
	public UserProfile updateProfile(String userEmail, UserProfile payload) {
	    User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
	    UserProfile profile = userProfileRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Profile not found"));

	    // Update only fields that are provided
	    if (payload.getFullName() != null) profile.setFullName(payload.getFullName());
	    if (payload.getPhone() != null) profile.setPhone(payload.getPhone());
	    if (payload.getAddress() != null) profile.setAddress(payload.getAddress());
	    if (payload.getCity() != null) profile.setCity(payload.getCity());
	    return userProfileRepository.save(profile);
	}

	public Optional<UserProfile> getByUserEmail(String userEmail) {
		return userRepository.findByEmail(userEmail).flatMap(userProfileRepository::findByUser);
	}
}
