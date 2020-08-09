package com.example.pfe.Controller;

import com.example.pfe.dto.TravelDto;
import com.example.pfe.model.Employer;
import com.example.pfe.model.Travel;
import com.example.pfe.repositories.EmployerRepository;
import com.example.pfe.repositories.TravelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/travels")
public class TravelController {
    private final EmployerRepository employerRepository;
    private final TravelRepository travelRepository;

    public TravelController(EmployerRepository employerRepository, TravelRepository travelRepository) {
        this.employerRepository = employerRepository;
        this.travelRepository = travelRepository;
    }

    @PostMapping("/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentParcAuto(#userConnectedRole)")
    public ResponseEntity<?> addTravel(@Valid @RequestBody TravelDto travelDto, @PathVariable("role") String userConnectedRole) {

        // verifier si l'employé exist ou nn
        Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(travelDto.getEmployerRegistrationNumber()));
        // si l'employé exist
        if (employer.isPresent()) {
            // creation d'un nouveau deplacement
            Travel travel = new Travel();
            double amount;
            switch (employer.get().getPostion()) {
                case EXECUTEUR:
                    amount = 10.0;
                    break;
                case MAITRISE:
                    amount = 15.0;
                    break;
                case CADRE:
                    amount = 20.0;
                    break;
                default:
                    amount = 0.0;
            }
            travel.setEmployerRegistrationNumber(travelDto.getEmployerRegistrationNumber());
            travel.setTravelAmount(amount);
            // enregister le deplacement dans la base de donnés
            travelRepository.save(travel);
            return new ResponseEntity<>(travel, HttpStatus.OK);
        }
        // message d'erreur en cas ou l'employé n'exist pas
        return new ResponseEntity<>("Employer not found !", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentParcAuto(#userConnectedRole)")
    public ResponseEntity<?> getTravelById(@PathVariable() int id, @PathVariable("role") String userConnectedRole) {
        // rechercher le deplacemnt par id de deplacemnt
        Optional<Travel> travel = travelRepository.findById(id);
        // si le deplacement exist
        if (travel.isPresent()) {
            // retourner le deplacement
            return new ResponseEntity<>(travel.get(), HttpStatus.OK);
        }
        // message d'erreur en cas ou le deplacement n'exist pas
        return new ResponseEntity<>("Travel not found !", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentParcAuto(#userConnectedRole)")
    public ResponseEntity<?> getAllTravels(@PathVariable("role") String userConnectedRole) {
        // lister tout les deplacement dans la base de donnnées
        return new ResponseEntity<>(travelRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/employer/{regNum}/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentParcAuto(#userConnectedRole)")
    public ResponseEntity<?> getAllTravelsByEmployee(@PathVariable() String regNum, @PathVariable("role") String userConnectedRole) {
        //verifier si l'employer exist
        Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(regNum));
        //s'il exist
        if (employer.isPresent()) {
            // recuperer la liste de deplacement par employer
            List<Travel> travels = travelRepository.findByEmployerRegistrationNumber(regNum);
            // retourner la liste
            return new ResponseEntity<>(travels, HttpStatus.OK);
        }
        // message d'erreur en cas ou l'employé n'exist pas
        return new ResponseEntity<>("Employer not found !", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentParcAuto(#userConnectedRole)")
    public ResponseEntity<?> deleteTravel(@PathVariable() int id, @PathVariable("role") String userConnectedRole) {
        // rechercher le deplacemnt par id de deplacemnt
        Optional<Travel> travel = travelRepository.findById(id);
        // si le deplacement exist
        if (travel.isPresent()) {
            // supprimer le deplacement
            travelRepository.delete(travel.get());
            // retourner un message de succés
            return new ResponseEntity<>("Travel deleted successfully !", HttpStatus.OK);
        }
        // message d'erreur en cas ou le deplacement n'exist pas
        return new ResponseEntity<>("Travel not found !", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentParcAuto(#userConnectedRole)")
    public ResponseEntity<?> updateTravel(@Valid @RequestBody TravelDto travelDto, @PathVariable() int id, @PathVariable("role") String userConnectedRole) {
        // rechercher le deplacemnt par id de deplacemnt
        Optional<Travel> tarvel = travelRepository.findById(id);
        // si le deplacement exst
        if (tarvel.isPresent()) {
            // mettre a jour s les données du deplacement
            tarvel.get().setEmployerRegistrationNumber(travelDto.getEmployerRegistrationNumber());
            tarvel.get().setTravelAmount(travelDto.getAmountOfTravel());
            // enregister les modification dans la base de données
            travelRepository.save(tarvel.get());
            return new ResponseEntity<>(tarvel.get(), HttpStatus.OK);
        }
        // message d'erreur en cas ou le deplacement n'exist pas
        return new ResponseEntity<>("Travel not found !", HttpStatus.NOT_FOUND);
    }

}
