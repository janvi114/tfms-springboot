package com.tfms.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfms.model.Vehicle;
import com.tfms.repository.VehicleRepository;
import com.tfms.service.VehicleService;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    
    @Autowired
    private VehicleRepository vehicleRepository;
    
    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        if (vehicle.getLastServicedDate() == null) {
            vehicle.setLastServicedDate(LocalDate.now());
        }
        return vehicleRepository.save(vehicle);
    }
    
    @Override
    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }
    
    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAllByOrderByRegistrationNumberAsc();
    }
    
    @Override
    public Optional<Vehicle> getVehicleByRegistrationNumber(String registrationNumber) {
        return vehicleRepository.findByRegistrationNumber(registrationNumber);
    }
    
    @Override
    public List<Vehicle> getVehiclesByStatus(String status) {
        return vehicleRepository.findByStatus(status);
    }
    
    @Override
    public List<Vehicle> getActiveVehicles() {
        return vehicleRepository.findAllActiveVehicles();
    }
    
    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByRegistrationNumber(String registrationNumber) {
        return vehicleRepository.existsByRegistrationNumber(registrationNumber);
    }
    
    @Override
    public Long countVehiclesByStatus(String status) {
        return vehicleRepository.countByStatus(status);
    }
    
    @Override
    public Vehicle updateVehicleStatus(Long id, String status) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);
        if (vehicleOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();
            vehicle.setStatus(status);
            return vehicleRepository.save(vehicle);
        }
        return null;
    }
}