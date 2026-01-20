package com.example.demo.models;

import com.example.demo.constants.WorkerCategory;
import com.example.demo.utils.Required;

public class CreateServiceRequest {
    @Required
    private long workerId;

    @Required
    private String title;

    private String description;
    private double price;
    private boolean isPublic = true;

    @Required
    private WorkerCategory serviceCategory;

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
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

    public WorkerCategory getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(WorkerCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }
}
