package com.example.demo.repositories;

import com.example.demo.Entities.Review;
import com.example.demo.Entities.WorkerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByWorkerProfile(WorkerProfile workerProfile);
}
