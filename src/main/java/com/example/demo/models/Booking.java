package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status = "PENDING"; 
    private LocalDateTime bookingDate = LocalDateTime.now();
    private LocalDateTime scheduledDate; // when user wants service

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // who booked

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceListing service;  // which service booked

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}

	public LocalDateTime getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(LocalDateTime scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ServiceListing getService() {
		return service;
	}

	public void setService(ServiceListing service) {
		this.service = service;
	}
    
    
}
