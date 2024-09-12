package com.application.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "parking_point")
public class ParkingPoint {
    @Id
    @Column(name = "parking_point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parkingPointId;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @OneToMany(mappedBy = "parkingPoint")
    private List<Order> orders;
    @OneToOne(mappedBy = "parkingPoint")
    private ParkingPointBikeMap parkingPointBikeMap;

    public int getParkingPointId() {
        return parkingPointId;
    }

    public void setParkingPointId(int parkingPointId) {
        this.parkingPointId = parkingPointId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
