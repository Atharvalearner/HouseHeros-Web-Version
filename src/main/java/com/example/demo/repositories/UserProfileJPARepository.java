package com.example.demo.repositories;

import com.example.demo.Entities.UserProfile;
import com.example.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileJPARepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser(User user);
}