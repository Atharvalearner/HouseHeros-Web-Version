package com.example.demo.models;

import com.example.demo.constants.WorkerCategory;
import com.example.demo.utils.Required;

public class UpdateServiceListingRequest {
    @Required
    private long serviceId;

    @Required
    private long workerId;

    @Required
    private String title;
    private String description;
    private double price;
    private boolean isPublic = true;

    @Required
    private WorkerCategory workerCategory;

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public WorkerCategory getWorkerCategory() {
        return workerCategory;
    }

    public void setWorkerCategory(WorkerCategory workerCategory) {
        this.workerCategory = workerCategory;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
