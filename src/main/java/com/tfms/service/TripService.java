package com.tfms.service;

import com.tfms.model.Trip;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TripService {
    
    // Create or update trip
    Trip saveTrip(Trip trip);
    
    // Get trip by ID
    Optional<Trip> getTripById(Long id);
    
    // Get all trips
    List<Trip> getAllTrips();
    
    // Get trips by vehicle ID
    List<Trip> getTripsByVehicleId(Long vehicleId);
    
    // Get trips by driver ID
    List<Trip> getTripsByDriverId(Long driverId);
    
    // Get trips between dates
    List<Trip> getTripsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    
    // Get ongoing trips
    List<Trip> getOngoingTrips();
    
    // Get completed trips
    List<Trip> getCompletedTrips();
    
    // Delete trip
    void deleteTrip(Long id);
    
    // Complete trip (set end time)
    Trip completeTrip(Long id);
    
    // Count trips by vehicle
    Long countTripsByVehicle(Long vehicleId);
}