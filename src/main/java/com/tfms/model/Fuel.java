package com.tfms.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "fuel")
public class Fuel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_id")
    private Long fuelId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    
    @NotNull(message = "Date is required")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    
    @NotNull(message = "Fuel quantity is required")
    @Column(name = "fuel_quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal fuelQuantity;
    
    @NotNull(message = "Cost is required")
    @Column(name = "cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;
    
    // Constructors
    public Fuel() {
    }
    
    public Fuel(Vehicle vehicle, LocalDateTime date, BigDecimal fuelQuantity, BigDecimal cost) {
        this.vehicle = vehicle;
        this.date = date;
        this.fuelQuantity = fuelQuantity;
        this.cost = cost;
    }
    
    // Getters and Setters
    public Long getFuelId() {
        return fuelId;
    }
    
    public void setFuelId(Long fuelId) {
        this.fuelId = fuelId;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public BigDecimal getFuelQuantity() {
        return fuelQuantity;
    }
    
    public void setFuelQuantity(BigDecimal fuelQuantity) {
        this.fuelQuantity = fuelQuantity;
    }
    
    public BigDecimal getCost() {
        return cost;
    }
    
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    
    
    public String getDateFormatted() {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
    
    @Override
    public String toString() {
        return "Fuel{" +
                "fuelId=" + fuelId +
                ", date=" + date +
                ", fuelQuantity=" + fuelQuantity +
                ", cost=" + cost +
                '}';
    }
}