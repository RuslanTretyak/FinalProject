package com.application.model.dto;

import com.application.model.entity.Person;

public class OrderDTO {
    private int parkingPointId;
    private int bikeId;
    private int termMinutes;
    private int termHours;
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

    public int getTermMinutes() {
        return termMinutes;
    }

    public void setTermMinutes(int termMinutes) {
        this.termMinutes = termMinutes;
    }

    public int getTermHours() {
        return termHours;
    }

    public void setTermHours(int termHours) {
        this.termHours = termHours;
    }
}
