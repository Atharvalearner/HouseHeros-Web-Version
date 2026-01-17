package com.example.demo.services;

import com.example.demo.Entities.User;
import com.example.demo.Entities.UserProfile;
import com.example.demo.models.*;
import com.example.demo.repositories.UserProfileJPARepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class UserProfileService {

	private final UserProfileJPARepository userProfileJPARepository;
	private final UserRepository userRepository;

	public UserProfileService(UserProfileJPARepository userProfileJPARepository, UserRepository userRepository) {
		this.userProfileJPARepository = userProfileJPARepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public void createProfile(String userEmail, CreateUserProfileRequest createUserProfileRequest) {
		User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
		Optional<UserProfile> existing = userProfileJPARepository.findByUser(user);

		UserProfile profile = existing.orElseGet(UserProfile::new);
		profile.setUser(user);
		profile.setFullName(createUserProfileRequest.getFullName());
		profile.setPhone(createUserProfileRequest.getPhone());
		profile.setAddress(createUserProfileRequest.getAddress());
		profile.setCity(createUserProfileRequest.getCity());
		profile.setUpdatedAt(new Date());
		userProfileJPARepository.save(profile);
	}
	
	@Transactional
	public UpdateUserProfileResponse updateProfile(String userEmail,UpdateUserProfileRequest updateUserProfileRequest) throws Exception  {
		User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
		UserProfile profile = userProfileJPARepository.findByUser(user).orElseThrow(() -> new RuntimeException("UserProfile not found"));

		if (updateUserProfileRequest.getFullName() != null && !updateUserProfileRequest.getFullName().isBlank()) {
			profile.setFullName(updateUserProfileRequest.getFullName());
		}
		if (updateUserProfileRequest.getPhone() != null && !updateUserProfileRequest.getPhone().isBlank()) {
			profile.setPhone(updateUserProfileRequest.getPhone());
		}
		if (updateUserProfileRequest.getAddress() != null && !updateUserProfileRequest.getAddress().isBlank()) {
			profile.setAddress(updateUserProfileRequest.getAddress());
		}
		if (updateUserProfileRequest.getCity() != null && !updateUserProfileRequest.getCity().isBlank()) {
			profile.setCity(updateUserProfileRequest.getCity());
		}
		profile.setUpdatedAt(new Date());

		UserProfile savedProfile = userProfileJPARepository.save(profile);

		UpdateUserProfileResponse resp = new UpdateUserProfileResponse();
		resp.setId(savedProfile.getId());
		resp.setFullName(savedProfile.getFullName());
		resp.setPhone(savedProfile.getPhone());
		resp.setAddress(savedProfile.getAddress());
		resp.setCity(savedProfile.getCity());
		resp.setUser(savedProfile.getUser());
		resp.setCreatedAt(savedProfile.getCreatedAt());
		resp.setUpdatedAt(savedProfile.getUpdatedAt());

		return resp;
	}

	public Optional<UserProfile> getUserByUserEmail(String userEmail) {
		return userRepository.findByEmail(userEmail).flatMap(userProfileJPARepository::findByUser);
	}
}
