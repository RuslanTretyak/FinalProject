package com.application.repository;

import com.application.model.entity.ParkingPointBikeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPointBikeMapRepository extends JpaRepository<ParkingPointBikeMap, Integer> {
    @Modifying
    @Query(value = "delete from parking_point_bike_map where bike = :id", nativeQuery = true)
    void removeBikeFromParking(@Param("id") int id);
}
