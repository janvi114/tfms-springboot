package com.tfms.controller;

import com.tfms.service.VehicleService;
import com.tfms.service.TripService;
import com.tfms.service.FuelService;
import com.tfms.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private TripService tripService;

    @Autowired
    private FuelService fuelService;

    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping({"/", "/dashboard"})
    public String home(Model model) {

        Map<String, Object> dashboardData = new HashMap<>();

        // ===== VEHICLES =====
        dashboardData.put("totalVehicles",
                vehicleService.getAllVehicles().size());
        dashboardData.put("activeVehicles",
                vehicleService.countVehiclesByStatus("ACTIVE"));
        dashboardData.put("inactiveVehicles",
                vehicleService.countVehiclesByStatus("INACTIVE"));

        // ===== TRIPS =====
        dashboardData.put("totalTrips",
                tripService.getAllTrips().size());
        dashboardData.put("ongoingTrips",
                tripService.getOngoingTrips().size());
        dashboardData.put("completedTrips",
                tripService.getCompletedTrips().size());

        // ===== FUEL =====
        // If you don’t have getAllFuelRecords(), use whatever list method you have there
        dashboardData.put("totalFuelRecords",
                fuelService.getAllFuelRecords().size());

        // ===== MAINTENANCE =====
        dashboardData.put("scheduledMaintenance",
                maintenanceService.countMaintenanceByStatus("SCHEDULED"));
        dashboardData.put("overdueMaintenance",
                maintenanceService.countMaintenanceByStatus("OVERDUE"));
        dashboardData.put("completedMaintenance",
                maintenanceService.countMaintenanceByStatus("COMPLETED"));

        model.addAttribute("dashboardData", dashboardData);

        return "home";
    }
}
