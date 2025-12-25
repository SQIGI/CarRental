package com.example.carrentalmanager.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "damage_logs")
public class DamageLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    
    private String description;
    private LocalDate date;
    private Double cost;

    // Constructors
    public DamageLog() {}

    public DamageLog(Car car, String description, LocalDate date, Double cost) {
        this.car = car;
        this.description = description;
        this.date = date;
        this.cost = cost;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }
}