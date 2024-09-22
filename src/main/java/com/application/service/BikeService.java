package com.application.service;

import com.application.constant.BikeStatus;
import com.application.model.entity.Bike;
import com.application.model.entity.ParkingPoint;
import com.application.model.entity.ParkingPointBikeMap;
import com.application.repository.BikeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BikeService {
    private BikeRepository bikeRepository;
    private ParkingService parkingService;

    @Autowired
    public BikeService(BikeRepository bikeRepository, ParkingService parkingService) {
        this.bikeRepository = bikeRepository;
        this.parkingService = parkingService;
    }

    @Transactional
    public void addNewBike(Bike bike) {
        bikeRepository.save(bike);
    }

    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }

    public Bike getBikeById(int id) {
        return bikeRepository.findById(Integer.valueOf(id)).get();
    }

    @Transactional
    public void updateBike(Bike bike) {
        bikeRepository.updateBike(bike.getStatus(), bike.getBikeId());
    }

    public List<Bike> getBikeByStatus(BikeStatus bikeStatus) {
        return bikeRepository.getBikeByStatus(bikeStatus.name());
    }

    public List<Bike> getBikeFromParking(int parkingId) {
        List<Bike> bikes = new ArrayList<>();
        ParkingPoint parkingPoint = parkingService.getParkingById(parkingId);
        for (ParkingPointBikeMap parkingPointBikeMap : parkingPoint.getParkingPointBikeMap()) {
            bikes.add(parkingPointBikeMap.getBike());
        }
        return bikes;
    }

}
