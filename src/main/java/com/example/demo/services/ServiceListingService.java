package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import com.example.demo.models.*;
import com.example.demo.repositories.*;

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
