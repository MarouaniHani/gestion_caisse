package com.example.pfe.repositories;

import com.example.pfe.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer, Integer> {
    Optional<Employer> findByRegistrationNumber(int regNum);
    Optional<Employer> findByRegistrationNumberAndPassword(int regNum,String mdp);
}
