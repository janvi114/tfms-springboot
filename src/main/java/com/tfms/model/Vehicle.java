package com.tfms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;
    
    @NotBlank(message = "Registration number is required")
    @Column(name = "registration_number", unique = true, nullable = false, length = 50)
    private String registrationNumber;
    
    @NotNull(message = "Capacity is required")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;
    
    @NotBlank(message = "Status is required")
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    
    @Column(name = "last_serviced_date")
    private LocalDate lastServicedDate;
    
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Trip> trips;
    
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Fuel> fuelRecords;
    
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Maintenance> maintenanceRecords;
    
    // Constructors
    public Vehicle() {
    }
    
    public Vehicle(String registrationNumber, Integer capacity, String status) {
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.status = status;
        this.lastServicedDate = LocalDate.now();
    }
    
    // Getters and Setters
    public Long getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDate getLastServicedDate() {
        return lastServicedDate;
    }
    
    public void setLastServicedDate(LocalDate localDateTime) {
        this.lastServicedDate = localDateTime;
    }
    
    public String getLastServicedDateFormatted() {
        if (lastServicedDate == null) {
            return "";
        }
        return lastServicedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    
    public List<Trip> getTrips() {
        return trips;
    }
    
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
    
    public List<Fuel> getFuelRecords() {
        return fuelRecords;
    }
    
    public void setFuelRecords(List<Fuel> fuelRecords) {
        this.fuelRecords = fuelRecords;
    }
    
    public List<Maintenance> getMaintenanceRecords() {
        return maintenanceRecords;
    }
    
    public void setMaintenanceRecords(List<Maintenance> maintenanceRecords) {
        this.maintenanceRecords = maintenanceRecords;
    }
    
    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", capacity=" + capacity +
                ", status='" + status + '\'' +
                ", lastServicedDate=" + lastServicedDate +
                '}';
    }
}