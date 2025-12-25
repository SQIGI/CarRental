package com.example.carrentalmanager.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalPrice;
    
    @Enumerated(EnumType.STRING)
    private RentalStatus status;
    
    private Double deposit;

    // Constructors
    public Rental() {}

    public Rental(User user, Car car, LocalDate startDate, LocalDate endDate, 
                  Double totalPrice, RentalStatus status, Double deposit) {
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.deposit = deposit;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public RentalStatus getStatus() { return status; }
    public void setStatus(RentalStatus status) { this.status = status; }

    public Double getDeposit() { return deposit; }
    public void setDeposit(Double deposit) { this.deposit = deposit; }
}