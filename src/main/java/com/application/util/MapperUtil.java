package com.application.util;

import com.application.model.dto.PersonDTO;
import com.application.model.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {
    public Person mapToPersonEntity(PersonDTO personDTO) {
        Person person = new Person();
        person.setLogin(personDTO.getLogin());
        person.setName(personDTO.getName());
        person.setSurname(personDTO.getSurname());
        person.setDateOfBirth(personDTO.getDateOfBirth());
        person.setEmail(personDTO.getEmail());
        person.setPassword(personDTO.getPassword());
        return person;
    }

    public PersonDTO mapToPersonDTOEntity(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setLogin(person.getLogin());
        personDTO.setName(person.getName());
        personDTO.setSurname(person.getSurname());
        personDTO.setDateOfBirth(person.getDateOfBirth());
        personDTO.setEmail(person.getEmail());
        personDTO.setPassword("00000000");
        personDTO.setVerificationPassword("00000000");
        return personDTO;
    }

}
