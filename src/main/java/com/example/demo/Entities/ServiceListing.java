package com.example.demo.Entities;

import com.example.demo.constants.WorkerCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "service")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ServiceListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotBlank(message = "Service Title is required")
    private String title;
    private String description;
    private double price;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
    private WorkerCategory category;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private WorkerProfile worker;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public WorkerCategory getCategory() {
		return category;
	}

	public void setCategory(WorkerCategory category) {
		this.category = category;
	}

	public WorkerProfile getWorker() {
		return worker;
	}

	public void setWorker(WorkerProfile worker) {
		this.worker = worker;
	}

}
