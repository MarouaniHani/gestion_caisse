package com.example.pfe.repositories;

import com.example.pfe.model.Caisse;
import com.example.pfe.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CaisseRepository extends JpaRepository<Caisse, Integer> {
}
