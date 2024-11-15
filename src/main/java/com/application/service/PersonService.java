package com.application.service;

import com.application.exception.DataNotFoundException;
import com.application.model.dto.PersonDTO;
import com.application.model.entity.Person;
import com.application.repository.PersonRepository;
import com.application.repository.PersonRoleRepository;
import com.application.util.MapperUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;
    private PersonRoleRepository personRoleRepository;
    private MapperUtil personDTOMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, PersonRoleRepository personRoleRepository, MapperUtil personDTOMapper, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.personRoleRepository = personRoleRepository;
        this.personDTOMapper = personDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerPerson(PersonDTO personDTO) {
        personDTO.setPassword(passwordEncoder.encode(personDTO.getPassword()));
        Person person = personDTOMapper.mapToPersonEntity(personDTO);
        person.setStatus(true);
        person.setPersonRole(personRoleRepository.findByPersonRoleName("ROLE_USER"));
        personRepository.save(person);
    }

    public Person findPersonById(int id) throws DataNotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Person with id " + id + " was not found"));
    }

    public Person findPersonByLogin(String login) throws DataNotFoundException {
        return personRepository.findByLogin(login).orElseThrow(() -> new DataNotFoundException("Person with login " + login + " was not found"));
    }

    public List<Person> findPersonBySurname(String surname) {
        return personRepository.findBySurname(surname);
    }

    public List<Person> findPersonByName(String name) {
        return personRepository.findByName(name);
    }

    public List<Person> findPersonBySurnameAndName(String surname, String name) {
        return personRepository.findByNameAndSurname(name, surname);
    }

    @Transactional
    public void changePerson(PersonDTO personDTO, Person person) {
        person.setName(personDTO.getName());
        person.setSurname(personDTO.getSurname());
        person.setEmail(personDTO.getEmail());
        person.setDateOfBirth(personDTO.getDateOfBirth());
        personRepository.save(person);

    }

    @Transactional
    public void changePersonStatus(int id) throws DataNotFoundException {
        Person person = personRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Person with id " + id + " was not found"));
        person.setStatus(!person.isStatus());
    }

    @Transactional
    public void changePersonBalance(int id, double sum) throws DataNotFoundException {
        Person person = personRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Person with id " + id + " was not found"));
        person.setBalance(person.getBalance() + sum);
    }
}
