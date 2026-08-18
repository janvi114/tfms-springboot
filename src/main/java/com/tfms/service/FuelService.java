package com.tfms.service;

import com.tfms.model.Fuel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FuelService {
    
    // Create or update fuel record
    Fuel saveFuelRecord(Fuel fuel);
    
    // Get fuel record by ID
    Optional<Fuel> getFuelRecordById(Long id);
    
    // Get all fuel records
    List<Fuel> getAllFuelRecords();
    
    // Get fuel records by vehicle ID
    List<Fuel> getFuelRecordsByVehicleId(Long vehicleId);
    
    // Get fuel records between dates
    List<Fuel> getFuelRecordsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    
    // Calculate total fuel cost by vehicle
    BigDecimal calculateTotalCostByVehicle(Long vehicleId);
    
    // Calculate total fuel quantity by vehicle
    BigDecimal calculateTotalQuantityByVehicle(Long vehicleId);
    
    // Delete fuel record
    void deleteFuelRecord(Long id);
    
    // Get recent fuel records for a vehicle
    List<Fuel> getRecentFuelRecordsByVehicle(Long vehicleId);
}