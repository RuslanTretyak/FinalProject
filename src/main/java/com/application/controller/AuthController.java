package com.application.controller;

import com.application.constant.OrderStatus;
import com.application.model.entity.Person;
import com.application.service.OrderService;
import com.application.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private PersonService personService;
    private OrderService orderService;

    @Autowired
    public AuthController(PersonService personService, OrderService orderService) {
        this.personService = personService;
        this.orderService = orderService;
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }
    @GetMapping("/home")
    public ModelAndView showHomePage(@AuthenticationPrincipal UserDetails userDetails){
        Person person = personService.getPersonByLogin(userDetails.getUsername());
        ModelAndView modelAndView = new ModelAndView("home", "person", person);
        modelAndView.addObject("orders", orderService.getOrdersByPersonAndStatus(person, OrderStatus.OPEN));
        return modelAndView;
    }
}
