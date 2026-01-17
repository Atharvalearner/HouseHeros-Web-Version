package com.example.demo.models;

import com.example.demo.utils.Required;

import java.util.Date;

public class CreateBookingForServiceRequest {
    @Required
    private long serviceId;
    @Required
    private Date scheduleDate = new Date();

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
}