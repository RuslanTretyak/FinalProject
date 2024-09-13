package com.application.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_point_bike_map")
public class ParkingPointBikeMap {
    @Id
    @Column(name = "parking_point_bike_map_id")
    private int parkingPointId;
    @OneToOne
    @JoinColumn(name = "parkingPoint", referencedColumnName = "parking_point_id")
    private ParkingPoint parkingPoint;
    @OneToOne
    @JoinColumn(name = "bike", referencedColumnName = "bike_id")
    private Bike bike;

    public int getParkingPointId() {
        return parkingPointId;
    }

    public void setParkingPointId(int parkingPointId) {
        this.parkingPointId = parkingPointId;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public ParkingPoint getParkingPoint() {
        return parkingPoint;
    }

    public void setParkingPoint(ParkingPoint parkingPoint) {
        this.parkingPoint = parkingPoint;
    }
}
