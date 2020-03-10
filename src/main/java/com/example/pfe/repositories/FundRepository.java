package com.example.pfe.repositories;

import com.example.pfe.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FundRepository extends JpaRepository<Fund, Integer> {
    Optional<Fund> findFirstByOrderById();
}
