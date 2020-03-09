package com.example.pfe.repositories;

import com.example.pfe.model.Assurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssuranceRepository extends JpaRepository<Assurance, Integer> {
    List<Assurance> findByEmployerMatricule(String matricule);
    List<Assurance> findByEmployerMatriculeAndEtat(String matricule, Assurance.Etat etat);
}
