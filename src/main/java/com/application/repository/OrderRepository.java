package com.application.repository;

import com.application.model.entity.Order;
import com.application.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "select * from bike_order where person = :person order by date_of_begin desc", nativeQuery = true)
    List<Order> findBikeOrderByPerson(@Param("person") int personId);

    List<Order> findBikeOrderByPersonAndStatus(Person person, String status);
}
