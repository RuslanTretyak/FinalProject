package com.application.service;

import com.application.constant.BikeStatus;
import com.application.constant.OrderStatus;
import com.application.model.dto.OrderDTO;
import com.application.model.entity.Bike;
import com.application.model.entity.Order;
import com.application.model.entity.Person;
import com.application.repository.BikeRepository;
import com.application.repository.OrderRepository;
import com.application.repository.ParkingPointRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class OrderService {
    private OrderRepository orderRepository;
    private ParkingPointRepository parkingPointRepository;
    private BikeRepository bikeRepository;
    private ParkingPointBikeMapService parkingPointBikeMapService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ParkingPointRepository parkingPointRepository, BikeRepository bikeRepository, ParkingPointBikeMapService parkingPointBikeMapService) {
        this.orderRepository = orderRepository;
        this.parkingPointRepository = parkingPointRepository;
        this.bikeRepository = bikeRepository;
        this.parkingPointBikeMapService = parkingPointBikeMapService;
    }

    @Transactional
    public void createOrder(OrderDTO orderDTO){
        Date dateOfBegin = new Date();
        Order order = new Order();
        Bike bike = bikeRepository.findById(orderDTO.getBikeId()).get();
        parkingPointBikeMapService.removeBikeFromParking(bike.getBikeId());
        bike.setStatus(BikeStatus.IN_ORDER.name());
        order.setParkingPoint(parkingPointRepository.findById(orderDTO.getParkingPointId()).get());
        order.setBike(bike);
        order.setTerm(orderDTO.getTerm());
        order.setDateOfBegin(dateOfBegin);
        order.setEndDate(new Date(dateOfBegin.getTime() + (orderDTO.getTerm() * 3600000L)));
        order.setPerson(orderDTO.getPerson());
        order.setStatus(OrderStatus.OPEN.name());
        orderRepository.save(order);
    }
    public List<Order> getOrdersByPersonAndStatus(Person person, OrderStatus orderStatus){
        return orderRepository.findBikeOrderByPersonAndStatus(person, orderStatus.name());
    }
    public List<Order> getOrdersByPerson(Person person){
        return orderRepository.findBikeOrderByPerson(person.getPersonId());
    }

}
