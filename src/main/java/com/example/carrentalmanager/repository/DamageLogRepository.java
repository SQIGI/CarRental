package com.example.carrentalmanager.repository;

import com.example.carrentalmanager.entity.DamageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DamageLogRepository extends JpaRepository<DamageLog, Long> {
    List<DamageLog> findByCarId(Long carId);
}