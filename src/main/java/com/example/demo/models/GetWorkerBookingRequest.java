package com.example.demo.models;

import com.example.demo.utils.Required;

public class GetWorkerBookingRequest {
    @Required
    private long workerId;

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }
}
