package com.application.controller;

import com.application.constant.BikeStatus;
import com.application.constant.OrderStatus;
import com.application.exception.DataNotFoundException;
import com.application.model.entity.Bike;
import com.application.model.entity.ParkingPoint;
import com.application.model.entity.Person;
import com.application.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private BikeService bikeService;
    private ParkingService parkingService;
    private ParkingPointBikeMapService parkingPointBikeMapService;
    private PersonService personService;
    private OrderService orderService;

    @Autowired
    public AdminController(BikeService bikeService, ParkingService parkingService, ParkingPointBikeMapService parkingPointBikeMapService, PersonService personService, OrderService orderService) {
        this.bikeService = bikeService;
        this.parkingService = parkingService;
        this.parkingPointBikeMapService = parkingPointBikeMapService;
        this.personService = personService;
        this.orderService = orderService;
    }

    @GetMapping("/bike")
    public ModelAndView showAllBikes() {
        return new ModelAndView("bikes", "bikes", bikeService.getAllBikes());
    }

    @GetMapping("/bike/add")
    public ModelAndView showAddBikePage(@ModelAttribute Bike bike) {
        return new ModelAndView("add_bike", "statuses", BikeStatus.values());
    }

    @PostMapping("/bike/add")
    public ModelAndView addBike(@Valid @ModelAttribute Bike bike, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("add_bike", "statuses", BikeStatus.values());
        } else {
            bikeService.addNewBike(bike);
            return new ModelAndView("redirect:/admin/bike");
        }
    }

    @GetMapping("/bike/{id}")
    public ModelAndView showBikeInfo(@PathVariable int id) throws DataNotFoundException {
        ModelAndView modelAndView = new ModelAndView("bike_info", "bike", bikeService.getBikeById(id));
        modelAndView.addObject("statuses", BikeStatus.values());
        return modelAndView;
    }

    @PostMapping("/bike")
    public ModelAndView changeBikeStatus(@ModelAttribute Bike bike) throws DataNotFoundException {
        bikeService.updateBike(bike);
        ModelAndView modelAndView = new ModelAndView("bike_info", "bike", bikeService.getBikeById(bike.getBikeId()));
        modelAndView.addObject("statuses", BikeStatus.values());
        return modelAndView;
    }

    @GetMapping("/parking")
    public ModelAndView showAllParking() {
        return new ModelAndView("parking", "parking", parkingService.getAllParking());

    }

    @GetMapping("/parking/{id}")
    public ModelAndView showParkingInfo(@PathVariable int id) throws DataNotFoundException {
        ModelAndView modelAndView = new ModelAndView("parking_info", "parkingPoint", parkingService.getParkingById(id));
        modelAndView.addObject("availableBikes", bikeService.getBikeByStatus(BikeStatus.AVAILABLE_FOR_PLACEMENT));
        modelAndView.addObject("placementBikes", bikeService.getBikeFromParking(id));
        return modelAndView;
    }

    @GetMapping("/parking/add")
    public ModelAndView showAddParkingPage(@ModelAttribute ParkingPoint parkingPoint) {
        return new ModelAndView("add_parking");
    }

    @PostMapping("/parking/add")
    public ModelAndView addParking(@Valid @ModelAttribute ParkingPoint parkingPoint, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("add_parking");
        } else {
            parkingService.addNewParking(parkingPoint);
            return new ModelAndView("redirect:/admin/parking");
        }
    }

    @PostMapping("/parking/add_bike/{bikeId}/{parkingId}")
    public String addBikeToParking(@PathVariable int bikeId, @PathVariable int parkingId) throws DataNotFoundException {
        parkingPointBikeMapService.addBikeToParking(parkingId, bikeId);
        return "redirect:/admin/parking/" + parkingId;
    }

    @PostMapping("/parking/remove_bike/{bikeId}/{parkingId}")
    public String removeBikeFromParking(@PathVariable int bikeId, @PathVariable int parkingId) throws DataNotFoundException {
        parkingPointBikeMapService.removeBikeFromParking(bikeId);
        return "redirect:/admin/parking/" + parkingId;
    }

    @GetMapping("/users")
    public ModelAndView showFindUserPage() {
        return new ModelAndView("find_user");
    }

    @GetMapping("/user")
    public ModelAndView findUser(@AuthenticationPrincipal UserDetails userDetails, @Param("login") String login, @Param("surname") String surname, @Param("name") String name) throws DataNotFoundException {
        Person person = personService.findPersonByLogin(userDetails.getUsername());
        List<Person> users;
        ModelAndView modelAndView = new ModelAndView("find_user", "person", person);
        if (login.isEmpty() && surname.isEmpty() && name.isEmpty()) {
            return modelAndView;
        } else {
            if (!login.isEmpty()) {
                Person user = personService.findPersonByLogin(login);
                users = Collections.singletonList(user);
            } else if (!surname.isEmpty()) {
                if (!name.isEmpty()) {
                    users = personService.findPersonBySurnameAndName(surname, name);
                } else {
                    users = personService.findPersonBySurname(surname);
                }
            } else {
                users = personService.findPersonByName(name);
            }
            return modelAndView.addObject("users", users);
        }
    }

    @GetMapping("/user/{id}")
    public ModelAndView showUserInfoPage(@PathVariable("id") int id) throws DataNotFoundException {
        Person user = personService.findPersonById(id);
        ModelAndView modelAndView = new ModelAndView("user_info_admin", "user", user);
        modelAndView.addObject("orders", orderService.getOrdersByPersonAndStatus(user, OrderStatus.OPEN));
        return modelAndView;
    }

    @PostMapping("/user/{id}/change_status")
    public ModelAndView unblockUser(@PathVariable("id") int id) throws DataNotFoundException {
        personService.changePersonStatus(id);
        return new ModelAndView("redirect:/admin/user/" + id);
    }

    @PostMapping("/user/{id}/add_money")
    public ModelAndView addMoneyToBalance(@PathVariable("id") int id, @Param("sum") double sum) throws DataNotFoundException {
        personService.changePersonBalance(id, sum);
        return new ModelAndView("redirect:/admin/user/" + id);
    }

    @PostMapping("/order/{id}/close")
    public ModelAndView closeOrder(@PathVariable("id") int id) throws DataNotFoundException {
        orderService.closeOrder(id);
        return new ModelAndView("redirect:/admin/user/" + orderService.getOrderById(id).getPerson().getPersonId());
    }
}
