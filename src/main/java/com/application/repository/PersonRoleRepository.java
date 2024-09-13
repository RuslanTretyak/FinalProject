package com.application.repository;

import com.application.model.entity.PersonRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRoleRepository extends JpaRepository<PersonRole, Integer> {
    PersonRole findByPersonRoleName(String personRoleName);
}
