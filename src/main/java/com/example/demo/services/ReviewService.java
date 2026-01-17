package com.example.demo.services;

import com.example.demo.Entities.Booking;
import com.example.demo.Entities.Review;
import com.example.demo.Entities.User;
import com.example.demo.Entities.WorkerProfile;
import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final WorkerProfileRepository workerProfileRepository;
	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;

	public ReviewService(ReviewRepository reviewRepository, WorkerProfileRepository workerProfileRepository,
			BookingRepository bookingRepository, UserRepository userRepository) {
		this.reviewRepository = reviewRepository;
		this.workerProfileRepository = workerProfileRepository;
		this.bookingRepository = bookingRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public Review addReview(String userEmail, Long bookingId, int rating, String comment) {
		User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
		Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

		if (!booking.getUser().getEmail().equals(userEmail)) {
			throw new RuntimeException("You can review only your own bookings");
		}

		WorkerProfile workerProfile = booking.getService().getWorker();

		Review review = new Review();
		review.setUser(user);
		review.setWorkerProfile(workerProfile);
		review.setBooking(booking);
		review.setRating(rating);
		review.setComment(comment);

		Review saved = reviewRepository.save(review);
		updateWorkerAverageRating(workerProfile);
		return saved;
	}

	@Transactional
	public Review updateReview(String userEmail, Long reviewId, Integer newRating, String newComment) {
	    Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));

	    // Check if logged-in user is owner of review
	    if (!review.getUser().getEmail().equals(userEmail)) {
	        throw new RuntimeException("You are not allowed to update this review");
	    }
	    if (newRating != null) review.setRating(newRating);
	    if (newComment != null && !newComment.trim().isEmpty()) review.setComment(newComment);

	    Review savedReview = reviewRepository.save(review);
	    updateWorkerAverageRating(review.getWorkerProfile());
	    return savedReview;
	}

	public List<ReviewResponse> getReviewsForWorker(Long workerId) {
		WorkerProfile worker = workerProfileRepository.findById(workerId).orElseThrow(() -> new RuntimeException("Worker not found"));
		List<Review> reviews = reviewRepository.findByWorkerProfile(worker);
		return reviews.stream().map(r -> new ReviewResponse(r.getId(), r.getRating(), r.getComment(), r.getCreatedAt(),
				r.getUser().getUsername(), r.getUser().getEmail())).toList();
	}

	@Transactional
	public void deleteReview(Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
		WorkerProfile worker = review.getWorkerProfile();
		reviewRepository.delete(review);
		updateWorkerAverageRating(worker);
	}

	@Transactional
	protected void updateWorkerAverageRating(WorkerProfile worker) {
		List<Review> reviews = reviewRepository.findByWorkerProfile(worker);
		double avg = reviews.isEmpty() ? 0.0 : reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
		worker.setAverageRating(avg);
		workerProfileRepository.save(worker);
	}
}
