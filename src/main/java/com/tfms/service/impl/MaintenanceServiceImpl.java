package com.tfms.service.impl;

import com.tfms.model.Maintenance;
import com.tfms.repository.MaintenanceRepository;
import com.tfms.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService {
    
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    
    @Override
    public Maintenance saveMaintenanceRecord(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }
    
    @Override
    public Optional<Maintenance> getMaintenanceRecordById(Long id) {
        return maintenanceRepository.findById(id);
    }
    
    @Override
    public List<Maintenance> getAllMaintenanceRecords() {
        return maintenanceRepository.findAllByOrderByScheduledDateDesc();
    }
    
    @Override
    public List<Maintenance> getMaintenanceRecordsByVehicleId(Long vehicleId) {
        return maintenanceRepository.findByVehicleVehicleId(vehicleId);
    }
    
    @Override
    public List<Maintenance> getMaintenanceRecordsByStatus(String status) {
        return maintenanceRepository.findByStatus(status);
    }
    
    @Override
    public List<Maintenance> getUpcomingMaintenance() {
        return maintenanceRepository.findUpcomingMaintenance(LocalDateTime.now());
    }
    
    @Override
    public List<Maintenance> getOverdueMaintenance() {
        return maintenanceRepository.findOverdueMaintenance(LocalDateTime.now());
    }
    
    @Override
    public List<Maintenance> getMaintenanceBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return maintenanceRepository.findMaintenanceBetweenDates(startDate, endDate);
    }
    
    @Override
    public void deleteMaintenanceRecord(Long id) {
        maintenanceRepository.deleteById(id);
    }
    
    @Override
    public Maintenance updateMaintenanceStatus(Long id, String status) {
        Optional<Maintenance> maintenanceOpt = maintenanceRepository.findById(id);
        if (maintenanceOpt.isPresent()) {
            Maintenance maintenance = maintenanceOpt.get();
            maintenance.setStatus(status);
            return maintenanceRepository.save(maintenance);
        }
        return null;
    }
    
    @Override
    public Long countMaintenanceByStatus(String status) {
        return maintenanceRepository.countByStatus(status);
    }
    
    @Override
    public List<Maintenance> getPendingMaintenanceByVehicle(Long vehicleId) {
        return maintenanceRepository.findPendingMaintenanceByVehicle(vehicleId);
    }
}