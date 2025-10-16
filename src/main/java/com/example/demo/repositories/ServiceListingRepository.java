package com.example.demo.repositories;
import java.util.*;
import com.example.demo.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceListingRepository extends JpaRepository<ServiceListing, Long>{
	List<ServiceListing> findByWorkerId(Long workerId);
}
