package com.tfms.repository;

import com.tfms.model.Maintenance;
import com.tfms.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    
    // Find maintenance records by vehicle
    List<Maintenance> findByVehicle(Vehicle vehicle);
    
    // Find maintenance records by vehicle ID
    List<Maintenance> findByVehicleVehicleId(Long vehicleId);
    
    // Find maintenance records by status
    List<Maintenance> findByStatus(String status);
    
    // Find upcoming maintenance (scheduled in future)
    @Query("SELECT m FROM Maintenance m WHERE m.scheduledDate > ?1 AND m.status = 'SCHEDULED' ORDER BY m.scheduledDate ASC")
    List<Maintenance> findUpcomingMaintenance(LocalDateTime currentDate);
    
    // Find overdue maintenance
    @Query("SELECT m FROM Maintenance m WHERE m.scheduledDate < ?1 AND m.status = 'SCHEDULED' ORDER BY m.scheduledDate ASC")
    List<Maintenance> findOverdueMaintenance(LocalDateTime currentDate);
    
    // Find maintenance within date range
    @Query("SELECT m FROM Maintenance m WHERE m.scheduledDate BETWEEN ?1 AND ?2")
    List<Maintenance> findMaintenanceBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    
    // Count maintenance by status
    Long countByStatus(String status);
    
    // Get all maintenance records ordered by scheduled date
    List<Maintenance> findAllByOrderByScheduledDateDesc();
    
    // Find pending maintenance for a vehicle
    @Query("SELECT m FROM Maintenance m WHERE m.vehicle.vehicleId = ?1 AND m.status = 'SCHEDULED'")
    List<Maintenance> findPendingMaintenanceByVehicle(Long vehicleId);
}