package com.example.carrentalmanager.controller;

import com.example.carrentalmanager.entity.Car;
import com.example.carrentalmanager.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cars")
public class RestAdminController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.findAll();
        return ResponseEntity.ok(cars);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car savedCar = carService.save(car);
        return ResponseEntity.ok(savedCar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        car.setId(id);
        Car updatedCar = carService.save(car);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}