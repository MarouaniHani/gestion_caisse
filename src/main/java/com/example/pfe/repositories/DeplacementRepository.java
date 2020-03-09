package com.example.pfe.repositories;

import com.example.pfe.model.Deplacement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeplacementRepository extends JpaRepository<Deplacement, Integer> {
    List<Deplacement> findByEmployerMatricule(String matricule);
}
