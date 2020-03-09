package com.example.pfe.repositories;

import com.example.pfe.model.BonPour;
import com.example.pfe.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BonPourRepository extends JpaRepository<BonPour, Integer> {
    List<BonPour> findAllByEmployerMatricule(String id);
}
