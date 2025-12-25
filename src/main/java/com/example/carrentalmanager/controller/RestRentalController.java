package com.example.carrentalmanager.controller;

import com.example.carrentalmanager.entity.Car;
import com.example.carrentalmanager.entity.Rental;
import com.example.carrentalmanager.service.CarService;
import com.example.carrentalmanager.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RestRentalController {

    @Autowired
    private RentalService rentalService;
    
    @Autowired
    private CarService carService;

    @PostMapping
    public ResponseEntity<Rental> bookCar(@RequestBody RentalRequest request) {
        Car car = carService.findById(request.getCarId());
        if (car == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Get current user ID (in a real app, you'd extract from security context)
        // For this example, we'll use a placeholder
        Long userId = 1L; // This should be extracted from security context
        
        Rental rental = rentalService.createRental(
            car, 
            userId, 
            request.getStartDate(), 
            request.getEndDate(), 
            request.getDeposit()
        );
        
        return ResponseEntity.ok(rental);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Rental>> getMyRentals() {
        // Get current user ID from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // In a real app, you would get user ID from the authenticated user
        // For this example, we'll use a placeholder
        Long userId = 1L; // This should be extracted from security context
        
        List<Rental> rentals = rentalService.findByUserId(userId);
        return ResponseEntity.ok(rentals);
    }
    
    // Inner class for request body
    public static class RentalRequest {
        private Long carId;
        private LocalDate startDate;
        private LocalDate endDate;
        private Double deposit;
        
        // Getters and setters
        public Long getCarId() { return carId; }
        public void setCarId(Long carId) { this.carId = carId; }
        
        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
        
        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
        
        public Double getDeposit() { return deposit; }
        public void setDeposit(Double deposit) { this.deposit = deposit; }
    }
}