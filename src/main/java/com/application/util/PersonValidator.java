package com.application.util;

import com.application.model.dto.PersonDTO;
import com.application.model.entity.Person;
import com.application.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class PersonValidator implements Validator {
    private PersonRepository personRepository;

    @Autowired
    public PersonValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonDTO personDTO = (PersonDTO) target;
        if(personRepository.findByLogin(personDTO.getLogin()).isPresent()){
            errors.rejectValue("login", "", "This login is already exist");
        }
        if(personRepository.findByEmail(personDTO.getEmail()).isPresent()){
            errors.rejectValue("email", "", "This email is already exist");
        }
        if(!personDTO.getPassword().equals(personDTO.getVerificationPassword())){
            errors.rejectValue("verificationPassword", "", "verification password does not match");
        }
        if(personDTO.getDateOfBirth() != null){
            if(personDTO.getDateOfBirth().after(new Date())){
                errors.rejectValue("dateOfBirth", "", "Date Of Birth is not correct");
            }
        }

    }
}
