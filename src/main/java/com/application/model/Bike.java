package com.application.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "bike")
public class Bike {
    @Id
    @Column(name = "bike_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bikeId;
    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "bike")
    private List<Order> orders;
    @OneToOne(mappedBy = "bike")
    private ParkingPointBikeMap parkingPointBikeMap;


    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
