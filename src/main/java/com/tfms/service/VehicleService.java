package com.tfms.service;

import com.tfms.model.Vehicle;
import java.util.List;
import java.util.Optional;

public interface VehicleService {
    
    // Create or update vehicle
    Vehicle saveVehicle(Vehicle vehicle);
    
    // Get vehicle by ID
    Optional<Vehicle> getVehicleById(Long id);
    
    // Get all vehicles
    List<Vehicle> getAllVehicles();
    
    // Get vehicle by registration number
    Optional<Vehicle> getVehicleByRegistrationNumber(String registrationNumber);
    
    // Get vehicles by status
    List<Vehicle> getVehiclesByStatus(String status);
    
    // Get active vehicles
    List<Vehicle> getActiveVehicles();
    
    // Delete vehicle
    void deleteVehicle(Long id);
    
    // Check if registration number exists
    boolean existsByRegistrationNumber(String registrationNumber);
    
    // Count vehicles by status
    Long countVehiclesByStatus(String status);
    
    // Update vehicle status
    Vehicle updateVehicleStatus(Long id, String status);
}