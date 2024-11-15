package com.application.service;

import com.application.exception.DataNotFoundException;
import com.application.model.entity.Bike;
import com.application.model.entity.ParkingPoint;
import com.application.model.entity.Person;
import com.application.repository.ParkingPointRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingService {
    private ParkingPointRepository parkingPointRepository;

    @Autowired
    public ParkingService(ParkingPointRepository parkingPointRepository) {
        this.parkingPointRepository = parkingPointRepository;
    }
    public List<ParkingPoint> getAllParking(){
        return parkingPointRepository.findAll();
    }
    @Transactional
    public void addNewParking(ParkingPoint parkingPoint) {
        parkingPointRepository.save(parkingPoint);
    }

    public ParkingPoint getParkingById(int id) throws DataNotFoundException {
        return parkingPointRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Parking Point with id " + id + " was not found"));
    }
}
