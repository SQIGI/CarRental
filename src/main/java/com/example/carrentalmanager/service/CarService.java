package com.example.carrentalmanager.service;

import com.example.carrentalmanager.entity.Car;
import com.example.carrentalmanager.entity.CarStatus;
import com.example.carrentalmanager.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    public List<Car> findByStatus(CarStatus status) {
        return carRepository.findByStatus(status);
    }

    public List<Car> findByStatusAndCategoryAndPrice(CarStatus status, Long categoryId, Double maxPrice) {
        return carRepository.findByStatusAndCategoryAndPrice(status, categoryId, maxPrice);
    }
}