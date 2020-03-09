package com.example.pfe.Controller;

import com.example.pfe.dto.PaymentDemandDto;
import com.example.pfe.model.BonPour;
import com.example.pfe.model.PaymentDemand;
import com.example.pfe.repositories.BonPourRepository;
import com.example.pfe.repositories.PaymentDemandRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/payments")
public class PaymentDemandController {

    private final PaymentDemandRepository paymentDemandRepository;
    private final BonPourRepository bonPourRepository;

    public PaymentDemandController(PaymentDemandRepository paymentDemandRepository, BonPourRepository bonPourRepository) {
        this.paymentDemandRepository = paymentDemandRepository;
        this.bonPourRepository = bonPourRepository;
    }

    @PostMapping
    public ResponseEntity<?> addPaymentDemand(@Valid @RequestBody PaymentDemandDto paymentDemandDto) {
        // verify if bon pour exist
        Optional<BonPour> bonPour = bonPourRepository.findById(Integer.parseInt(paymentDemandDto.getBonPourId()));
        // if exist
        if (bonPour.isPresent()) {
            // creation d'une demande de paiement
            PaymentDemand paymentDemand = new PaymentDemand();
            LocalDate date = LocalDate.now();
            paymentDemand.setDate(date.toString());
            paymentDemand.setBonPourId(paymentDemandDto.getBonPourId());
            // enregistrer la demande
            paymentDemandRepository.save(paymentDemand);
            return new ResponseEntity<>(paymentDemand, HttpStatus.OK);
        }
        // message d'erreur en cas ou le bon pour n'exist pas
        return new ResponseEntity<>("Bon pour not found !", HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<?> getAllPaymentDemands() {
        // lister tout les demandes de paiement
        List<PaymentDemand> paymentDemandList = paymentDemandRepository.findAll();
        return new ResponseEntity<>(paymentDemandList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentDemandById(@PathVariable() int id) {
        // verifier si la demande de paiement exist
        Optional<PaymentDemand> paymentDemand = paymentDemandRepository.findById(id);
        if (paymentDemand.isPresent()) {
            // retourner la demande de paiement
            return new ResponseEntity<>(paymentDemand.get(), HttpStatus.OK);
        }
        // message d'erreur si la demande de paiement n'exist pas
        return new ResponseEntity<>("Payment demand not found !", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentDemand(@PathVariable() int id) {
        // verifier si la demande de paiement exist
        Optional<PaymentDemand> paymentDemand = paymentDemandRepository.findById(id);
        // si la demande exist
        if (paymentDemand.isPresent()) {
            // supprimer la demande de paiement
            paymentDemandRepository.delete(paymentDemand.get());
            return new ResponseEntity<>("Payment demand deleted successfully !", HttpStatus.OK);
        }
        // message d'erreur en cas ou la demande n'exist pas
        return new ResponseEntity<>("Payment demand not found !", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentDemand(@PathVariable() int id, @RequestBody PaymentDemandDto paymentDemandDto) {
        // verifier si la demande de paiement exist
        Optional<PaymentDemand> paymentDemand = paymentDemandRepository.findById(id);
        // si la demande exist
        if (paymentDemand.isPresent()) {
            // mettre à jours les données de la demande
            if (paymentDemandDto.getDate() != null && !paymentDemandDto.getDate().equals("")) {
                paymentDemand.get().setDate(paymentDemandDto.getDate());
            }
            if (!paymentDemandDto.getBonPourId().equals("")) {
                paymentDemand.get().setBonPourId(paymentDemandDto.getBonPourId());
            }
            // enregistrer les modifications de la demande de paiement
            paymentDemandRepository.save(paymentDemand.get());
            return new ResponseEntity<>(paymentDemand.get(), HttpStatus.OK);
        }
        // message d'erreur en cas ou la demande de paiement n'exist pas
        return new ResponseEntity<>("Payment demand not found !", HttpStatus.NOT_FOUND);
    }
}
