package com.tfms.repository;

import com.tfms.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    // Find vehicle by registration number
    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
    
    // Find vehicles by status
    List<Vehicle> findByStatus(String status);
    
    // Find all vehicles ordered by registration number
    List<Vehicle> findAllByOrderByRegistrationNumberAsc();
    
    // Check if registration number exists
    boolean existsByRegistrationNumber(String registrationNumber);
    
    // Custom query to count vehicles by status
    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.status = ?1")
    Long countByStatus(String status);
    
    // Get all active vehicles
    @Query("SELECT v FROM Vehicle v WHERE v.status = 'ACTIVE'")
    List<Vehicle> findAllActiveVehicles();
}