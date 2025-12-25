package com.example.carrentalmanager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String brand;
    private String model;
    private Integer year;
    private String licensePlate;
    private Double pricePerDay;
    
    @Enumerated(EnumType.STRING)
    private CarStatus status;
    
    private String photoUrl;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Constructors
    public Car() {}

    public Car(String brand, String model, Integer year, String licensePlate, 
               Double pricePerDay, CarStatus status, String photoUrl, String description, Category category) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.pricePerDay = pricePerDay;
        this.status = status;
        this.photoUrl = photoUrl;
        this.description = description;
        this.category = category;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public Double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(Double pricePerDay) { this.pricePerDay = pricePerDay; }

    public CarStatus getStatus() { return status; }
    public void setStatus(CarStatus status) { this.status = status; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}