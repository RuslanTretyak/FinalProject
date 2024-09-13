package com.application.service;

import com.application.model.dto.PersonDTO;
import com.application.model.entity.Person;
import com.application.repository.PersonRepository;
import com.application.repository.PersonRoleRepository;
import com.application.util.MapperUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void registerPerson(PersonDTO personDTO){
        personDTO.setPassword(passwordEncoder.encode(personDTO.getPassword()));
        Person person = personDTOMapper.mapToPersonEntity(new Person(), personDTO);
        person.setStatus(true);
        person.setPersonRole(personRoleRepository.findByPersonRoleName("ROLE_USER"));
        personRepository.save(person);
    }
}
