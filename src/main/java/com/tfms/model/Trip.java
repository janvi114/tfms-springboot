package com.tfms.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "trip")
public class Trip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long tripId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    
    @NotNull(message = "Driver ID is required")
    @Column(name = "driver_id", nullable = false)
    private Long driverId;
    
    @NotBlank(message = "Start location is required")
    @Column(name = "start_location", nullable = false, length = 100)
    private String startLocation;
    
    @NotBlank(message = "End location is required")
    @Column(name = "end_location", nullable = false, length = 100)
    private String endLocation;
    
    @NotNull(message = "Start time is required")
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    // Constructors
    public Trip() {
    }
    
    public Trip(Vehicle vehicle, Long driverId, String startLocation, String endLocation, LocalDateTime startTime) {
        this.vehicle = vehicle;
        this.driverId = driverId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startTime = startTime;
    }
    
    // Getters and Setters
    public Long getTripId() {
        return tripId;
    }
    
    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public Long getDriverId() {
        return driverId;
    }
    
    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
    
    public String getStartLocation() {
        return startLocation;
    }
    
    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }
    
    public String getEndLocation() {
        return endLocation;
    }
    
    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    
    public String getStartTimeFormatted() {
        if (startTime == null) {
            return "";
        }
        return startTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
    
    public String getEndTimeFormatted() {
        if (endTime == null) {
            return "";
        }
        return endTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
    
    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", driverId=" + driverId +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}