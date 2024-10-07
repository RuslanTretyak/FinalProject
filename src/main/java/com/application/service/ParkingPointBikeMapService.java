package com.application.service;

import com.application.constant.BikeStatus;
import com.application.exception.DataNotFoundException;
import com.application.model.entity.Bike;
import com.application.model.entity.ParkingPointBikeMap;
import com.application.repository.ParkingPointBikeMapRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingPointBikeMapService {
    private ParkingPointBikeMapRepository parkingPointBikeMapRepository;
    private ParkingService parkingService;
    private BikeService bikeService;

    @Autowired
    public ParkingPointBikeMapService(ParkingPointBikeMapRepository parkingPointBikeMapRepository, ParkingService parkingService, BikeService bikeService) {
        this.parkingPointBikeMapRepository = parkingPointBikeMapRepository;
        this.parkingService = parkingService;
        this.bikeService = bikeService;
    }

    @Transactional
    public void addBikeToParking(int parkingId, int bikeId) throws DataNotFoundException {
        ParkingPointBikeMap parkingPointBikeMap = new ParkingPointBikeMap();
        parkingPointBikeMap.setParkingPoint(parkingService.getParkingById(parkingId));
        Bike bike = bikeService.getBikeById(bikeId);
        bike.setStatus(BikeStatus.AVAILABLE_FOR_ORDER.name());
        parkingPointBikeMap.setBike(bike);
        parkingPointBikeMapRepository.save(parkingPointBikeMap);
    }

    @Transactional
    public void removeBikeFromParking(int bikeId) throws DataNotFoundException {
        Bike bike = bikeService.getBikeById(bikeId);
        bike.setStatus(BikeStatus.AVAILABLE_FOR_PLACEMENT.name());
        parkingPointBikeMapRepository.removeBikeFromParking(bikeId);
    }
}
