package com.tfms.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfms.model.Vehicle;
import com.tfms.service.VehicleService;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    
    @Autowired
    private VehicleService vehicleService;
    
    // Main page - list all vehicles (Single JSP approach)
    @GetMapping({"", "/"})
    public String listVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehicle"; // Returns vehicle.jsp (single page)
    }
    
    
    // Add new vehicle
    @PostMapping("/add")
    public String addVehicle(@RequestParam("registrationNumber") String registrationNumber,
                            @RequestParam("capacity") Integer capacity,
                            @RequestParam("status") String status,
                            RedirectAttributes redirectAttributes) {
        try {
            // Check if registration number already exists
            if (vehicleService.existsByRegistrationNumber(registrationNumber)) {
                redirectAttributes.addFlashAttribute("error", 
                    "Registration number already exists!");
                return "redirect:/vehicles";
            }
            
            Vehicle vehicle = new Vehicle();
            vehicle.setRegistrationNumber(registrationNumber);
            vehicle.setCapacity(capacity);
            vehicle.setStatus(status);
            vehicle.setLastServicedDate(LocalDate.now());
            
            vehicleService.saveVehicle(vehicle);
            redirectAttributes.addFlashAttribute("success", 
                "Vehicle added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to add vehicle: " + e.getMessage());
        }
        
        return "redirect:/vehicles";
    }
    
    // Update existing vehicle
    @PostMapping("/edit/{id}")
    public String updateVehicle(@PathVariable("id") Long id,
                               @RequestParam("registrationNumber") String registrationNumber,
                               @RequestParam("capacity") Integer capacity,
                               @RequestParam("status") String status,
                               @RequestParam(value = "lastServicedDate", required = false) String lastServicedDate,
                               RedirectAttributes redirectAttributes) {
        try {
            Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(id);
            if (vehicleOpt.isPresent()) {
                Vehicle vehicle = vehicleOpt.get();
                vehicle.setRegistrationNumber(registrationNumber);
                vehicle.setCapacity(capacity);
                vehicle.setStatus(status);
                
                // Keep existing last serviced date if not provided
                if (lastServicedDate == null || lastServicedDate.isEmpty()) {
                    vehicle.setLastServicedDate(vehicle.getLastServicedDate());
                }
                
                vehicleService.saveVehicle(vehicle);
                redirectAttributes.addFlashAttribute("success", 
                    "Vehicle updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Vehicle not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to update vehicle: " + e.getMessage());
        }
        
        return "redirect:/vehicles";
    }
    
    // Delete vehicle
    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable("id") Long id,
                               RedirectAttributes redirectAttributes) {
        try {
            vehicleService.deleteVehicle(id);
            redirectAttributes.addFlashAttribute("success", 
                "Vehicle deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Cannot delete vehicle. It may have associated records.");
        }
        return "redirect:/vehicles";
    }
    
    // Update vehicle status (Additional feature)
    @PostMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable("id") Long id,
                              @RequestParam("status") String status,
                              RedirectAttributes redirectAttributes) {
        try {
            Vehicle vehicle = vehicleService.updateVehicleStatus(id, status);
            if (vehicle != null) {
                redirectAttributes.addFlashAttribute("success", 
                    "Vehicle status updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Failed to update vehicle status!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error: " + e.getMessage());
        }
        return "redirect:/vehicles";
    }
}