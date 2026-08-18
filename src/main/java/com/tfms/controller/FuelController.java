package com.tfms.controller;

import com.tfms.model.Fuel;
import com.tfms.model.Vehicle;
import com.tfms.service.FuelService;
import com.tfms.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/fuel")
public class FuelController {
    
    @Autowired
    private FuelService fuelService;
    
    @Autowired
    private VehicleService vehicleService;
    
    // Main page - list all fuel records (Single JSP approach)
    @GetMapping
    public String listFuelRecords(Model model) {
        List<Fuel> fuelRecords = fuelService.getAllFuelRecords();
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("fuelRecords", fuelRecords);
        model.addAttribute("vehicles", vehicles);
        return "fuel"; // Returns fuel.jsp (single page)
    }
    
    // Add new fuel record
    @PostMapping("/add")
    public String addFuelRecord(@RequestParam("vehicleId") Long vehicleId,
                               @RequestParam("fuelQuantity") BigDecimal fuelQuantity,
                               @RequestParam("cost") BigDecimal cost,
                               RedirectAttributes redirectAttributes) {
        try {
            Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(vehicleId);
            if (vehicleOpt.isPresent()) {
                Fuel fuel = new Fuel();
                fuel.setVehicle(vehicleOpt.get());
                fuel.setFuelQuantity(fuelQuantity);
                fuel.setCost(cost);
                fuel.setDate(LocalDateTime.now());
                
                fuelService.saveFuelRecord(fuel);
                redirectAttributes.addFlashAttribute("success", 
                    "Fuel record added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Vehicle not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to add fuel record: " + e.getMessage());
        }
        
        return "redirect:/fuel";
    }
    
    // Delete fuel record
    @GetMapping("/delete/{id}")
    public String deleteFuelRecord(@PathVariable("id") Long id,
                                  RedirectAttributes redirectAttributes) {
        try {
            fuelService.deleteFuelRecord(id);
            redirectAttributes.addFlashAttribute("success", 
                "Fuel record deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to delete fuel record: " + e.getMessage());
        }
        return "redirect:/fuel";
    }
    
    // View fuel records by vehicle
    @GetMapping("/vehicle/{vehicleId}")
    public String viewFuelByVehicle(@PathVariable("vehicleId") Long vehicleId, 
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        try {
            Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(vehicleId);
            if (vehicleOpt.isPresent()) {
                List<Fuel> fuelRecords = fuelService.getFuelRecordsByVehicleId(vehicleId);
                BigDecimal totalCost = fuelService.calculateTotalCostByVehicle(vehicleId);
                BigDecimal totalQuantity = fuelService.calculateTotalQuantityByVehicle(vehicleId);
                
                model.addAttribute("vehicle", vehicleOpt.get());
                model.addAttribute("fuelRecords", fuelRecords);
                model.addAttribute("totalCost", totalCost);
                model.addAttribute("totalQuantity", totalQuantity);
                
                List<Vehicle> vehicles = vehicleService.getAllVehicles();
                model.addAttribute("vehicles", vehicles);
                
                return "fuel";
            } else {
                redirectAttributes.addFlashAttribute("error", "Vehicle not found!");
                return "redirect:/fuel";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error: " + e.getMessage());
            return "redirect:/fuel";
        }
    }
}