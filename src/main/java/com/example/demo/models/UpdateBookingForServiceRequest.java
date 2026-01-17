package com.example.demo.models;

import com.example.demo.constants.BookingStatus;
import com.example.demo.utils.Required;

import java.util.Date;

public class UpdateBookingForServiceRequest {
    @Required
    private long bookingId;
    private BookingStatus status;
    private Date bookingDate = new Date();
    private Date scheduledDate; // when user wants service

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
}
