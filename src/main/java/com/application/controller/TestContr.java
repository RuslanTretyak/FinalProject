package com.application.controller;

import com.application.model.Bike;
import com.application.model.Person;
import com.application.repository.BikeRepository;
import com.application.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class TestContr {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    BikeRepository bikeRepository;
    @Autowired

    @GetMapping("/test")
    public void test(){
        Person person = new Person();
        person.setName("dsfhg");
        person.setDateOFBirth(new Date());
        personRepository.save(person);
        Bike bike = new Bike();
        bike.setSerialNumber("lskubnghiu");
        bikeRepository.save(bike);

    }

}
