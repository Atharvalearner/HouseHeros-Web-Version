package com.example.demo.controllers;
import com.example.demo.Entities.ServiceListing;
import com.example.demo.models.CreateServiceRequest;
import com.example.demo.models.UpdateServiceListingRequest;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.*;
import java.util.*;

@RestController
@RequestMapping("/api/services")
public class ServiceListingController {
    @Autowired
    private ServiceListingService serviceListingService;

    @PostMapping("/create")
    public String createService(@RequestBody CreateServiceRequest createServiceRequest) throws Exception {
        serviceListingService.createListing(createServiceRequest);
        return "Service created successfully";
    }
    
    @PutMapping("/update")
    public String updateService(@RequestBody UpdateServiceListingRequest updateServiceListingRequest) throws Exception {
        serviceListingService.updateListing(updateServiceListingRequest);
        return "Service updated successfully";
    }

    @GetMapping("/")
    public List<ServiceListing> getAllServices() throws Exception {
        return serviceListingService.getAllServices();
    }

    @GetMapping("/worker")
    public List<ServiceListing> getServiceByWorkerId(long workerId) throws Exception {
        return serviceListingService.getServiceByWorkerId(workerId);
    }

    @DeleteMapping("/worker")
    public String deleteService(long serviceId) throws Exception {
        serviceListingService.deleteService(serviceId);
        return "Service deleted successfully";
    }
}