package com.example.demo.services;

import com.example.demo.models.UpdateWorkerProfileRequest;
import com.example.demo.models.UpdateWorkerProfileResponse;
import com.example.demo.Entities.User;
import com.example.demo.Entities.WorkerProfile;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WorkerProfileRepository;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerProfileService {

	@Autowired
	private WorkerProfileRepository workerProfileRepository;
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void createProfile(String userEmail, WorkerProfile payload) {
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
		profile.setUpdatedAt(new Date());
		workerProfileRepository.save(profile);
	}

	@Transactional
	public UpdateWorkerProfileResponse updateProfile(String userEmail, UpdateWorkerProfileRequest updateWorkerProfileRequest) {
		User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
		WorkerProfile profile = workerProfileRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Profile not found"));

		if (updateWorkerProfileRequest.getFullName() != null)
			profile.setFullName(updateWorkerProfileRequest.getFullName());
		if (updateWorkerProfileRequest.getOccupation() != null)
			profile.setOccupation(updateWorkerProfileRequest.getOccupation());
		if (updateWorkerProfileRequest.getExperienceYears() != 0)
            profile.setExperienceYears(updateWorkerProfileRequest.getExperienceYears());
		if (updateWorkerProfileRequest.getHourlyRate() > 0)
			profile.setHourlyRate(updateWorkerProfileRequest.getHourlyRate());
		if (updateWorkerProfileRequest.getSkills() != null)
			profile.setSkills(updateWorkerProfileRequest.getSkills());
		if (updateWorkerProfileRequest.getPhone() != null)
			profile.setPhone(updateWorkerProfileRequest.getPhone());
		if (updateWorkerProfileRequest.getAddress() != null)
			profile.setAddress(updateWorkerProfileRequest.getAddress());
		if (updateWorkerProfileRequest.getCity() != null)
			profile.setCity(updateWorkerProfileRequest.getCity());
		if (updateWorkerProfileRequest.getDescription() != null)
			profile.setDescription(updateWorkerProfileRequest.getDescription());
		if (updateWorkerProfileRequest.getImageUrl() != null)
			profile.setImageUrl(updateWorkerProfileRequest.getImageUrl());
		profile.setUpdatedAt(new Date());

		WorkerProfile savedProfile = workerProfileRepository.save(profile);

        return getUpdateWorkerProfileResponse(savedProfile);
	}

	@Nonnull
	private static UpdateWorkerProfileResponse getUpdateWorkerProfileResponse(WorkerProfile savedProfile) {
		UpdateWorkerProfileResponse updateWorkerProfileResponse = new UpdateWorkerProfileResponse();

		updateWorkerProfileResponse.setAddress(savedProfile.getAddress());
		updateWorkerProfileResponse.setCity(savedProfile.getCity());
		updateWorkerProfileResponse.setCreatedAt(savedProfile.getCreatedAt());
		updateWorkerProfileResponse.setDescription(savedProfile.getDescription());
		updateWorkerProfileResponse.setExperienceYears(savedProfile.getExperienceYears());
		updateWorkerProfileResponse.setFullName(savedProfile.getFullName());
		updateWorkerProfileResponse.setHourlyRate(savedProfile.getHourlyRate());
		updateWorkerProfileResponse.setImageUrl(savedProfile.getImageUrl());
		updateWorkerProfileResponse.setOccupation(savedProfile.getOccupation());
		updateWorkerProfileResponse.setPhone(savedProfile.getPhone());
		updateWorkerProfileResponse.setSkills(savedProfile.getSkills());
		updateWorkerProfileResponse.setUpdatedAt(savedProfile.getUpdatedAt());
		updateWorkerProfileResponse.setUser(savedProfile.getUser());
		return updateWorkerProfileResponse;
	}

	public Optional<WorkerProfile> getByUserEmail(String userEmail) {
		return userRepository.findByEmail(userEmail).flatMap(workerProfileRepository::findByUser);
	}

	public List<WorkerProfile> searchWorkers(String city, String skill, Integer minExp, Integer maxRate, Double minRating, String occupation) {
		return workerProfileRepository.searchWorkers(city, skill, minExp, maxRate, minRating, occupation);
	}
}
