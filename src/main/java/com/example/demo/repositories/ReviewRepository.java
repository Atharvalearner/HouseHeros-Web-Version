package com.example.demo.repositories;

import com.example.demo.models.Review;
import com.example.demo.models.WorkerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByWorkerProfile(WorkerProfile workerProfile);
}
