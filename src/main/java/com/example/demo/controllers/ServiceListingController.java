package com.example.demo.controllers;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.*;
import com.example.demo.services.*;
import java.util.*;

@RestController
@RequestMapping("/api/services")
public class ServiceListingController {

    @Autowired
    private ServiceListingService service;

    @PostMapping("/worker/{workerId}")
    public ResponseEntity<ServiceListing> createService(@PathVariable Long workerId, @RequestBody ServiceListing listing) {
        return ResponseEntity.ok(service.createListing(workerId, listing));
    }

    @GetMapping
    public ResponseEntity<List<ServiceListing>> getAllServices() {
        return ResponseEntity.ok(service.getAllServices());
    }

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<ServiceListing>> getByWorker(@PathVariable Long workerId) {
        return ResponseEntity.ok(service.getServicesByWorker(workerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        service.deleteListing(id);
        return ResponseEntity.noContent().build();
    }
}

