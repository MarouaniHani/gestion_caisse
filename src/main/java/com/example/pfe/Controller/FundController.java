package com.example.pfe.Controller;


import com.example.pfe.model.Fund;
import com.example.pfe.repositories.FundRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/funds")
public class FundController {

    private final FundRepository fundRepository;

    public FundController(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    @PostMapping
    public ResponseEntity<?> createFund() {
        List<Fund> funds = fundRepository.findAll();
        if (funds.isEmpty()) {
            Fund fund = new Fund();
            fund.setSold(1500);
            fundRepository.save(fund);
            return new ResponseEntity<>(fund, HttpStatus.OK);
        } else return new ResponseEntity<>("Fund already exist !", HttpStatus.BAD_REQUEST);
    }
}
