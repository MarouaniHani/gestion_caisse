package com.example.pfe.Controller;


import com.example.pfe.dto.BonPourDto;
import com.example.pfe.model.BonPour;
import com.example.pfe.model.Employer;
import com.example.pfe.repositories.BonPourRepository;
import com.example.pfe.repositories.EmployerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/bon-pour")
public class BonPourController {

    private final BonPourRepository bonPourRepository;
    private final EmployerRepository employerRepository;

    public BonPourController(BonPourRepository bonPourRepository, EmployerRepository employerRepository) {
        this.bonPourRepository = bonPourRepository;
        this.employerRepository = employerRepository;
    }

    @PostMapping
    public ResponseEntity<?> addBonPour(@Valid @RequestBody BonPourDto bonPourDto) {
        if (bonPourDto.getEmployerMatricule().equals("")) {
            return new ResponseEntity<>("You need to enter employer matricule !", HttpStatus.BAD_REQUEST);
        }
        if (bonPourDto.getLibelle().equals("")) {
            return new ResponseEntity<>("You need to enter libelle !", HttpStatus.BAD_REQUEST);
        }
        if (bonPourDto.getMontant() == 0) {
            return new ResponseEntity<>("You need to enter price !", HttpStatus.BAD_REQUEST);
        }
        Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(bonPourDto.getEmployerMatricule()));
        if (employer.isPresent()) {
            LocalDate date = LocalDate.now();
            BonPour bonPour = new BonPour();
            bonPour.setEmployerMatricule(bonPourDto.getEmployerMatricule());
            bonPour.setDate(date.toString());
            bonPour.setLibelle(bonPourDto.getLibelle());
            bonPour.setMontant(bonPourDto.getMontant());
            bonPour.setEnInstance(true);
            bonPourRepository.save(bonPour);
            return new ResponseEntity<>(bonPour, HttpStatus.OK);
        }
        return new ResponseEntity<>("Employer not found ! check matricule ", HttpStatus.NOT_FOUND);

    }

    @GetMapping
    public ResponseEntity<List<BonPour>> getAllBonPour() {
        List<BonPour> bonPourList = bonPourRepository.findAll();
        return new ResponseEntity<>(bonPourList, HttpStatus.OK);
    }

    @GetMapping("/matricule/{id}")
    public ResponseEntity<?> getListBonPourByMatriclue(@PathVariable() String id) {
        Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(id));
        if (employer.isPresent()) {
            List<BonPour> bonPourList = bonPourRepository.findAllByEmployerMatricule(id);
            return new ResponseEntity<>(bonPourList, HttpStatus.OK);
        }
        return new ResponseEntity<>("Employer not found ! check matricule ", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBonPourById(@PathVariable() int id) {
        Optional<BonPour> bonPour = bonPourRepository.findById(id);
        if (bonPour.isPresent()) {
            return new ResponseEntity<>(bonPour.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("BonPour not found !", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBonPourById(@PathVariable() int id) {
        Optional<BonPour> bonPour = bonPourRepository.findById(id);
        if (bonPour.isPresent()) {
            bonPourRepository.delete(bonPour.get());
            return new ResponseEntity<>("BonPour deleted successfully !", HttpStatus.OK);
        }
        return new ResponseEntity<>("BonPour not found !", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/validate/{id}")
    public ResponseEntity<?> validateBonPour(@PathVariable() int id) {
        Optional<BonPour> bonPour = bonPourRepository.findById(id);
        if (bonPour.isPresent()) {
            // todo : decrementer la caisse
            bonPour.get().setEnInstance(false);
            return new ResponseEntity<>("BonPour validated successfully !", HttpStatus.OK);
        }
        return new ResponseEntity<>("BonPour not found !", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBonPour(@PathVariable() int id, @Valid @RequestBody BonPourDto bonPourDto) {
        Optional<BonPour> bonPour = bonPourRepository.findById(id);
        if (bonPour.isPresent()) {
            if (!bonPourDto.getEmployerMatricule().equals("")) {
                Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(bonPourDto.getEmployerMatricule()));
                if (employer.isPresent()) {
                    bonPour.get().setEmployerMatricule(bonPourDto.getEmployerMatricule());
                    if (!bonPourDto.getLibelle().equals("")) {
                        bonPour.get().setLibelle(bonPourDto.getLibelle());
                    }
                    if (bonPourDto.getMontant() != 0) {
                        bonPour.get().setMontant(bonPourDto.getMontant());
                    }
                    bonPourRepository.save(bonPour.get());
                    return new ResponseEntity<>("BonPour updated successfully !", HttpStatus.OK);
                }
                return new ResponseEntity<>("Employer not found ! verify matriclue", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("BonPour not found !", HttpStatus.NOT_FOUND);
    }
}
