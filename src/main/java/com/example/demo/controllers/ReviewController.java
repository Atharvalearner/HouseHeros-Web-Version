package com.example.demo.controllers;

import com.example.demo.models.Review;
import com.example.demo.models.ReviewResponse;
import com.example.demo.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // User adds a review after service completion
    @PostMapping
    public ResponseEntity<?> addReview(Authentication auth, @RequestBody Map<String, Object> request) {
        String email = auth.getName();
        Long bookingId = Long.valueOf(request.get("bookingId").toString());
        int rating = (int) request.get("rating");
        String comment = request.get("comment").toString();

        Review review = reviewService.addReview(email, bookingId, rating, comment);
        return ResponseEntity.ok(review);
    }

    // Public endpoint: get reviews for a specific worker
    @GetMapping("/worker/{workerId}")
   	public ResponseEntity<List<ReviewResponse>> getReviews(@PathVariable Long workerId){
        return ResponseEntity.ok(reviewService.getReviewsForWorker(workerId));
        
    }
}
