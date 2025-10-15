package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.WorkerProfile;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WorkerProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerProfileService {

	private final WorkerProfileRepository workerProfileRepository;
	private final UserRepository userRepository;

	public WorkerProfileService(WorkerProfileRepository workerProfileRepository, UserRepository userRepository) {
		this.workerProfileRepository = workerProfileRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public WorkerProfile createOrUpdateProfile(String userEmail, WorkerProfile payload) {
		User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

		Optional<WorkerProfile> existing = workerProfileRepository.findByUser(user);
		WorkerProfile profile = existing.orElseGet(WorkerProfile::new);

		profile.setUser(user);
		profile.setFullName(payload.getFullName());
		profile.setOccupation(payload.getOccupation());
		profile.setExperienceYears(payload.getExperienceYears());
		profile.setSkills(payload.getSkills());
		profile.setPhone(payload.getPhone());
		profile.setAddress(payload.getAddress());
		profile.setCity(payload.getCity());
		profile.setDescription(payload.getDescription());
		profile.setImageUrl(payload.getImageUrl());
		profile.setUpdatedAt(Instant.now());

		// When editing, mark as not approved until admin re-approves
		if (existing.isPresent())
			profile.setIsApproved(false);

		return workerProfileRepository.save(profile);
	}

	public Optional<WorkerProfile> getByUserEmail(String userEmail) {
		return userRepository.findByEmail(userEmail).flatMap(workerProfileRepository::findByUser);
	}

	public List<WorkerProfile> getAllApproved() {
		return workerProfileRepository.findByIsApprovedTrue();
	}

	public List<WorkerProfile> getAllForAdmin() {
		return workerProfileRepository.findAll();
	}

	@Transactional
	public WorkerProfile approveProfile(Long profileId, boolean approve) {
		WorkerProfile p = workerProfileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
		p.setIsApproved(approve);
		p.setUpdatedAt(Instant.now());
		return workerProfileRepository.save(p);
	}
}
