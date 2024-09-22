package com.application.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "bike")
public class Bike {
    @Id
    @Column(name = "bike_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bikeId;
    @Column(name = "serial_number")
    @NotEmpty(message = "Serial Number field cannot be empty")
    @Size(min = 5, max = 10)
    private String serialNumber;
    @Column(name = "status")
    @NotEmpty(message = "Status field cannot be empty")
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

    @Override
    public String toString() {
        return "Bike{" +
                "bikeId=" + bikeId +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
