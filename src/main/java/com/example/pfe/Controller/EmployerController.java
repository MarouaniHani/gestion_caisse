package com.example.pfe.Controller;

import com.example.pfe.dto.EmployerDto;
import com.example.pfe.dto.LoginDto;
import com.example.pfe.model.Employer;
import com.example.pfe.repositories.EmployerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/employers")
public class EmployerController {

    //todo: get list employees by each filter
    private final EmployerRepository employerRepository;

    public EmployerController(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @PostMapping("/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdmin(#userConnectedRole)")
    public ResponseEntity<?> createEmployer(@Valid @RequestBody EmployerDto employerDto, @PathVariable("role") String userConnectedRole) {
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
        // validation du role
        Employer.Role role;
        switch (employerDto.getRole().toLowerCase()) {
            case "admin":
                role = Employer.Role.ADMIN;
                break;
            case "caissier":
                role = Employer.Role.CAISSIER;
                break;
            case "agent_parc_auto":
                role = Employer.Role.AGENT_PARC_AUTO;
                break;
            case "agent_service_social":
                role = Employer.Role.AGENT_SERVICE_SOCIAL;
                break;
            default:
                return new ResponseEntity<>("Please enter a valid role !", HttpStatus.BAD_REQUEST);
        }
        // persister les données de l'employé
        employer.setFirstName(employerDto.getFirstName());
        employer.setLastName(employerDto.getLastName());
        employer.setPassword(employerDto.getPassword());
        employer.setPostion(position);
        employer.setRole(role);
        employer.setService(employerDto.getService());
        //todo : plafond assurance
        employer.setCeilingAssurance(2000);
        // enregistrer les données dans la base de données
        employerRepository.save(employer);
        return new ResponseEntity<>(employer, HttpStatus.OK);
    }

    @GetMapping("/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdmin(#userConnectedRole)")
    public ResponseEntity<?> getAllEmployees(@PathVariable("role") String userConnectedRole) {
        // Lister tout les employés dans la base de données
        List<Employer> employers = employerRepository.findAll();
        return new ResponseEntity<>(employers, HttpStatus.OK);
    }

    @GetMapping("{role}/registration-number/{id}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdmin(#userConnectedRole)")
    public ResponseEntity<?> getEmployerByMatricule(@PathVariable() String id, @PathVariable("role") String userConnectedRole) {
        // rechercher l'employé par le matricule
        Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(id));
        // si l'employé exist
        if (employer.isPresent()) {
            return new ResponseEntity<>(employer, HttpStatus.OK);
        }
        // pas d'employé trouvé avec le matricule donné
        return new ResponseEntity<>("Employer not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdmin(#userConnectedRole)")
    public ResponseEntity<?> deleteEmployer(@PathVariable() String id, @PathVariable("role") String userConnectedRole) {
        // rechercher l'employé par le matricule
        Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(id));
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

    @PutMapping("/{id}/{role}")
    @PreAuthorize("@employeeServiceImpl.checkIsAdmin(#userConnectedRole)")
    public ResponseEntity<?> updateEmployer(@RequestBody EmployerDto employerDto, @PathVariable() String id, @PathVariable("role") String userConnectedRole) {
        // rechercher l'employé par le matricule
        Optional<Employer> employer = employerRepository.findByRegistrationNumber(Integer.parseInt(id));
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
            employer.get().setFirstName(employerDto.getFirstName());
            employer.get().setLastName(employerDto.getLastName());
            employer.get().setService(employerDto.getService());
            employer.get().setPostion(position);
            employer.get().setPassword(employerDto.getPassword());
            // mise a jour de l'employé dans la base de donnés
            employerRepository.save(employer.get());
            return new ResponseEntity<>(employer.get(), HttpStatus.OK);
        }
        // message d'erreur si l'employé n'est pas trouvé
        return new ResponseEntity<>("Employer not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    @PreAuthorize("@employeeServiceImpl.checkIsAdmin(#userConnectedRole)")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, @PathVariable("role") String userConnectedRole) {
        Optional<Employer> employer = employerRepository.findByRegistrationNumberAndPassword(loginDto.getRegistrationNumber(), loginDto.getPassword());
        if (employer.isPresent()) {
            return new ResponseEntity<>(employer.get().getPostion(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Employer not found ! verify password and registration number", HttpStatus.NOT_FOUND);
    }
}
