package com.example.pfe.Controller;

import com.example.pfe.dto.AssuranceDto;
import com.example.pfe.model.Assurance;
import com.example.pfe.model.Employer;
import com.example.pfe.model.Fund;
import com.example.pfe.repositories.AssuranceRepository;
import com.example.pfe.repositories.EmployerRepository;
import com.example.pfe.repositories.FundRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/assurances")
public class AssuranceController {

    private final AssuranceRepository assuranceRepository;
    private final EmployerRepository employerRepository;
    private final FundRepository fundRepository;

    public AssuranceController(AssuranceRepository assuranceRepository, EmployerRepository employerRepository, FundRepository fundRepository) {
        this.assuranceRepository = assuranceRepository;
        this.employerRepository = employerRepository;
        this.fundRepository = fundRepository;
    }

    @PostMapping("/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentServiceSocial(#userConnectedRole)")
    ResponseEntity<?> addAssurance(@Valid @RequestBody AssuranceDto assuranceDto, @PathVariable("role") String userConnectedRole) {
        // verifier si l'emploer exist
        Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(assuranceDto.getEmployerRegistrationNumber()));
        // s'il exist
        if (employer.isPresent()) {
            // creation d'une nouvelle assurance
            Assurance assurance = new Assurance();
            // la date de creation
            LocalDate date = LocalDate.now();
            // persister les données
            assurance.setEmployerRegistrationNumber(assuranceDto.getEmployerRegistrationNumber());
            assurance.setNumCareBulletin(assuranceDto.getNumCareBulletin());
            assurance.setAmount(assuranceDto.getAmount());
            assurance.setDate(date.toString());
            // etat de l'assurance par defaut non payé
            assurance.setStatus(Assurance.Etat.NOT_PAYED);
            // enregistrer l'assurance dans la base de données
            assuranceRepository.save(assurance);
            return new ResponseEntity<>(assurance, HttpStatus.OK);
        }
        // messsage d'erreur en cas ou l'employé n'exist pas
        return new ResponseEntity<>("Employer not found ! please check registration number ", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentServiceSocial(#userConnectedRole)")
    ResponseEntity<?> getAllAssurances(@PathVariable("role") String userConnectedRole) {
        // lister tout les assurances de la base de données
        List<Assurance> assurances = assuranceRepository.findAll();
        return new ResponseEntity<>(assurances, HttpStatus.OK);
    }

    @GetMapping("/{id}/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentServiceSocial(#userConnectedRole)")
    ResponseEntity<?> getAssuranceById(@PathVariable() int id, @PathVariable("role") String userConnectedRole) {
        // rechercher l'assurance par id
        Optional<Assurance> assurance = assuranceRepository.findById(id);
        // si l'asssurance exist
        if (assurance.isPresent()) {
            // retourner l'assurance
            return new ResponseEntity<>(assurance.get(), HttpStatus.OK);
        }
        //message d'erreur en cas ou l'assurance n'exist pas
        return new ResponseEntity<>("Assurance not found !", HttpStatus.OK);
    }

    @GetMapping("/registration-number/{id}/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentServiceSocial(#userConnectedRole)")
    ResponseEntity<?> getAssurancesByRegistrationNumber(@PathVariable() String id, @PathVariable("role") String userConnectedRole) {
        // verifier si l'employé exist
        Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(id));
        // s'il exist
        if (employer.isPresent()) {
            // lister les assurances par matricule
            List<Assurance> assurances = assuranceRepository.findByEmployerRegistrationNumber(id);
            return new ResponseEntity<>(assurances, HttpStatus.OK);
        }
        // message d'erreur en cas ou l'employé n'exist pas
        return new ResponseEntity<>("Employer not found !", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/registration-number/{id}/not-payed/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentServiceSocial(#userConnectedRole)")
    ResponseEntity<?> getAssurancesNotPayedByRegistrationNumber(@PathVariable() String id, @PathVariable("role") String userConnectedRole) {
        // verifier si l'employé exist
        Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(id));
        // s'il exist
        if (employer.isPresent()) {
            // lister les assurances par matricule
            List<Assurance> assurances = assuranceRepository.findByEmployerRegistrationNumberAndStatus(id, Assurance.Etat.NOT_PAYED);
            return new ResponseEntity<>(assurances, HttpStatus.OK);
        }
        // message d'erreur en cas ou l'employé n'exist pas
        return new ResponseEntity<>("Employer not found !", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/registration-number/{id}/payed/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentServiceSocial(#userConnectedRole)")
    ResponseEntity<?> getAssurancesPayedByRegistrationNumber(@PathVariable() String id, @PathVariable("role") String userConnectedRole) {
        // verifier si l'employé exist
        Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(id));
        // s'il exist
        if (employer.isPresent()) {
            // lister les assurances par matricule
            List<Assurance> assurances = assuranceRepository.findByEmployerRegistrationNumberAndStatus(id, Assurance.Etat.PAYED);
            return new ResponseEntity<>(assurances, HttpStatus.OK);
        }
        // message d'erreur en cas ou l'employé n'exist pas
        return new ResponseEntity<>("Employer not found !", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentServiceSocial(#userConnectedRole)")
    ResponseEntity<?> deleteAssurance(@PathVariable() int id, @PathVariable("role") String userConnectedRole) {
        // rechercher l'assurance par id
        Optional<Assurance> assurance = assuranceRepository.findById(id);
        // s'il exist
        if (assurance.isPresent()) {
            // supprimer assurance
            assuranceRepository.delete(assurance.get());
            return new ResponseEntity<>("Assurance deleted successfully !", HttpStatus.OK);
        }
        //message d'erreur en cas ou l'assurance n'exist pas
        return new ResponseEntity<>("Assurance not found !", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentServiceSocial(#userConnectedRole)")
    ResponseEntity<?> updateAssurance(@PathVariable() int id, @Valid @RequestBody AssuranceDto assuranceDto, @PathVariable("role") String userConnectedRole) {
        // rechercher l'assurance par id
        Optional<Assurance> assurance = assuranceRepository.findById(id);
        // s'il exist
        if (assurance.isPresent()) {
            // mettre a jours les donnés
            if (!assuranceDto.getEmployerRegistrationNumber().equals("")) {
                Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(assuranceDto.getEmployerRegistrationNumber()));
                if (employer.isEmpty()) {
                    return new ResponseEntity<>("Employer not found ! check registration number", HttpStatus.NOT_FOUND);
                }
                assurance.get().setEmployerRegistrationNumber(assuranceDto.getEmployerRegistrationNumber());
            }
            if (assuranceDto.getAmount() != assurance.get().getAmount()) {
                assurance.get().setAmount(assuranceDto.getAmount());
            }
            if (assuranceDto.getNumCareBulletin() != null && !assuranceDto.getNumCareBulletin().equals("")) {
                assurance.get().setNumCareBulletin(assuranceDto.getNumCareBulletin());
            }
            // enregistrer les modifications
            assuranceRepository.save(assurance.get());
            return new ResponseEntity<>(assurance.get(), HttpStatus.OK);
        }
        // message d'erreur en cas ou l'assurance n'exist pas
        return new ResponseEntity<>("Assurance not found !", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/pay/{id}/employees/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdminOrAgentServiceSocial(#userConnectedRole)")
    public ResponseEntity<?> payAssurance(@PathVariable() int id, @PathVariable("role") String userConnectedRole) {
        Optional<Assurance> assurance = assuranceRepository.findById(id);
        if (assurance.isPresent()) {
            if (assurance.get().getStatus() == Assurance.Etat.PAYED) {
                return new ResponseEntity<>("Assurance already payed !", HttpStatus.OK);
            }
            Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(assurance.get().getEmployerRegistrationNumber()));
            if (employer.isPresent()) {
                Optional<Fund> fund = fundRepository.findFirstByOrderById();
                if (fund.isEmpty()) {
                    return new ResponseEntity<>("No fund found !", HttpStatus.NOT_FOUND);
                }
                // if plafond assurance = 0
                if (employer.get().getCeilingAssurance() == 0) {
                    return new ResponseEntity<>("Employer reach the ceiling !", HttpStatus.BAD_REQUEST);
                }
                // verify sold caisse
                if (fund.get().getSold() < assurance.get().getAmount()) {
                    fund.get().setSold(1500);
                }
                // case : montant assurance superieur plafond
                if (employer.get().getCeilingAssurance() < assurance.get().getAmount()) {
                    fund.get().setSold(fund.get().getSold() - employer.get().getCeilingAssurance());
                    employer.get().setCeilingAssurance(0);
                }
                // case : plafond superieur montant
                if (assurance.get().getAmount() < employer.get().getCeilingAssurance()) {
                    fund.get().setSold(fund.get().getSold() - assurance.get().getAmount());
                    employer.get().setCeilingAssurance(employer.get().getCeilingAssurance() - assurance.get().getAmount());
                }

                assurance.get().setStatus(Assurance.Etat.PAYED);
                employerRepository.save(employer.get());
                assuranceRepository.save(assurance.get());
                return new ResponseEntity<>("Assurance payed !", HttpStatus.OK);
            }
            return new ResponseEntity<>("Employer not found !", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Assurance not found !", HttpStatus.NOT_FOUND);
    }
}
