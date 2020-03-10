package com.example.pfe.Controller;


import com.example.pfe.dto.BonPourDto;
import com.example.pfe.model.BonPour;
import com.example.pfe.model.Employer;
import com.example.pfe.model.Fund;
import com.example.pfe.repositories.BonPourRepository;
import com.example.pfe.repositories.EmployerRepository;
import com.example.pfe.repositories.FundRepository;
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
    private final FundRepository fundRepository;

    public BonPourController(BonPourRepository bonPourRepository, EmployerRepository employerRepository, FundRepository fundRepository) {
        this.bonPourRepository = bonPourRepository;
        this.employerRepository = employerRepository;
        this.fundRepository = fundRepository;
    }

    @PostMapping
    public ResponseEntity<?> addBonPour(@Valid @RequestBody BonPourDto bonPourDto) {
        if (bonPourDto.getEmployerRegistrationNumber().equals("")) {
            return new ResponseEntity<>("You need to enter employer registration number !", HttpStatus.BAD_REQUEST);
        }
        if (bonPourDto.getWording().equals("")) {
            return new ResponseEntity<>("You need to enter wording !", HttpStatus.BAD_REQUEST);
        }
        if (bonPourDto.getAmount() == 0) {
            return new ResponseEntity<>("You need to enter price !", HttpStatus.BAD_REQUEST);
        }
        Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(bonPourDto.getEmployerRegistrationNumber()));
        if (employer.isPresent()) {
            LocalDate date = LocalDate.now();
            BonPour bonPour = new BonPour();
            bonPour.setEmployerRegistrationNumber(bonPourDto.getEmployerRegistrationNumber());
            bonPour.setDate(date.toString());
            bonPour.setWording(bonPourDto.getWording());
            bonPour.setAmount(bonPourDto.getAmount());
            bonPour.setEnInstance(true);
            bonPourRepository.save(bonPour);
            return new ResponseEntity<>(bonPour, HttpStatus.OK);
        }
        return new ResponseEntity<>("Employer not found ! check registration number ", HttpStatus.NOT_FOUND);

    }

    @GetMapping
    public ResponseEntity<List<BonPour>> getAllBonPour() {
        List<BonPour> bonPourList = bonPourRepository.findAll();
        return new ResponseEntity<>(bonPourList, HttpStatus.OK);
    }

    @GetMapping("/registration-number/{id}")
    public ResponseEntity<?> getListBonPourByRegistrationNumber(@PathVariable() String id) {
        Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(id));
        if (employer.isPresent()) {
            List<BonPour> bonPourList = bonPourRepository.findAllByEmployerRegistrationNumber(id);
            return new ResponseEntity<>(bonPourList, HttpStatus.OK);
        }
        return new ResponseEntity<>("Employer not found ! check registration number ", HttpStatus.NOT_FOUND);
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
            if (!bonPour.get().isEnInstance()) {
                return new ResponseEntity<>("BonPour is already payed !", HttpStatus.OK);
            }
            Optional<Fund> fund = fundRepository.findFirstByOrderById();
            if (fund.isEmpty()) {
                return new ResponseEntity<>("Caisse not found !", HttpStatus.NOT_FOUND);
            }
            // todo : verify sold of caisse cases
            if (fund.get().getSold() < bonPour.get().getAmount()) {
                fund.get().setSold(1500);
            }
            if (fund.get().getSold() < bonPour.get().getAmount()) {
                return new ResponseEntity<>("Insufficient fund amount", HttpStatus.BAD_REQUEST);
            }
            fund.get().setSold(fund.get().getSold() - bonPour.get().getAmount());
            bonPour.get().setEnInstance(false);
            bonPourRepository.save(bonPour.get());
            fundRepository.save(fund.get());
            return new ResponseEntity<>("BonPour validated successfully !", HttpStatus.OK);
        }
        return new ResponseEntity<>("BonPour not found !", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBonPour(@PathVariable() int id, @Valid @RequestBody BonPourDto bonPourDto) {
        Optional<BonPour> bonPour = bonPourRepository.findById(id);
        if (bonPour.isPresent()) {
            if (!bonPourDto.getEmployerRegistrationNumber().equals("")) {
                Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(bonPourDto.getEmployerRegistrationNumber()));
                if (employer.isPresent()) {
                    bonPour.get().setEmployerRegistrationNumber(bonPourDto.getEmployerRegistrationNumber());
                    if (!bonPourDto.getWording().equals("")) {
                        bonPour.get().setWording(bonPourDto.getWording());
                    }
                    if (bonPourDto.getAmount() != 0) {
                        bonPour.get().setAmount(bonPourDto.getAmount());
                    }
                    bonPourRepository.save(bonPour.get());
                    return new ResponseEntity<>("BonPour updated successfully !", HttpStatus.OK);
                }
                return new ResponseEntity<>("Employer not found ! verify registration number", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("BonPour not found !", HttpStatus.NOT_FOUND);
    }
}
