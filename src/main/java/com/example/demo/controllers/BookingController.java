package com.example.demo.controllers;

import com.example.demo.Entities.Booking;
import com.example.demo.models.*;
import com.example.demo.services.BookingService;
import com.example.demo.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PostMapping("/{serviceId}")
	public Map<String, Object> createBookingForService(@RequestBody BookingServiceRequest bookingServiceRequest) throws Exception {
		String email = SecurityUtil.getCurrentUserEmail();
		return bookingService.createBookingForService(email, bookingServiceRequest.getServiceId(), bookingServiceRequest.getScheduledDate());
	}

	@GetMapping("/user")
	public Map<String, Object> getUserBookings() throws Exception {
		String email = SecurityUtil.getCurrentUserEmail();
		return bookingService.getUserBookings(email);
	}

	@GetMapping("/worker/{workerId}")
	public Map<String, Object> getWorkerBookings(GetWorkerBookingRequest getWorkerBookingRequest) throws Exception {
		return bookingService.getWorkerBookings(getWorkerBookingRequest);
	}

	@PutMapping("/{bookingId}/status")
	public boolean updateStatus(@RequestBody UpdateBookingStatusRequest updateBookingStatusRequest) throws Exception {
		bookingService.updateStatus(updateBookingStatusRequest);
		return true;
	}
	
	@PutMapping("/{id}")
    public boolean updateBooking(@RequestBody UpdateBookingForServiceRequest updateBookingForServiceRequest) throws Exception {
        bookingService.updateBooking(updateBookingForServiceRequest);
		return true;
    }
}
