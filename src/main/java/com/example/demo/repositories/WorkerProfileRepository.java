package com.example.demo.repositories;

import com.example.demo.models.WorkerProfile;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerProfileRepository extends JpaRepository<WorkerProfile, Long> {
    Optional<WorkerProfile> findByUser(User user);
    List<WorkerProfile> findByIsApprovedTrue();    // for public listing
}
