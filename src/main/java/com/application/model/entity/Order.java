package com.application.model.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "bike_order")
public class Order {


    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @Column(name = "date_of_begin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBegin;
    @Column(name = "term")
    private int term;
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "person", referencedColumnName = "person_id")
    private Person person;
    @ManyToOne
    @JoinColumn(name = "bike", referencedColumnName = "bike_id")
    private Bike bike;
    @ManyToOne
    @JoinColumn(name = "parking_point", referencedColumnName = "parking_point_id")
    private ParkingPoint parkingPoint;
    @Column(name = "status")
    private String status;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getDateOfBegin() {
        return dateOfBegin;
    }

    public void setDateOfBegin(Date dateOfBegin) {
        this.dateOfBegin = dateOfBegin;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
