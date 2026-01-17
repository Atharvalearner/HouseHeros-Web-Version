package com.example.demo.models;

import com.example.demo.Entities.ServiceListing;
import com.example.demo.Entities.User;
import com.example.demo.constants.BookingStatus;

import java.util.Date;

public class CreateBookingForServiceResponse {
    private long bookingId;
    private BookingStatus status = BookingStatus.PENDING;
    private Date bookingDate;
    private Date scheduledDate; // when user wants service
    private User user; // who booked
    private ServiceListing service;

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
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
