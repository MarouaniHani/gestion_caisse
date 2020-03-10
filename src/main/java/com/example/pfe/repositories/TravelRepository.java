package com.example.pfe.repositories;

import com.example.pfe.model.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Integer> {
    List<Travel> findByEmployerRegistrationNumber(String regNum);
}
