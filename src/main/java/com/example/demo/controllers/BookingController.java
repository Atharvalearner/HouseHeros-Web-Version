package com.example.demo.controllers;

import com.example.demo.models.BookingServiceRequest;
import com.example.demo.models.GetWorkerBookingRequest;
import com.example.demo.models.UpdateBookingForServiceRequest;
import com.example.demo.models.UpdateBookingStatusRequest;
import com.example.demo.services.BookingService;
import com.example.demo.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PostMapping("/create")
	public Map<String, Object> createBookingForService(@RequestBody BookingServiceRequest bookingServiceRequest) throws Exception {
		String email = SecurityUtil.getCurrentUserEmail();
		return bookingService.createBookingForService(email, bookingServiceRequest.getServiceId(), bookingServiceRequest.getScheduledDate());
	}

	@GetMapping("/")
	public Map<String, Object> getUserBookings() throws Exception {
		String email = SecurityUtil.getCurrentUserEmail();
		return bookingService.getUserBookings(email);
	}

	@GetMapping("/worker")
	public Map<String, Object> getWorkerBookings(GetWorkerBookingRequest getWorkerBookingRequest) throws Exception {
		return bookingService.getWorkerBookings(getWorkerBookingRequest);
	}

	@PutMapping("/status/update")
	public boolean updateStatus(@RequestBody UpdateBookingStatusRequest updateBookingStatusRequest) throws Exception {
		bookingService.updateStatus(updateBookingStatusRequest);
		return true;
	}
	
	@PutMapping("/update")
    public boolean updateBooking(@RequestBody UpdateBookingForServiceRequest updateBookingForServiceRequest) throws Exception {
        bookingService.updateBooking(updateBookingForServiceRequest);
		return true;
    }
}
