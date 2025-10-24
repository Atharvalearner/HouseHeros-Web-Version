package com.example.demo.services;

import com.example.demo.models.*;
import com.example.demo.repositories.*;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
	private final BookingRepository bookingRepo;
	private final UserRepository userRepo;
	private final ServiceListingRepository serviceRepo;

	public BookingService(BookingRepository bookingRepo, UserRepository userRepo, ServiceListingRepository serviceRepo) {
		this.bookingRepo = bookingRepo;
		this.userRepo = userRepo;
		this.serviceRepo = serviceRepo;
	}

	public Booking createBooking(String userEmail, Long serviceId, LocalDateTime date) {
		User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
		ServiceListing service = serviceRepo.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));

		Booking booking = new Booking();
		booking.setUser(user);
		booking.setService(service);
		booking.setScheduledDate(date);
		booking.setStatus("PENDING");

		return bookingRepo.save(booking);
	}

	@Transactional
    public Booking updateBooking(Long bookingId, Booking payload) {
        Booking existing = bookingRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

        if (payload.getBookingDate() != null) existing.setBookingDate(payload.getBookingDate());
        if (payload.getStatus() != null) existing.setStatus(payload.getStatus());
        if (payload.getService() != null) {
            ServiceListing service = serviceRepo.findById(payload.getService().getId()).orElseThrow(() -> new RuntimeException("Service not found"));
            existing.setService(service);
        }
        return bookingRepo.save(existing);
    }
	
	public List<Booking> getUserBookings(String userEmail) {
		User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
		return bookingRepo.findByUser(user);
	}

	public List<Booking> getWorkerBookings(Long workerId) {
		WorkerProfile worker = new WorkerProfile();
		worker.setId(workerId);
		return bookingRepo.findByService_Worker(worker);
	}

	public Booking updateStatus(Long bookingId, String status) {
		Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
		booking.setStatus(status);
		return bookingRepo.save(booking);
	}
}
