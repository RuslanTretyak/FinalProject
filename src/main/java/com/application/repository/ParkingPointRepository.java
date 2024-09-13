package com.application.repository;

import com.application.model.entity.ParkingPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPointRepository extends JpaRepository<ParkingPoint, Integer> {
}
