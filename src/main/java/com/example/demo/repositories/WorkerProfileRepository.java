package com.example.demo.repositories;

import com.example.demo.models.WorkerProfile;
import com.example.demo.models.User;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

public interface WorkerProfileRepository extends JpaRepository<WorkerProfile, Long> {
    Optional<WorkerProfile> findByUser(User user);
    List<WorkerProfile> findByCityContainingIgnoreCaseAndSkillsContainingIgnoreCase(String city, String skills);
    List<WorkerProfile> findByExperienceYearsGreaterThanEqual(int experienceYears);
    List<WorkerProfile> findByHourlyRateBetween(int minRate, int maxRate);	 	
    List<WorkerProfile> findByAverageRatingGreaterThanEqual(double rating);  
    List<WorkerProfile> findByOccupation(String occupation);
    
    @Query("""
            SELECT w FROM WorkerProfile w
            WHERE (:city IS NULL OR LOWER(w.city) LIKE LOWER(CONCAT('%', :city, '%')))
            AND (:skills IS NULL OR LOWER(w.skills) LIKE LOWER(CONCAT('%', :skills, '%')))
            AND (:minExp IS NULL OR w.experienceYears >= :minExp)
            AND (:maxRate IS NULL OR w.hourlyRate <= :maxRate)
            AND (:minRating IS NULL OR w.averageRating >= :minRating)
            AND (:occupation IS NULL OR LOWER(w.occupation) LIKE LOWER(CONCAT('%', :occupation, '%')))
        """)

        List<WorkerProfile> searchWorkers(@Param("city") String city,
                                          @Param("skills") String skills,
                                          @Param("minExp") Integer minExp,
                                          @Param("maxRate") Integer maxRate,
                                          @Param("minRating") Double minRating,
        								  @Param("occupation") String occupation);
}
