package com.example.carrentalmanager.repository;

import com.example.carrentalmanager.entity.Car;
import com.example.carrentalmanager.entity.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByStatus(CarStatus status);
    
    @Query("SELECT c FROM Car c WHERE c.status = :status AND (:categoryId IS NULL OR c.category.id = :categoryId) AND (:maxPrice IS NULL OR c.pricePerDay <= :maxPrice)")
    List<Car> findByStatusAndCategoryAndPrice(@Param("status") CarStatus status, 
                                              @Param("categoryId") Long categoryId, 
                                              @Param("maxPrice") Double maxPrice);
}