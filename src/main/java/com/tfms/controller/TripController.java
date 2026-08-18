package com.tfms.controller;

import com.tfms.model.Trip;
import com.tfms.model.Vehicle;
import com.tfms.service.TripService;
import com.tfms.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/trips")
public class TripController {
    
    @Autowired
    private TripService tripService;
    
    @Autowired
    private VehicleService vehicleService;
    
    // Main page - list all trips (Single JSP approach)
    @GetMapping
    public String listTrips(Model model) {
        List<Trip> trips = tripService.getAllTrips();
        List<Vehicle> vehicles = vehicleService.getActiveVehicles();
        model.addAttribute("trips", trips);
        model.addAttribute("vehicles", vehicles);
        return "trip"; // Returns trip.jsp (single page)
    }
    
    // Add new trip
    @PostMapping("/add")
    public String addTrip(@RequestParam("vehicleId") Long vehicleId,
                         @RequestParam("driverId") Long driverId,
                         @RequestParam("startLocation") String startLocation,
                         @RequestParam("endLocation") String endLocation,
                         @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                         RedirectAttributes redirectAttributes) {
        try {
            Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(vehicleId);
            if (vehicleOpt.isPresent()) {
                Trip trip = new Trip();
                trip.setVehicle(vehicleOpt.get());
                trip.setDriverId(driverId);
                trip.setStartLocation(startLocation);
                trip.setEndLocation(endLocation);
                trip.setStartTime(startTime);
                
                tripService.saveTrip(trip);
                redirectAttributes.addFlashAttribute("success", 
                    "Trip scheduled successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Vehicle not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to schedule trip: " + e.getMessage());
        }
        
        return "redirect:/trips";
    }
    
    // Update existing trip
    @PostMapping("/edit/{id}")
    public String updateTrip(@PathVariable("id") Long id,
                            @RequestParam("vehicleId") Long vehicleId,
                            @RequestParam("driverId") Long driverId,
                            @RequestParam("startLocation") String startLocation,
                            @RequestParam("endLocation") String endLocation,
                            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                            @RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                            RedirectAttributes redirectAttributes) {
        try {
            Optional<Trip> tripOpt = tripService.getTripById(id);
            Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(vehicleId);
            
            if (tripOpt.isPresent() && vehicleOpt.isPresent()) {
                Trip trip = tripOpt.get();
                trip.setVehicle(vehicleOpt.get());
                trip.setDriverId(driverId);
                trip.setStartLocation(startLocation);
                trip.setEndLocation(endLocation);
                trip.setStartTime(startTime);
                
                if (endTime != null) {
                    trip.setEndTime(endTime);
                }
                
                tripService.saveTrip(trip);
                redirectAttributes.addFlashAttribute("success", 
                    "Trip updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Trip or Vehicle not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to update trip: " + e.getMessage());
        }
        
        return "redirect:/trips";
    }
    
    // Delete trip
    @GetMapping("/delete/{id}")
    public String deleteTrip(@PathVariable("id") Long id,
                            RedirectAttributes redirectAttributes) {
        try {
            tripService.deleteTrip(id);
            redirectAttributes.addFlashAttribute("success", 
                "Trip deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to delete trip: " + e.getMessage());
        }
        return "redirect:/trips";
    }
    
    // Complete trip (set end time)
    @GetMapping("/complete/{id}")
    public String completeTrip(@PathVariable("id") Long id,
                              RedirectAttributes redirectAttributes) {
        try {
            Trip trip = tripService.completeTrip(id);
            if (trip != null) {
                redirectAttributes.addFlashAttribute("success", 
                    "Trip completed successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Failed to complete trip!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error: " + e.getMessage());
        }
        return "redirect:/trips";
    }
    
    // View ongoing trips
    @GetMapping("/ongoing")
    public String viewOngoingTrips(Model model) {
        List<Trip> ongoingTrips = tripService.getOngoingTrips();
        List<Vehicle> vehicles = vehicleService.getActiveVehicles();
        model.addAttribute("trips", ongoingTrips);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("pageTitle", "Ongoing Trips");
        return "trip";
    }
}