package com.example.pfe.Controller;

import com.example.pfe.dto.AssuranceDto;
import com.example.pfe.model.Assurance;
import com.example.pfe.model.Caisse;
import com.example.pfe.model.Employer;
import com.example.pfe.repositories.AssuranceRepository;
import com.example.pfe.repositories.CaisseRepository;
import com.example.pfe.repositories.EmployerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final CaisseRepository caisseRepository;

    public AssuranceController(AssuranceRepository assuranceRepository, EmployerRepository employerRepository, CaisseRepository caisseRepository) {
        this.assuranceRepository = assuranceRepository;
        this.employerRepository = employerRepository;
        this.caisseRepository = caisseRepository;
    }

    @PostMapping
    ResponseEntity<?> addAssurance(@Valid @RequestBody AssuranceDto assuranceDto) {
        // verifier si l'emploer exist
        Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(assuranceDto.getEmployerMatricule()));
        // s'il exist
        if (employer.isPresent()) {
            // creation d'une nouvelle assurance
            Assurance assurance = new Assurance();
            // la date de creation
            LocalDate date = LocalDate.now();
            // persister les données
            assurance.setEmployerMatricule(assuranceDto.getEmployerMatricule());
            assurance.setNumBultinDeSoin(assuranceDto.getNumBultinDeSoin());
            assurance.setMontant(assuranceDto.getMontant());
            assurance.setDate(date.toString());
            // etat de l'assurance par defaut non payé
            assurance.setEtat(Assurance.Etat.NOT_PAYED);
            // enregistrer l'assurance dans la base de données
            assuranceRepository.save(assurance);
            return new ResponseEntity<>(assurance, HttpStatus.OK);
        }
        // messsage d'erreur en cas ou l'employé n'exist pas
        return new ResponseEntity<>("Employer not found ! please check matricule ", HttpStatus.NOT_FOUND);
    }

    @GetMapping
    ResponseEntity<?> getAllAssurances() {
        // lister tout les assurances de la base de données
        List<Assurance> assurances = assuranceRepository.findAll();
        return new ResponseEntity<>(assurances, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getAssuranceById(@PathVariable() int id) {
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

    @GetMapping("/matricule/{id}")
    ResponseEntity<?> getAssurancesByMatricule(@PathVariable() String id) {
        // verifier si l'employé exist
        Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(id));
        // s'il exist
        if (employer.isPresent()) {
            // lister les assurances par matricule
            List<Assurance> assurances = assuranceRepository.findByEmployerMatricule(id);
            return new ResponseEntity<>(assurances, HttpStatus.OK);
        }
        // message d'erreur en cas ou l'employé n'exist pas
        return new ResponseEntity<>("Employer not found !", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/matriclue/{id}/not-payed")
    ResponseEntity<?> getAssurancesNotPayedByMatricule(@PathVariable() String id) {
        // verifier si l'employé exist
        Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(id));
        // s'il exist
        if (employer.isPresent()) {
            // lister les assurances par matricule
            List<Assurance> assurances = assuranceRepository.findByEmployerMatriculeAndEtat(id, Assurance.Etat.NOT_PAYED);
            return new ResponseEntity<>(assurances, HttpStatus.OK);
        }
        // message d'erreur en cas ou l'employé n'exist pas
        return new ResponseEntity<>("Employer not found !", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/matriclue/{id}/payed")
    ResponseEntity<?> getAssurancesPayedByMatricule(@PathVariable() String id) {
        // verifier si l'employé exist
        Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(id));
        // s'il exist
        if (employer.isPresent()) {
            // lister les assurances par matricule
            List<Assurance> assurances = assuranceRepository.findByEmployerMatriculeAndEtat(id, Assurance.Etat.PAYED);
            return new ResponseEntity<>(assurances, HttpStatus.OK);
        }
        // message d'erreur en cas ou l'employé n'exist pas
        return new ResponseEntity<>("Employer not found !", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteAssurance(@PathVariable() int id) {
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

    @PutMapping("/{id}")
    ResponseEntity<?> updateAssurance(@PathVariable() int id, @Valid @RequestBody AssuranceDto assuranceDto) {
        // rechercher l'assurance par id
        Optional<Assurance> assurance = assuranceRepository.findById(id);
        // s'il exist
        if (assurance.isPresent()) {
            // mettre a jours les donnés
            if (!assuranceDto.getEmployerMatricule().equals("")) {
                Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(assuranceDto.getEmployerMatricule()));
                if (employer.isEmpty()) {
                    return new ResponseEntity<>("Employer not found ! check matricule", HttpStatus.NOT_FOUND);
                }
                assurance.get().setEmployerMatricule(assuranceDto.getEmployerMatricule());
            }
            if (assuranceDto.getMontant() != assurance.get().getMontant()) {
                assurance.get().setMontant(assuranceDto.getMontant());
            }
            if (assuranceDto.getNumBultinDeSoin() != null && !assuranceDto.getNumBultinDeSoin().equals("")) {
                assurance.get().setNumBultinDeSoin(assuranceDto.getNumBultinDeSoin());
            }
            // enregistrer les modifications
            assuranceRepository.save(assurance.get());
            return new ResponseEntity<>(assurance.get(), HttpStatus.OK);
        }
        // message d'erreur en cas ou l'assurance n'exist pas
        return new ResponseEntity<>("Assurance not found !", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<?> payAssurance(@PathVariable() int id) {
        Optional<Assurance> assurance = assuranceRepository.findById(id);
        if (assurance.isPresent()) {
            if (assurance.get().getEtat() == Assurance.Etat.PAYED) {
                return new ResponseEntity<>("Assurance already payed !", HttpStatus.OK);
            }
            Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(assurance.get().getEmployerMatricule()));
            if (employer.isPresent()) {
                Optional<Caisse> caisse = caisseRepository.findFirstByOrderById();
                if (caisse.isEmpty()) {
                    return new ResponseEntity<>("No caisse found !", HttpStatus.NOT_FOUND);
                }
                // if plafond assurance = 0
                if (employer.get().getPlafondAssurance() == 0) {
                    return new ResponseEntity<>("Employer reach the ceiling !", HttpStatus.BAD_REQUEST);
                }
                // verify sold caisse
                if (caisse.get().getSolde() < assurance.get().getMontant()) {
                    caisse.get().setSolde(1500);
                }
                // case : montant assurance superieur plafond
                if (employer.get().getPlafondAssurance() < assurance.get().getMontant()) {
                    caisse.get().setSolde(caisse.get().getSolde() - employer.get().getPlafondAssurance());
                    employer.get().setPlafondAssurance(0);
                }
                // case : plafond superieur montant
                if (assurance.get().getMontant() < employer.get().getPlafondAssurance()) {
                    caisse.get().setSolde(caisse.get().getSolde() - assurance.get().getMontant());
                    employer.get().setPlafondAssurance(employer.get().getPlafondAssurance() - assurance.get().getMontant());
                }

                assurance.get().setEtat(Assurance.Etat.PAYED);
                employerRepository.save(employer.get());
                assuranceRepository.save(assurance.get());
                return new ResponseEntity<>("Assurance payed !", HttpStatus.OK);
            }
            return new ResponseEntity<>("Employer not found !", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Assurance not found !", HttpStatus.NOT_FOUND);
    }
}
