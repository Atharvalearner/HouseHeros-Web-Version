package com.example.demo.models;

import com.example.demo.constants.BookingStatus;
import com.example.demo.utils.Required;

public class UpdateBookingStatusRequest {
    @Required
    private long bookingId;
    @Required
    private BookingStatus bookingStatus;

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
