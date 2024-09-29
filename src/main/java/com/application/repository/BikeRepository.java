package com.application.repository;

import com.application.constant.BikeStatus;
import com.application.model.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {
    @Modifying
    @Query(value = "update bike set status = :status where bike_id = :id", nativeQuery = true)
    void updateBike(@Param("status") String status, @Param("id") int id);
    List<Bike> getBikeByStatus(String bikeStatus);
}
