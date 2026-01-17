package com.example.demo.services;

import com.example.demo.Entities.ServiceListing;
import com.example.demo.Entities.WorkerProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import com.example.demo.repositories.*;

import jakarta.transaction.Transactional;

@Service
public class ServiceListingService {
	@Autowired
	private ServiceListingRepository serviceListingRepository;

	@Autowired
	private WorkerProfileRepository workerProfileRepository;

	public ServiceListing createListing(Long workerId, ServiceListing listing) {
		WorkerProfile worker = workerProfileRepository.findById(workerId).orElseThrow(() -> new RuntimeException("Worker not found"));
		listing.setWorker(worker);
		return serviceListingRepository.save(listing);
	}

	@Transactional
	public ServiceListing updateListing(Long listingId, ServiceListing payload) {
	    ServiceListing existing = serviceListingRepository.findById(listingId).orElseThrow(() -> new RuntimeException("Service listing not found"));

	    if (payload.getTitle() != null) existing.setTitle(payload.getTitle());
	    if (payload.getDescription() != null) existing.setDescription(payload.getDescription());
	    if (payload.getCategory() != null) existing.setCategory(payload.getCategory());
	    if (payload.getPrice() > 0) existing.setPrice(payload.getPrice());
	    return serviceListingRepository.save(existing);
	}

	public List<ServiceListing> getAllServices() {
		return serviceListingRepository.findAll();
	}

	public List<ServiceListing> getServicesByWorker(Long workerId) {
		return serviceListingRepository.findByWorkerId(workerId);
	}

	public void deleteListing(Long id) {
		serviceListingRepository.deleteById(id);
	}
}
