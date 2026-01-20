package com.example.demo.services;

import com.example.demo.Entities.Booking;
import com.example.demo.Entities.ServiceListing;
import com.example.demo.Entities.User;
import com.example.demo.Entities.WorkerProfile;
import com.example.demo.constants.BookingStatus;
import com.example.demo.models.CreateBookingForServiceResponse;

import com.example.demo.models.GetWorkerBookingRequest;
import com.example.demo.models.UpdateBookingForServiceRequest;
import com.example.demo.models.UpdateBookingStatusRequest;
import com.example.demo.repositories.BookingRepository;
import com.example.demo.repositories.ServiceListingRepository;
import com.example.demo.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Service
public class BookingService {
	public final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Autowired
	private BookingRepository bookingRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ServiceListingRepository serviceRepo;

	@Transactional
	public Map<String, Object> createBookingForService(String userEmail, Long serviceId, Date date) throws  Exception {
		User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
		ServiceListing service = serviceRepo.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));

		Booking booking = new Booking();
		booking.setUser(user);
		booking.setService(service);
		booking.setScheduledDate(date);
		booking.setStatus(BookingStatus.PENDING);
		booking.setBookingDate(new Date());
		booking.setCreatedDate(new Date());
		booking.setUpdatedDate(new Date());
		bookingRepo.save(booking);

		CreateBookingForServiceResponse response = OBJECT_MAPPER.convertValue(booking, CreateBookingForServiceResponse.class);
		return Collections.singletonMap("createBookingForService", response);
	}

	@Transactional
    public void updateBooking(UpdateBookingForServiceRequest updateBookingForServiceRequest) throws  Exception {
        Booking existingBooking = bookingRepo.findById(updateBookingForServiceRequest.getBookingId()).orElseThrow(() -> new RuntimeException("Booking not found"));

        if (updateBookingForServiceRequest.getBookingDate() != null) {
			existingBooking.setBookingDate(updateBookingForServiceRequest.getBookingDate());
		}
        if (updateBookingForServiceRequest.getStatus() != null) {
			existingBooking.setStatus(updateBookingForServiceRequest.getStatus());
		}
		if (updateBookingForServiceRequest.getScheduledDate() != null) {
			existingBooking.setScheduledDate(updateBookingForServiceRequest.getScheduledDate());
		}
        bookingRepo.save(existingBooking);
    }

	@Transactional(readOnly = true)
	public Map<String, Object> getUserBookings(String userEmail) throws Exception {
		User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
		return Collections.singletonMap("userBookings", bookingRepo.findByUser(user));
	}

	public Map<String, Object> getWorkerBookings(GetWorkerBookingRequest getWorkerBookingRequest) throws Exception {
		WorkerProfile worker = new WorkerProfile();
		worker.setId(getWorkerBookingRequest.getWorkerId());
		return Collections.singletonMap("workerBookings", bookingRepo.findByService_Worker(worker));
	}

	public void updateStatus(UpdateBookingStatusRequest updateBookingStatusRequest) throws Exception {
		Booking booking = bookingRepo.findById(updateBookingStatusRequest.getBookingId()).orElseThrow(() -> new RuntimeException("Booking not found"));
		booking.setStatus(updateBookingStatusRequest.getBookingStatus());
		bookingRepo.save(booking);
	}
}
