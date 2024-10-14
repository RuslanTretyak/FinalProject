package com.application.service;

import com.application.constant.BikeStatus;
import com.application.constant.OrderStatus;
import com.application.exception.DataNotFoundException;
import com.application.model.dto.OrderDTO;
import com.application.model.entity.Bike;
import com.application.model.entity.Order;
import com.application.model.entity.Person;
import com.application.repository.BikeRepository;
import com.application.repository.OrderRepository;
import com.application.repository.ParkingPointRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class OrderService {
    private OrderRepository orderRepository;
    private ParkingPointRepository parkingPointRepository;
    private BikeRepository bikeRepository;
    private ParkingPointBikeMapService parkingPointBikeMapService;
    private AppService appService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ParkingPointRepository parkingPointRepository, BikeRepository bikeRepository, ParkingPointBikeMapService parkingPointBikeMapService, AppService appService) {
        this.orderRepository = orderRepository;
        this.parkingPointRepository = parkingPointRepository;
        this.bikeRepository = bikeRepository;
        this.parkingPointBikeMapService = parkingPointBikeMapService;
        this.appService = appService;
    }

    @Transactional
    public void createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        Date dateOfBegin = new Date();
        Order order = new Order();
        Bike bike = bikeRepository.findById(orderDTO.getBikeId()).orElseThrow(() -> new DataNotFoundException("Bike with id " + orderDTO.getBikeId() + " was not found"));
        parkingPointBikeMapService.removeBikeFromParking(bike.getBikeId());
        bike.setStatus(BikeStatus.IN_ORDER.name());
        order.setParkingPoint(parkingPointRepository.findById(orderDTO.getParkingPointId()).orElseThrow(() -> new DataNotFoundException("Parking Point with id " + orderDTO.getParkingPointId() + " was not found")));
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
    public Page<Order> getOrdersByPerson(Person person, Pageable pageable){
        return orderRepository.findBikeOrderByPerson(person, pageable);
    }
    public Page<Order> getAllOrders(Pageable pageable){
        return orderRepository.findAll(pageable);
    }

    @Transactional
    public void closeOrder(int id) throws DataNotFoundException {
        Date date = new Date();
        Order order = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order with id " + id + " was not found"));
        order.setStatus(OrderStatus.CLOSE.name());
        order.getBike().setStatus(BikeStatus.AVAILABLE_FOR_PLACEMENT.name());
        order.setEndDate(date);
        Person person = order.getPerson();
        person.setBalance(person.getBalance() - appService.calculateCost(order.getDateOfBegin(), date));
    }
    public boolean checkBalanceForOrder(OrderDTO orderDTO) {
        Date date = new Date();
        double cost = appService.calculateCost(date, new Date(date.getTime() + (orderDTO.getTerm() * 3600000L)));
        return orderDTO.getPerson().getBalance() >= cost;
    }
    public Order getOrderById(int id) throws DataNotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order with id " + id + " was not found"));
    }
}
