package com.application.repository;

import com.application.model.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Integer> {

    List<Tariff> findByStatus(boolean status);

}
