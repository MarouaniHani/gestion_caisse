package com.example.pfe.Controller;

import com.example.pfe.dto.EmployerDto;
import com.example.pfe.dto.LoginDto;
import com.example.pfe.model.Employer;
import com.example.pfe.repositories.EmployerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/employers")
public class EmployerController {

    private final EmployerRepository employerRepository;

    public EmployerController(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @PostMapping
    public ResponseEntity<?> createEmployer(@Valid @RequestBody EmployerDto employerDto) {
        // creation d'un nouveau employé
        Employer employer = new Employer();
        Employer.Postion position;
        // validation de la position
        switch (employerDto.getPosition().toLowerCase()) {
            case "cadre":
                position = Employer.Postion.CADRE;
                break;
            case "maitrise":
                position = Employer.Postion.MAITRISE;
                break;
            case "executeur":
                position = Employer.Postion.EXECUTEUR;
                break;
            default:
                return new ResponseEntity<>("Please enter a valid position !", HttpStatus.BAD_REQUEST);
        }
        // persister les données de l'employé
        employer.setNom(employerDto.getNom());
        employer.setPrenom(employerDto.getPrenom());
        employer.setMotDePass(employerDto.getMotDePass());
        employer.setPostion(position);
        employer.setService(employerDto.getService());
        employer.setPlafondAssurance(2000);
        // enregistrer les données dans la base de données
        employerRepository.save(employer);
        return new ResponseEntity<>(employer, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        // Lister tout les employés dans la base de données
        List<Employer> employers = employerRepository.findAll();
        return new ResponseEntity<>(employers, HttpStatus.OK);
    }

    @GetMapping("/matricule/{id}")
    public ResponseEntity<?> getEmployerByMatricule(@PathVariable() String id) {
        // rechercher l'employé par le matricule
        Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(id));
        // si l'employé exist
        if (employer.isPresent()) {
            return new ResponseEntity<>(employer, HttpStatus.OK);
        }
        // pas d'employé trouvé avec le matricule donné
        return new ResponseEntity<>("Employer not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployer(@PathVariable() String id) {
        // rechercher l'employé par le matricule
        Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(id));
        // si l'employé exist
        if (employer.isPresent()) {
            // supprimer l'employé
            employerRepository.delete(employer.get());
            // retourner un message de succés
            return new ResponseEntity<>("Employer deleted successfully !", HttpStatus.OK);
        }
        // aucun employé trouver avec ce matricule
        return new ResponseEntity<>("Employer not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployer(@RequestBody EmployerDto employerDto, @PathVariable() String id) {
        // rechercher l'employé par le matricule
        Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(id));
        // si l'employé exist
        if (employer.isPresent()) {
            Employer.Postion position;
            //valider la position de l'employé
            switch (employerDto.getPosition().toLowerCase()) {
                case "cadre":
                    position = Employer.Postion.CADRE;
                    break;
                case "maitrise":
                    position = Employer.Postion.MAITRISE;
                    break;
                case "executeur":
                    position = Employer.Postion.EXECUTEUR;
                    break;
                default:
                    return new ResponseEntity<>("Please enter a valid position !", HttpStatus.BAD_REQUEST);
            }
            //persister les données
            employer.get().setNom(employerDto.getNom());
            employer.get().setPrenom(employerDto.getPrenom());
            employer.get().setService(employerDto.getService());
            employer.get().setPostion(position);
            employer.get().setMotDePass(employerDto.getMotDePass());
            // mise a jour de l'employé dans la base de donnés
            employerRepository.save(employer.get());
            return new ResponseEntity<>(employer.get(), HttpStatus.OK);
        }
        // message d'erreur si l'employé n'est pas trouvé
        return new ResponseEntity<>("Employer not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto){
        Optional<Employer> employer = employerRepository.findByMatriculeAndMotDePass(loginDto.getMatricule(),loginDto.getMotDePass());
        if (employer.isPresent()){
            return new ResponseEntity<>(employer.get().getPostion(),HttpStatus.OK);
        }
        return new ResponseEntity<>("Employer not found ! verify mdp et matricule",HttpStatus.NOT_FOUND);
    }
}
