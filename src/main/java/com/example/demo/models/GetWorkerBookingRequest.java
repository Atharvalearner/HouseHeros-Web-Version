package com.example.demo.models;

import com.example.demo.utils.Required;

public class GetWorkerBookingRequest {
    @Required
    private Long workerId;

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }
}
