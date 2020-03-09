package com.example.pfe.Controller;

import com.example.pfe.dto.DeplacementDto;
import com.example.pfe.model.Deplacement;
import com.example.pfe.model.Employer;
import com.example.pfe.repositories.DeplacementRepository;
import com.example.pfe.repositories.EmployerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/deplacements")
public class DeplacementController {
    private final EmployerRepository employerRepository;
    private final DeplacementRepository deplacementRepository;

    public DeplacementController(EmployerRepository employerRepository, DeplacementRepository deplacementRepository) {
        this.employerRepository = employerRepository;
        this.deplacementRepository = deplacementRepository;
    }

    @PostMapping
    public ResponseEntity<?> addDeplacment(@Valid @RequestBody DeplacementDto deplacementDto) {
        // verifier si l'employé exist ou nn
        Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(deplacementDto.getEmployerMatricule()));
        // si l'employé exist
        if (employer.isPresent()) {
            // creation d'un nouveau deplacement
            Deplacement deplacement = new Deplacement();
            deplacement.setEmployerMatricule(deplacementDto.getEmployerMatricule());
            deplacement.setMontantDeDeplacement(deplacementDto.getMontantDeDeplacement());
            // enregister le deplacement dans la base de donnés
            deplacementRepository.save(deplacement);
            return new ResponseEntity<>(deplacement, HttpStatus.OK);
        }
        // message d'erreur en cas ou l'employé n'exist pas
        return new ResponseEntity<>("Employer not found !", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getDeplacemntById(@PathVariable() int id){
        // rechercher le deplacemnt par id de deplacemnt
        Optional<Deplacement> deplacement = deplacementRepository.findById(id);
        // si le deplacement exist
        if (deplacement.isPresent()){
            // retourner le deplacement
            return new ResponseEntity<>(deplacement.get(),HttpStatus.OK);
        }
        // message d'erreur en cas ou le deplacement n'exist pas
        return new ResponseEntity<>("Deplacement not found !",HttpStatus.NOT_FOUND);
    }
    @GetMapping
    public ResponseEntity<?> getAllDeplacemnts(){
        // lister tout les deplacement dans la base de donnnées
        return new ResponseEntity<>(deplacementRepository.findAll(),HttpStatus.OK);
    }
    @GetMapping("/employer/{matricule}")
    public ResponseEntity<?> getAllDeplacementsByEmployee(@PathVariable() String matricule){
        //verifier si l'employer exist
        Optional<Employer> employer = employerRepository.findByMatricule(Integer.parseInt(matricule));
        //s'il exist
        if (employer.isPresent()){
            // recuperer la liste de deplacement par employer
            List<Deplacement> deplacements = deplacementRepository.findByEmployerMatricule(matricule);
            // retourner la liste
            return new ResponseEntity<>(deplacements,HttpStatus.OK);
        }
        // message d'erreur en cas ou l'employé n'exist pas
        return new ResponseEntity<>("Employer not found !",HttpStatus.NOT_FOUND);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteDeplacemnt(@PathVariable() int id){
        // rechercher le deplacemnt par id de deplacemnt
        Optional<Deplacement> deplacement = deplacementRepository.findById(id);
        // si le deplacement exist
        if (deplacement.isPresent()){
            // supprimer le deplacement
            deplacementRepository.delete(deplacement.get());
            // retourner un message de succés
            return new ResponseEntity<>("Deplacment deleted successfully !",HttpStatus.OK);
        }
        // message d'erreur en cas ou le deplacement n'exist pas
        return new ResponseEntity<>("Deplacement not found !",HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeplacemnt(@Valid @RequestBody DeplacementDto deplacementDto, @PathVariable() int id){
        // rechercher le deplacemnt par id de deplacemnt
        Optional<Deplacement> deplacement = deplacementRepository.findById(id);
        // si le deplacement exst
        if (deplacement.isPresent()){
            // mettre a jour s les données du deplacement
            deplacement.get().setEmployerMatricule(deplacementDto.getEmployerMatricule());
            deplacement.get().setMontantDeDeplacement(deplacementDto.getMontantDeDeplacement());
            // enregister les modification dans la base de données
            deplacementRepository.save(deplacement.get());
            return new ResponseEntity<>(deplacement.get(),HttpStatus.OK);
        }
        // message d'erreur en cas ou le deplacement n'exist pas
        return new ResponseEntity<>("Deplacement not found !",HttpStatus.NOT_FOUND);
    }

}
