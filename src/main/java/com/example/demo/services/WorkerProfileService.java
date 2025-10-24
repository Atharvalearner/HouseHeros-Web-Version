package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.WorkerProfile;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WorkerProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerProfileService {

	private WorkerProfileRepository workerProfileRepository;
	private UserRepository userRepository;

	public WorkerProfileService(WorkerProfileRepository workerProfileRepository, UserRepository userRepository) {
		this.workerProfileRepository = workerProfileRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public WorkerProfile createProfile(String userEmail, WorkerProfile payload) {
		User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

		Optional<WorkerProfile> existing = workerProfileRepository.findByUser(user);
		WorkerProfile profile = existing.orElseGet(WorkerProfile::new);

		profile.setUser(user);
		profile.setFullName(payload.getFullName());
		profile.setOccupation(payload.getOccupation());
		profile.setExperienceYears(payload.getExperienceYears());
		profile.setHourlyRate(payload.getHourlyRate());
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

	@Transactional
	public WorkerProfile updateProfile(String userEmail, WorkerProfile payload) {
		User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
		WorkerProfile profile = workerProfileRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Profile not found"));

		if (payload.getFullName() != null)
			profile.setFullName(payload.getFullName());
		if (payload.getOccupation() != null)
			profile.setOccupation(payload.getOccupation());
		if (payload.getExperienceYears() != null)
			profile.setExperienceYears(payload.getExperienceYears());
//		if (payload.getHourlyRate() != null)
//			profile.setHourlyRate(payload.getHourlyRate());
		if (payload.getSkills() != null)
			profile.setSkills(payload.getSkills());
		if (payload.getPhone() != null)
			profile.setPhone(payload.getPhone());
		if (payload.getAddress() != null)
			profile.setAddress(payload.getAddress());
		if (payload.getCity() != null)
			profile.setCity(payload.getCity());
		if (payload.getDescription() != null)
			profile.setDescription(payload.getDescription());
		if (payload.getImageUrl() != null)
			profile.setImageUrl(payload.getImageUrl());

		profile.setIsApproved(false); // Any update requires re-approval
		profile.setUpdatedAt(Instant.now());
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

	public List<WorkerProfile> searchWorkers(String city, String skill, Integer minExp, Integer maxRate, Double minRating, String occupation) {
		return workerProfileRepository.searchWorkers(city, skill, minExp, maxRate, minRating, occupation);
	}
}
