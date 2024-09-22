package com.application.controller;

import com.application.constant.BikeStatus;
import com.application.model.entity.Bike;
import com.application.model.entity.ParkingPoint;
import com.application.service.BikeService;
import com.application.service.ParkingPointBikeMapService;
import com.application.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private BikeService bikeService;
    private ParkingService parkingService;
    private ParkingPointBikeMapService parkingPointBikeMapService;

    @Autowired
    public AdminController(BikeService bikeService, ParkingService parkingService, ParkingPointBikeMapService parkingPointBikeMapService) {
        this.bikeService = bikeService;
        this.parkingService = parkingService;
        this.parkingPointBikeMapService = parkingPointBikeMapService;
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
    public ModelAndView addBike(@Valid @ModelAttribute Bike bike, BindingResult result){
        if (result.hasErrors()) {
            return new ModelAndView("add_bike", "statuses", BikeStatus.values());
        } else {
            bikeService.addNewBike(bike);
            return new ModelAndView("redirect:/admin/bike");
        }
    }
    @GetMapping("/bike/{id}")
    public ModelAndView showBikeInfo(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("bike_info", "bike", bikeService.getBikeById(id));
        modelAndView.addObject("statuses", BikeStatus.values());
        return modelAndView;
    }
    @PostMapping("/bike")
    public ModelAndView changeBikeStatus(@ModelAttribute Bike bike) {
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
    public ModelAndView showParkingInfo(@PathVariable int id) {
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
    public ModelAndView addParking(@Valid @ModelAttribute ParkingPoint parkingPoint, BindingResult result){
        if (result.hasErrors()) {
            return new ModelAndView("add_parking");
        } else {
            parkingService.addNewParking(parkingPoint);
            return new ModelAndView("redirect:/admin/parking");
        }
    }
    @PostMapping("/parking/add_bike/{bikeId}/{parkingId}")
    public String addBikeToParking(@PathVariable int bikeId, @PathVariable int parkingId){
        parkingPointBikeMapService.addBikeToParking(parkingId, bikeId);
        return "redirect:/admin/parking/" + parkingId;
    }
    @PostMapping("/parking/remove_bike/{bikeId}/{parkingId}")
    public String removeBikeFromParking(@PathVariable int bikeId, @PathVariable int parkingId){
        parkingPointBikeMapService.removeBikeFromParking(bikeId);
        return "redirect:/admin/parking/" + parkingId;
    }

}
