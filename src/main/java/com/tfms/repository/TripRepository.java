package com.tfms.repository;

import com.tfms.model.Trip;
import com.tfms.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    
    // Find trips by vehicle
    List<Trip> findByVehicle(Vehicle vehicle);
    
    // Find trips by vehicle ID
    List<Trip> findByVehicleVehicleId(Long vehicleId);
    
    // Find trips by driver ID
    List<Trip> findByDriverId(Long driverId);
    
    // Find trips within date range
    @Query("SELECT t FROM Trip t WHERE t.startTime BETWEEN ?1 AND ?2")
    List<Trip> findTripsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find ongoing trips (end time is null)
    @Query("SELECT t FROM Trip t WHERE t.endTime IS NULL")
    List<Trip> findOngoingTrips();
    
    // Find completed trips
    @Query("SELECT t FROM Trip t WHERE t.endTime IS NOT NULL ORDER BY t.endTime DESC")
    List<Trip> findCompletedTrips();
    
    // Count trips by vehicle
    Long countByVehicleVehicleId(Long vehicleId);
    
    // Get all trips ordered by start time
    List<Trip> findAllByOrderByStartTimeDesc();
}