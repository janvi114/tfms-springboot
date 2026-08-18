package com.tfms.controller;

import com.tfms.model.Maintenance;
import com.tfms.model.Vehicle;
import com.tfms.service.MaintenanceService;
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
@RequestMapping("/maintenance")
public class MaintenanceController {
    
    @Autowired
    private MaintenanceService maintenanceService;
    
    @Autowired
    private VehicleService vehicleService;
    
    // Main page - list all maintenance records (Single JSP approach)
    @GetMapping
    public String listMaintenanceRecords(Model model) {
        List<Maintenance> maintenanceRecords = maintenanceService.getAllMaintenanceRecords();
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("maintenanceRecords", maintenanceRecords);
        model.addAttribute("vehicles", vehicles);
        return "maintenance"; // Returns maintenance.jsp (single page)
    }
    
    // Schedule new maintenance
    @PostMapping("/schedule")
    public String scheduleMaintenance(@RequestParam("vehicleId") Long vehicleId,
                                     @RequestParam("description") String description,
                                     @RequestParam("scheduledDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledDate,
                                     @RequestParam("status") String status,
                                     RedirectAttributes redirectAttributes) {
        try {
            Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(vehicleId);
            if (vehicleOpt.isPresent()) {
                Maintenance maintenance = new Maintenance();
                maintenance.setVehicle(vehicleOpt.get());
                maintenance.setDescription(description);
                maintenance.setScheduledDate(scheduledDate);
                maintenance.setStatus(status);
                
                maintenanceService.saveMaintenanceRecord(maintenance);
                redirectAttributes.addFlashAttribute("success", 
                    "Maintenance scheduled successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Vehicle not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to schedule maintenance: " + e.getMessage());
        }
        
        return "redirect:/maintenance";
    }
    
    // Update existing maintenance
    @PostMapping("/edit/{id}")
    public String updateMaintenance(@PathVariable("id") Long id,
                                   @RequestParam("vehicleId") Long vehicleId,
                                   @RequestParam("description") String description,
                                   @RequestParam("scheduledDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledDate,
                                   @RequestParam("status") String status,
                                   RedirectAttributes redirectAttributes) {
        try {
            Optional<Maintenance> maintenanceOpt = maintenanceService.getMaintenanceRecordById(id);
            Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(vehicleId);
            
            if (maintenanceOpt.isPresent() && vehicleOpt.isPresent()) {
                Maintenance maintenance = maintenanceOpt.get();
                maintenance.setVehicle(vehicleOpt.get());
                maintenance.setDescription(description);
                maintenance.setScheduledDate(scheduledDate);
                maintenance.setStatus(status);
                
                maintenanceService.saveMaintenanceRecord(maintenance);
                redirectAttributes.addFlashAttribute("success", 
                    "Maintenance updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Maintenance or Vehicle not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to update maintenance: " + e.getMessage());
        }
        
        return "redirect:/maintenance";
    }
    
    // Delete maintenance
    @GetMapping("/delete/{id}")
    public String deleteMaintenance(@PathVariable("id") Long id,
                                   RedirectAttributes redirectAttributes) {
        try {
            maintenanceService.deleteMaintenanceRecord(id);
            redirectAttributes.addFlashAttribute("success", 
                "Maintenance record deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to delete maintenance record: " + e.getMessage());
        }
        return "redirect:/maintenance";
    }
    
    // Update maintenance status
    @PostMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable("id") Long id,
                              @RequestParam("status") String status,
                              RedirectAttributes redirectAttributes) {
        try {
            Maintenance maintenance = maintenanceService.updateMaintenanceStatus(id, status);
            if (maintenance != null) {
                redirectAttributes.addFlashAttribute("success", 
                    "Maintenance status updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Failed to update maintenance status!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error: " + e.getMessage());
        }
        return "redirect:/maintenance";
    }
    
    // View upcoming maintenance
    @GetMapping("/upcoming")
    public String viewUpcomingMaintenance(Model model) {
        List<Maintenance> upcomingMaintenance = maintenanceService.getUpcomingMaintenance();
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("maintenanceRecords", upcomingMaintenance);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("pageTitle", "Upcoming Maintenance");
        return "maintenance";
    }
    
    // View overdue maintenance
    @GetMapping("/overdue")
    public String viewOverdueMaintenance(Model model) {
        List<Maintenance> overdueMaintenance = maintenanceService.getOverdueMaintenance();
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("maintenanceRecords", overdueMaintenance);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("pageTitle", "Overdue Maintenance");
        return "maintenance";
    }
}