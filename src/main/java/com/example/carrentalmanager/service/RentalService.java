package com.example.carrentalmanager.service;

import com.example.carrentalmanager.entity.Car;
import com.example.carrentalmanager.entity.CarStatus;
import com.example.carrentalmanager.entity.Rental;
import com.example.carrentalmanager.entity.RentalStatus;
import com.example.carrentalmanager.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Rental findById(Long id) {
        return rentalRepository.findById(id).orElse(null);
    }

    public Rental save(Rental rental) {
        // Calculate total price if not set
        if (rental.getTotalPrice() == null && rental.getStartDate() != null && rental.getEndDate() != null) {
            long days = ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate()) + 1;
            rental.setTotalPrice(rental.getCar().getPricePerDay() * days);
        }
        return rentalRepository.save(rental);
    }

    public void deleteById(Long id) {
        rentalRepository.deleteById(id);
    }

    public List<Rental> findByUserId(Long userId) {
        return rentalRepository.findByUserId(userId);
    }

    public List<Rental> findByCarId(Long carId) {
        return rentalRepository.findByCarId(carId);
    }

    public Rental createRental(Car car, Long userId, LocalDate startDate, LocalDate endDate, Double deposit) {
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setStartDate(startDate);
        rental.setEndDate(endDate);
        rental.setDeposit(deposit);
        rental.setStatus(RentalStatus.BOOKED);
        
        // Calculate total price
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        rental.setTotalPrice(car.getPricePerDay() * days);
        
        // Update car status
        car.setStatus(CarStatus.RENTED);
        
        return save(rental);
    }
}