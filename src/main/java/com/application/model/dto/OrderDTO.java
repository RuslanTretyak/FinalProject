package com.application.model.dto;

import com.application.model.entity.Person;

public class OrderDTO {
    private int parkingPointId;
    private int bikeId;
    private int term;
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getParkingPointId() {
        return parkingPointId;
    }

    public void setParkingPointId(int parkingPointId) {
        this.parkingPointId = parkingPointId;
    }

    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "parkingPointId=" + parkingPointId +
                ", bikeId=" + bikeId +
                ", term=" + term +
                '}';
    }
}
