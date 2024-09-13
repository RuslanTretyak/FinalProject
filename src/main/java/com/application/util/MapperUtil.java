package com.application.util;

import com.application.model.dto.PersonDTO;
import com.application.model.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {
    public Person mapToPersonEntity(Person person, PersonDTO personDTO) {
        person.setLogin(personDTO.getLogin());
        person.setName(personDTO.getName());
        person.setSurname(personDTO.getSurname());
        person.setDateOFBirth(personDTO.getDateOfBirth());
        person.setEmail(personDTO.getEmail());
        person.setPassword(personDTO.getPassword());
        return person;
    }

}
