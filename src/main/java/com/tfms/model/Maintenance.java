package com.tfms.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "maintenance")
public class Maintenance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_id")
    private Long maintenanceId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    
    @NotBlank(message = "Description is required")
    @Column(name = "description", nullable = false, length = 255)
    private String description;
    
    @NotNull(message = "Scheduled date is required")
    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDate;
    
    @NotBlank(message = "Status is required")
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    
    // Constructors
    public Maintenance() {
    }
    
    public Maintenance(Vehicle vehicle, String description, LocalDateTime scheduledDate, String status) {
        this.vehicle = vehicle;
        this.description = description;
        this.scheduledDate = scheduledDate;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getMaintenanceId() {
        return maintenanceId;
    }
    
    public void setMaintenanceId(Long maintenanceId) {
        this.maintenanceId = maintenanceId;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }
    
    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    
    public String getScheduledDateFormatted() {
        if (scheduledDate == null) {
            return "";
        }
        return scheduledDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
    
    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenanceId=" + maintenanceId +
                ", description='" + description + '\'' +
                ", scheduledDate=" + scheduledDate +
                ", status='" + status + '\'' +
                '}';
    }
}