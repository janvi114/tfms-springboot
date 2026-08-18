package com.tfms.service;

import com.tfms.model.Maintenance;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MaintenanceService {
    
    // Create or update maintenance record
    Maintenance saveMaintenanceRecord(Maintenance maintenance);
    
    // Get maintenance record by ID
    Optional<Maintenance> getMaintenanceRecordById(Long id);
    
    // Get all maintenance records
    List<Maintenance> getAllMaintenanceRecords();
    
    // Get maintenance records by vehicle ID
    List<Maintenance> getMaintenanceRecordsByVehicleId(Long vehicleId);
    
    // Get maintenance records by status
    List<Maintenance> getMaintenanceRecordsByStatus(String status);
    
    // Get upcoming maintenance
    List<Maintenance> getUpcomingMaintenance();
    
    // Get overdue maintenance
    List<Maintenance> getOverdueMaintenance();
    
    // Get maintenance between dates
    List<Maintenance> getMaintenanceBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    
    // Delete maintenance record
    void deleteMaintenanceRecord(Long id);
    
    // Update maintenance status
    Maintenance updateMaintenanceStatus(Long id, String status);
    
    // Count maintenance by status
    Long countMaintenanceByStatus(String status);
    
    // Get pending maintenance for a vehicle
    List<Maintenance> getPendingMaintenanceByVehicle(Long vehicleId);
}