package com.example.pfe.repositories;

import com.example.pfe.model.Employer;
import com.example.pfe.model.PaymentDemand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PaymentDemandRepository extends JpaRepository<PaymentDemand, Integer> {
}
