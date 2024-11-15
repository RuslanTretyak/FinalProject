package com.application.controller;

import com.application.model.dto.PersonDTO;
import com.application.service.PersonService;
import com.application.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {
    private PersonService personService;
    private PersonValidator personValidator;


    @Autowired
    public RegisterController(PersonService personService, PersonValidator personValidator, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage(@ModelAttribute PersonDTO personDTO) {
        return new ModelAndView("register_person");
    }

    @PostMapping("/register")
    public ModelAndView registerPerson(@Valid @ModelAttribute PersonDTO personDTO, BindingResult result) {
        personValidator.validate(personDTO, result);
        if (result.hasErrors()) {
            return new ModelAndView("register_person", "person", personDTO);
        } else {
            personService.registerPerson(personDTO);
            return new ModelAndView("redirect:/auth/home");
        }
    }

}
