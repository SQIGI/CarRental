package com.example.carrentalmanager.controller;

import com.example.carrentalmanager.entity.Car;
import com.example.carrentalmanager.entity.CarStatus;
import com.example.carrentalmanager.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class RestCarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double maxPrice) {
        List<Car> cars = carService.findByStatusAndCategoryAndPrice(CarStatus.AVAILABLE, categoryId, maxPrice);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Car car = carService.findById(id);
        if (car != null) {
            return ResponseEntity.ok(car);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}