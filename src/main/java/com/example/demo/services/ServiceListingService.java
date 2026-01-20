package com.example.demo.services;

import com.example.demo.Entities.ServiceListing;
import com.example.demo.Entities.WorkerProfile;
import com.example.demo.models.CreateServiceRequest;
import com.example.demo.models.UpdateServiceListingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import com.example.demo.repositories.*;

import jakarta.transaction.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class ServiceListingService {
	@Autowired
	private ServiceListingRepository serviceListingRepository;

	@Autowired
	private WorkerProfileRepository workerProfileRepository;

	public void createListing(CreateServiceRequest createServiceRequest) throws Exception {
		WorkerProfile worker = workerProfileRepository.findById(createServiceRequest.getWorkerId()).orElseThrow(() -> new RuntimeException("Worker not found"));

		ServiceListing serviceListing = new ServiceListing();
		serviceListing.setTitle(createServiceRequest.getTitle());
		serviceListing.setDescription(createServiceRequest.getDescription());
		serviceListing.setPrice(createServiceRequest.getPrice());
		serviceListing.setCategory(createServiceRequest.getServiceCategory());
		serviceListing.setPublic(createServiceRequest.isPublic());
		serviceListing.setWorker(worker);
		serviceListingRepository.save(serviceListing);
	}

	@Transactional
	public void updateListing(UpdateServiceListingRequest updateServiceListingRequest) throws Exception {
	    if (ObjectUtils.isEmpty(updateServiceListingRequest.getWorkerId())){
            throw new Exception("Worker Id is mandatory to update the service");
		}
		if (ObjectUtils.isEmpty(updateServiceListingRequest.getServiceId())){
			throw new Exception("service Id is mandatory to update the service");
		}
		ServiceListing existing = serviceListingRepository.findById(updateServiceListingRequest.getServiceId()).orElseThrow(() -> new RuntimeException("Service for given Service Id not found"));
		if (!ObjectUtils.isEmpty(updateServiceListingRequest.getWorkerCategory())){
			existing.setCategory(updateServiceListingRequest.getWorkerCategory());
		}
		existing.setTitle(updateServiceListingRequest.getTitle());
		existing.setDescription(updateServiceListingRequest.getDescription());
		existing.setPrice(updateServiceListingRequest.getPrice());
		existing.setPublic(updateServiceListingRequest.isPublic());
		existing.setWorker(existing.getWorker());
	    serviceListingRepository.save(existing);
	}

	public List<ServiceListing> getAllServices() throws Exception {
		return serviceListingRepository.findAll();
	}

	public List<ServiceListing> getServiceByWorkerId(long workerId) throws Exception {
		return serviceListingRepository.findServiceByWorkerId(workerId);
	}

	public void deleteService(long serviceId) throws Exception {
		serviceListingRepository.deleteServiceByServiceListingId(serviceId);
	}
}
