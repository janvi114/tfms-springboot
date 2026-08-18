package com.tfms.service.impl;

import com.tfms.model.Trip;
import com.tfms.repository.TripRepository;
import com.tfms.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TripServiceImpl implements TripService {
    
    @Autowired
    private TripRepository tripRepository;
    
    @Override
    public Trip saveTrip(Trip trip) {
        return tripRepository.save(trip);
    }
    
    @Override
    public Optional<Trip> getTripById(Long id) {
        return tripRepository.findById(id);
    }
    
    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAllByOrderByStartTimeDesc();
    }
    
    @Override
    public List<Trip> getTripsByVehicleId(Long vehicleId) {
        return tripRepository.findByVehicleVehicleId(vehicleId);
    }
    
    @Override
    public List<Trip> getTripsByDriverId(Long driverId) {
        return tripRepository.findByDriverId(driverId);
    }
    
    @Override
    public List<Trip> getTripsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return tripRepository.findTripsBetweenDates(startDate, endDate);
    }
    
    @Override
    public List<Trip> getOngoingTrips() {
        return tripRepository.findOngoingTrips();
    }
    
    @Override
    public List<Trip> getCompletedTrips() {
        return tripRepository.findCompletedTrips();
    }
    
    @Override
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }
    
    @Override
    public Trip completeTrip(Long id) {
        Optional<Trip> tripOpt = tripRepository.findById(id);
        if (tripOpt.isPresent()) {
            Trip trip = tripOpt.get();
            trip.setEndTime(LocalDateTime.now());
            return tripRepository.save(trip);
        }
        return null;
    }
    
    @Override
    public Long countTripsByVehicle(Long vehicleId) {
        return tripRepository.countByVehicleVehicleId(vehicleId);
    }
}