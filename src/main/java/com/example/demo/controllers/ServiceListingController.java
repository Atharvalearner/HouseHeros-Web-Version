package com.example.demo.controllers;
import com.example.demo.Entities.ServiceListing;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.*;
import java.util.*;

@RestController
@RequestMapping("/api/services")
public class ServiceListingController {

    @Autowired
    private ServiceListingService serviceListingService;

    @PostMapping("/worker/{workerId}")
    public ResponseEntity<ServiceListing> createServiceByWorker(@PathVariable Long workerId, @RequestBody ServiceListing listing) {
        return ResponseEntity.ok(serviceListingService.createListing(workerId, listing));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ServiceListing> updateService(@PathVariable Long id, @RequestBody ServiceListing payload) {
        ServiceListing updated = serviceListingService.updateListing(id, payload);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("")
    public List<ServiceListing> getAllServices() {
        return serviceListingService.getAllServices();
    }

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<ServiceListing>> getByWorker(@PathVariable Long workerId) {
        return ResponseEntity.ok(serviceListingService.getServicesByWorker(workerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        serviceListingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }
}

