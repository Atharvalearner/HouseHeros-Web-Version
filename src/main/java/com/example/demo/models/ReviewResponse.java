package com.example.demo.models;

import java.time.Instant;

public class ReviewResponse {
	private Long id;
	private int rating;
	private String comment;
	private Instant createdAt;

	private String reviewerName;
	private String reviewerEmail;

	public ReviewResponse(Long id, int rating, String comment, Instant createdAt, String reviewerName,
			String reviewerEmail) {
		this.id = id;
		this.rating = rating;
		this.comment = comment;
		this.createdAt = createdAt;
		this.reviewerName = reviewerName;
		this.reviewerEmail = reviewerEmail;
	}

	public Long getId() {
		return id;
	}

	public int getRating() {
		return rating;
	}

	public String getComment() {
		return comment;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public String getReviewerName() {
		return reviewerName;
	}

	public String getReviewerEmail() {
		return reviewerEmail;
	}
}
