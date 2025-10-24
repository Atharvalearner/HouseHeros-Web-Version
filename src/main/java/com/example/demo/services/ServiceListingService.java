package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import com.example.demo.models.*;
import com.example.demo.repositories.*;

import jakarta.transaction.Transactional;

@Service
public class ServiceListingService {
	@Autowired
	private ServiceListingRepository repo;

	@Autowired
	private WorkerProfileRepository workerRepo;

	public ServiceListing createListing(Long workerId, ServiceListing listing) {
		WorkerProfile worker = workerRepo.findById(workerId).orElseThrow(() -> new RuntimeException("Worker not found"));
		listing.setWorker(worker);
		return repo.save(listing);
	}

	@Transactional
	public ServiceListing updateListing(Long listingId, ServiceListing payload) {
	    ServiceListing existing = repo.findById(listingId).orElseThrow(() -> new RuntimeException("Service listing not found"));

	    if (payload.getTitle() != null) existing.setTitle(payload.getTitle());
	    if (payload.getDescription() != null) existing.setDescription(payload.getDescription());
	    if (payload.getCategory() != null) existing.setCategory(payload.getCategory());
	    if (payload.getPrice() > 0) existing.setPrice(payload.getPrice());
	    return repo.save(existing);
	}

	public List<ServiceListing> getAllServices() {
		return repo.findAll();
	}

	public List<ServiceListing> getServicesByWorker(Long workerId) {
		return repo.findByWorkerId(workerId);
	}

	public void deleteListing(Long id) {
		repo.deleteById(id);
	}
}
