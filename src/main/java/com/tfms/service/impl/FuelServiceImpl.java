package com.tfms.service.impl;

import com.tfms.model.Fuel;
import com.tfms.repository.FuelRepository;
import com.tfms.service.FuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FuelServiceImpl implements FuelService {
    
    @Autowired
    private FuelRepository fuelRepository;
    
    @Override
    public Fuel saveFuelRecord(Fuel fuel) {
        if (fuel.getDate() == null) {
            fuel.setDate(LocalDateTime.now());
        }
        return fuelRepository.save(fuel);
    }
    
    @Override
    public Optional<Fuel> getFuelRecordById(Long id) {
        return fuelRepository.findById(id);
    }
    
    @Override
    public List<Fuel> getAllFuelRecords() {
        return fuelRepository.findAllByOrderByDateDesc();
    }
    
    @Override
    public List<Fuel> getFuelRecordsByVehicleId(Long vehicleId) {
        return fuelRepository.findByVehicleVehicleId(vehicleId);
    }
    
    @Override
    public List<Fuel> getFuelRecordsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return fuelRepository.findFuelRecordsBetweenDates(startDate, endDate);
    }
    
    @Override
    public BigDecimal calculateTotalCostByVehicle(Long vehicleId) {
        BigDecimal total = fuelRepository.calculateTotalCostByVehicle(vehicleId);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    @Override
    public BigDecimal calculateTotalQuantityByVehicle(Long vehicleId) {
        BigDecimal total = fuelRepository.calculateTotalQuantityByVehicle(vehicleId);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    @Override
    public void deleteFuelRecord(Long id) {
        fuelRepository.deleteById(id);
    }
    
    @Override
    public List<Fuel> getRecentFuelRecordsByVehicle(Long vehicleId) {
        return fuelRepository.findRecentFuelRecordsByVehicle(vehicleId);
    }
}