package com.example.demo.models;

import com.example.demo.utils.Required;

import java.util.Date;

public class BookingServiceRequest {
    @Required
    private long serviceId;
    @Required
    private Date scheduledDate;

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}
