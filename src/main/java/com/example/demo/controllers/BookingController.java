package com.example.demo.controllers;

import com.example.demo.models.Booking;
import com.example.demo.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping("/{serviceId}")
	public ResponseEntity<Booking> createBooking(Authentication auth, @PathVariable Long serviceId, @RequestParam("date") String date) {
		User userDetails = (User) auth.getPrincipal();
		String email = userDetails.getUsername();
		LocalDateTime scheduledDate = LocalDateTime.parse(date);
		return ResponseEntity.ok(bookingService.createBooking(email, serviceId, scheduledDate));
	}

	@GetMapping("/user")
	public ResponseEntity<List<Booking>> getUserBookings(Authentication auth) {
		User userDetails = (User) auth.getPrincipal();
		String email = userDetails.getUsername();
		return ResponseEntity.ok(bookingService.getUserBookings(email));
	}

	@GetMapping("/worker/{workerId}")
	public ResponseEntity<List<Booking>> getWorkerBookings(@PathVariable Long workerId) {
		return ResponseEntity.ok(bookingService.getWorkerBookings(workerId));
	}

	@PutMapping("/{bookingId}/status")
	public ResponseEntity<Booking> updateStatus(@PathVariable Long bookingId, @RequestParam String status) {
		return ResponseEntity.ok(bookingService.updateStatus(bookingId, status));
	}
}
