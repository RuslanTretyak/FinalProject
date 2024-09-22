package com.application.controller;

import com.application.model.dto.PersonDTO;
import com.application.model.entity.Person;
import com.application.service.PersonService;
import com.application.util.MapperUtil;
import com.application.util.PersonChangeValidator;
import com.application.util.PersonValidator;
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

    @Autowired
    public UserController(PersonService personService, MapperUtil mapperUtil, PersonChangeValidator personChangeValidator) {
        this.personService = personService;
        this.mapperUtil = mapperUtil;
        this.personChangeValidator = personChangeValidator;
    }


    @GetMapping("/my_info")
    public ModelAndView getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        Person person = personService.getPersonByLogin(userDetails.getUsername());
        return new ModelAndView("person_info", "person", person);

    }
    @GetMapping("/change")
    public ModelAndView changePersonData(@AuthenticationPrincipal UserDetails userDetails) {
        Person person = personService.getPersonByLogin(userDetails.getUsername());
        PersonDTO personDTO = mapperUtil.mapToPersonDTOEntity(person);
        return new ModelAndView("change_person", "personDTO", personDTO);
    }
    @PostMapping("/change")
    public ModelAndView registerPerson(@AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute PersonDTO personDTO, BindingResult result){
        Person person = personService.getPersonByLogin(userDetails.getUsername());
        personChangeValidator.validate(personDTO, result);
        if (result.hasErrors()) {
            return new ModelAndView("change_person", "personDTO", personDTO);
        } else {
            personService.changePerson(personDTO, person);
            return new ModelAndView("redirect:/user/my_info");
        }
    }
}
