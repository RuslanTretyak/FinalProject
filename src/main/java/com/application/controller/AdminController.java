package com.application.controller;

import com.application.constant.BikeStatus;
import com.application.constant.OrderStatus;
import com.application.exception.DataNotFoundException;
import com.application.model.entity.Bike;
import com.application.model.entity.Order;
import com.application.model.entity.ParkingPoint;
import com.application.model.entity.Person;
import com.application.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
    private TariffService tariffService;

    @Autowired
    public AdminController(BikeService bikeService, ParkingService parkingService, ParkingPointBikeMapService parkingPointBikeMapService, PersonService personService, OrderService orderService, TariffService tariffService) {
        this.bikeService = bikeService;
        this.parkingService = parkingService;
        this.parkingPointBikeMapService = parkingPointBikeMapService;
        this.personService = personService;
        this.orderService = orderService;
        this.tariffService = tariffService;
    }

    @GetMapping("/bikes/{page}")
    public ModelAndView showAllBikes(@PathVariable("page") int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, 5, Sort.by("status"));
        Page<Bike> page = bikeService.getAllBikes(pageable);
        ModelAndView modelAndView = new ModelAndView("bikes", "bikes", page.getContent());
        modelAndView.addObject("numberPages", page.getTotalPages());
        return modelAndView;

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
            return new ModelAndView("redirect:/admin/bikes/1");
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
                try {
                    Person user = personService.findPersonByLogin(login);
                    users = Collections.singletonList(user);
                } catch (DataNotFoundException e) {
                    return modelAndView;
                }
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
        modelAndView
                .addObject("orders", orderService.getOrdersByPersonAndStatus(user, OrderStatus.OPEN))
                .addObject("sum", 0);
        return modelAndView;
    }

    @PostMapping("/user/{id}/change_status")
    public ModelAndView unblockUser(@PathVariable("id") int id) throws DataNotFoundException {
        personService.changePersonStatus(id);
        return new ModelAndView("redirect:/admin/user/" + id);
    }

    @PostMapping("/user/{id}/add_money")
    public ModelAndView addMoneyToBalance(@PathVariable("id") int id, @Param("sum") double sum) throws DataNotFoundException {
        if (sum <= 0) {
            Person user = personService.findPersonById(id);
            ModelAndView modelAndView = new ModelAndView("user_info_admin", "user", user);
            modelAndView
                    .addObject("orders", orderService.getOrdersByPersonAndStatus(user, OrderStatus.OPEN))
                    .addObject("sum", sum)
                    .addObject("error", "incorrect input");
            return modelAndView;
        }
        personService.changePersonBalance(id, sum);
        return new ModelAndView("redirect:/admin/user/" + id);
    }
    @GetMapping("/order/{id}")
    public ModelAndView showOrderInfo(@PathVariable("id") int id) throws DataNotFoundException {
        return new ModelAndView("order", "order", orderService.getOrderById(id));
    }
    @PostMapping("/order/{id}/close")
    public ModelAndView closeOrder(@PathVariable("id") int id, @Param("page") String page) throws DataNotFoundException {
        orderService.closeOrder(id);
        if (page != null){
            if (page.equals("toOrder")){
                return new ModelAndView("redirect:/admin/order/" + id);
            }
        }
        return new ModelAndView("redirect:/admin/user/" + orderService.getOrderById(id).getPerson().getPersonId());
    }
    @GetMapping("/user/{userId}/order_history/{page}")
    public ModelAndView getUserOrderHistory(@PathVariable("userId") int userId, @PathVariable("page") int currentPage) throws DataNotFoundException {
        Person person = personService.findPersonById(userId);
        Pageable pageable = PageRequest.of(currentPage - 1, 5, Sort.by("dateOfBegin").descending());
        Page<Order> page= orderService.getOrdersByPerson(person, pageable);
        ModelAndView modelAndView = new ModelAndView("user_order_history_for_admin", "orders", page.getContent());
        modelAndView.addObject("numberPages", page.getTotalPages());
        return modelAndView;
    }
    @GetMapping("/order_history/{page}")
    public ModelAndView getAllOrderHistory(@PathVariable("page") int currentPage, @RequestParam(name = "sorting", defaultValue = "1") int sorting) throws DataNotFoundException {
        Sort sort = switch (sorting) {
            case (2) -> Sort.by("endDate").descending();
            case (3) -> Sort.by("status").descending().and(Sort.by("endDate").descending());
            default -> Sort.by("dateOfBegin").descending();
        };
        Pageable pageable = PageRequest.of(currentPage - 1, 5, sort);
        Page<Order> page= orderService.getAllOrders(pageable);
        ModelAndView modelAndView = new ModelAndView("all_order_history_for_admin", "orders", page.getContent());
        modelAndView.addObject("numberPages", page.getTotalPages()).addObject("sorting", sorting);
        System.out.println(sort);
        return modelAndView;
    }
    @GetMapping("/tariff")
    public ModelAndView showChangeTariffPage() throws DataNotFoundException {
        return new ModelAndView("change_tariff", "currentTariff", tariffService.getCurrentTariff().getValue())
                .addObject("newTariff", 0);
    }
    @PostMapping("/tariff")
    public ModelAndView changeTariff(@Param("newTariff") double newTariff) throws DataNotFoundException {
        if (newTariff <= 0) {
            ModelAndView modelAndView = new ModelAndView("change_tariff", "currentTariff", tariffService.getCurrentTariff().getValue());
            modelAndView
                    .addObject("newTariff", newTariff)
                    .addObject("error", "incorrect input");
            return modelAndView;
        }
        tariffService.changeTariff(newTariff);
        return new ModelAndView("redirect:/admin/tariff");
    }
}
