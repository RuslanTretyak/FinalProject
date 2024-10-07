package com.application.controller;

import com.application.exception.DataNotFoundException;
import com.application.model.dto.OrderDTO;
import com.application.model.dto.PersonDTO;
import com.application.model.entity.Person;
import com.application.service.BikeService;
import com.application.service.OrderService;
import com.application.service.ParkingService;
import com.application.service.PersonService;
import com.application.util.MapperUtil;
import com.application.util.PersonChangeValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    private PersonService personService;
    private MapperUtil mapperUtil;
    private PersonChangeValidator personChangeValidator;
    private ParkingService parkingService;
    private BikeService bikeService;
    private OrderService orderService;

    @Autowired
    public UserController(PersonService personService, MapperUtil mapperUtil, PersonChangeValidator personChangeValidator, ParkingService parkingService, BikeService bikeService, OrderService orderService) {
        this.personService = personService;
        this.mapperUtil = mapperUtil;
        this.personChangeValidator = personChangeValidator;
        this.parkingService = parkingService;
        this.bikeService = bikeService;
        this.orderService = orderService;
    }


    @GetMapping("/my_info")
    public ModelAndView getMyInfo(@AuthenticationPrincipal UserDetails userDetails) throws DataNotFoundException {
        Person person = personService.findPersonByLogin(userDetails.getUsername());
        return new ModelAndView("person_info", "person", person);

    }

    @GetMapping("/change")
    public ModelAndView changePersonData(@AuthenticationPrincipal UserDetails userDetails) throws DataNotFoundException {
        Person person = personService.findPersonByLogin(userDetails.getUsername());
        PersonDTO personDTO = mapperUtil.mapToPersonDTOEntity(person);
        return new ModelAndView("change_person", "personDTO", personDTO);
    }

    @PostMapping("/change")
    public ModelAndView registerPerson(@AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute PersonDTO personDTO, BindingResult result) throws DataNotFoundException {
        Person person = personService.findPersonByLogin(userDetails.getUsername());
        personChangeValidator.validate(personDTO, result);
        if (result.hasErrors()) {
            return new ModelAndView("change_person", "personDTO", personDTO);
        } else {
            personService.changePerson(personDTO, person);
            return new ModelAndView("redirect:/user/my_info");
        }
    }

    @PostMapping("/make_order")
    public ModelAndView createOrder(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("order") OrderDTO orderDTO) throws DataNotFoundException {
        Person person = personService.findPersonByLogin(userDetails.getUsername());
        if (orderDTO.getParkingPointId() == 0) {
            return new ModelAndView("choose_parking_to_order", "parking", parkingService.getAllParking());
        } else if (orderDTO.getBikeId() == 0) {
            return new ModelAndView("choose_bike_to_order", "bikes", bikeService.getBikeFromParking(orderDTO.getParkingPointId()));
        } else if (orderDTO.getTerm() == 0) {
            return new ModelAndView("choose_term_to_order");
        } else {
            orderDTO.setPerson(person);
            orderService.createOrder(orderDTO);
            return new ModelAndView("redirect:/auth/home");
        }
    }

    @GetMapping("/order_history")
    public ModelAndView getOrderHistory(@AuthenticationPrincipal UserDetails userDetails) throws DataNotFoundException {
        Person person = personService.findPersonByLogin(userDetails.getUsername());
        return new ModelAndView("order_history", "orders", orderService.getOrdersByPerson(person));
    }
}
