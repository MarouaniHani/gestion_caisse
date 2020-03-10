package com.example.pfe.Controller;


import com.example.pfe.model.Caisse;
import com.example.pfe.repositories.CaisseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/caisses")
public class CaisseController {

    private final CaisseRepository caisseRepository;

    public CaisseController(CaisseRepository caisseRepository) {
        this.caisseRepository = caisseRepository;
    }

    @PostMapping
    public ResponseEntity<?> createCaisse() {
        List<Caisse> caisses = caisseRepository.findAll();
        if (caisses.isEmpty()) {
            Caisse caisse = new Caisse();
            caisse.setSolde(1500);
            caisseRepository.save(caisse);
            return new ResponseEntity<>(caisse, HttpStatus.OK);
        } else return new ResponseEntity<>("Caisse already exist !", HttpStatus.BAD_REQUEST);
    }
}
