package com.example.demo.repositories;

import com.example.demo.Entities.Booking;
import com.example.demo.Entities.User;
import com.example.demo.Entities.WorkerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    List<Booking> findByService_Worker(WorkerProfile worker);
}