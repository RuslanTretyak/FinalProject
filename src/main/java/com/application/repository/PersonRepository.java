package com.application.repository;

import com.application.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByLogin(String login);

    Optional<Person> findByEmail(String email);

    List<Person> findBySurname(String surname);

    List<Person> findByName(String name);

    List<Person> findByNameAndSurname(String name, String surname);
}
