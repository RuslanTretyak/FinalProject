package com.application.repository;

import com.application.model.entity.Order;
import com.application.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findBikeOrderByPerson(Person person, Pageable pageable);

    List<Order> findBikeOrderByPersonAndStatus(Person person, String status);
}
