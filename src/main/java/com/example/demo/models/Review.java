package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Getter
@Setter
@Table(name = "reviews")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Rating is required")
	@Min(value = 1, message = "Rating cannot be less than 1")
	@Max(value = 5, message = "Rating cannot be greater than 5")
	private int rating;

	@NotBlank(message = "Comment is required")
	@Size(max = 1000, message = "Comment cannot exceed 1000 characters")
	private String comment;

	private Instant createdAt = Instant.now();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties({ "reviews", "workerProfile", "hibernateLazyInitializer", "handler" })
	private User user; // Reviewer (User who gave review)

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "worker_id")
	@JsonIgnoreProperties({ "reviews", "user", "hibernateLazyInitializer", "handler" })
	private WorkerProfile workerProfile; // Reviewed worker profile

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "booking_id")
	private Booking booking; // Optional booking reference

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WorkerProfile getWorkerProfile() {
		return workerProfile;
	}

	public void setWorkerProfile(WorkerProfile workerProfile) {
		this.workerProfile = workerProfile;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
}
