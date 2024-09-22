package com.application.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "parking_point")
public class ParkingPoint {
    @Id
    @Column(name = "parking_point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parkingPointId;
    @Column(name = "name")
    @NotEmpty(message = "Name Number field cannot be empty")
    @Size(min = 5, max = 20)
    private String name;
    @Column(name = "address")
    @NotEmpty(message = "Address Number field cannot be empty")
    @Size(min = 5, max = 40)
    private String address;
    @OneToMany(mappedBy = "parkingPoint")
    private List<Order> orders;
    @OneToMany(mappedBy = "parkingPoint")
    private List<ParkingPointBikeMap> parkingPointBikeMap;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<ParkingPointBikeMap> getParkingPointBikeMap() {
        return parkingPointBikeMap;
    }

    public void setParkingPointBikeMap(List<ParkingPointBikeMap> parkingPointBikeMap) {
        this.parkingPointBikeMap = parkingPointBikeMap;
    }

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

    @Override
    public String toString() {
        return "ParkingPoint{" +
                "parkingPointId=" + parkingPointId +
                ", name='" + name + '\'' +
                '}';
    }
}
