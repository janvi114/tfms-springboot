package com.tfms.repository;

import com.tfms.model.Fuel;
import com.tfms.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Long> {
    
    // Find fuel records by vehicle
    List<Fuel> findByVehicle(Vehicle vehicle);
    
    // Find fuel records by vehicle ID
    List<Fuel> findByVehicleVehicleId(Long vehicleId);
    
    // Find fuel records within date range
    @Query("SELECT f FROM Fuel f WHERE f.date BETWEEN ?1 AND ?2")
    List<Fuel> findFuelRecordsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    
    // Calculate total fuel cost for a vehicle
    @Query("SELECT SUM(f.cost) FROM Fuel f WHERE f.vehicle.vehicleId = ?1")
    BigDecimal calculateTotalCostByVehicle(Long vehicleId);
    
    // Calculate total fuel quantity for a vehicle
    @Query("SELECT SUM(f.fuelQuantity) FROM Fuel f WHERE f.vehicle.vehicleId = ?1")
    BigDecimal calculateTotalQuantityByVehicle(Long vehicleId);
    
    // Get all fuel records ordered by date
    List<Fuel> findAllByOrderByDateDesc();
    
    // Find recent fuel records for a vehicle (last 10)
    @Query("SELECT f FROM Fuel f WHERE f.vehicle.vehicleId = ?1 ORDER BY f.date DESC")
    List<Fuel> findRecentFuelRecordsByVehicle(Long vehicleId);
}