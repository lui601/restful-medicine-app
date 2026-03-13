package com.example.medicaloffice.presentation.controller;

import com.example.medicaloffice.business.dto.PatientDTO;
import com.example.medicaloffice.business.service.IdmClientService;
import com.example.medicaloffice.business.service.PatientService;
import com.example.medicaloffice.business.service.Utils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Validated
@RestController
@RequestMapping(value="/api/medical-office/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    IdmClientService idmClientService;

    // Combinat get patients cu get by id, doar adminul ia toti pacientii, un pacient se poate vedea pe el singur
    // nu avem o metoda de getPatientByIdUser deoarece idUser nu e cheie primara (getPatientById) => filtru pe idUser
    @GetMapping
    public ResponseEntity<Iterable<PatientDTO>> getPatients(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestParam(required = false) Integer idUser
            ) {
        String token = Utils.extractToken(authorizationHeader);

        if(idUser != null){
            PatientDTO patientDTO = patientService.getByIdUser(idUser);
            if(!idmClientService.verifyIsPatientAndHasIdUser(token, idUser)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            // fiindca metoda returneaza iterable, si in cazul asta eu vreau doar un patient, fac o lista cu un elem
            return new ResponseEntity<>(Collections.singletonList(patientDTO), HttpStatus.OK);
        }

        if(!idmClientService.verifyIsAdministrator(token)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(patientService.getPatients(), HttpStatus.OK);
    }

    // ADMIN SAU EL INSUSI sau DOCTOR
    @GetMapping(value = "/{cnp}")
    public ResponseEntity<PatientDTO> getPatientByCnp(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull String cnp) {
        String token = Utils.extractToken(authorizationHeader);
        PatientDTO patientDTO = patientService.getPatientByCnp(cnp);

        if(!(idmClientService.verifyIsAdministrator(token)
                || idmClientService.verifyIsPatientAndHasIdUser(token, patientDTO.getIdUser())
                || idmClientService.verifyIsPhysician(token))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }

    //PACIENTUL
    @PutMapping(value = "/{cnp}")
    public ResponseEntity<PatientDTO> updatePatient(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
            @RequestBody @Valid PatientDTO patientDTO,
            @PathVariable String cnp) {

        ResponseEntity<PatientDTO> responseEntity;
        try {
            PatientDTO oldPatientDTO = patientService.getPatientByCnp(cnp);

            // suntem pe cazul de update
            // lipseste header-ul de autorizare -> inseamna ca se incearca inregistrarea unui pacient nou cu un cnp care exista deja -> conflict
            if(authorizationHeader == null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "This CNP is already registered");
            }
            // header-ul de autorizare este prezent -> se continua cu flow ul de update

            String token = Utils.extractToken(authorizationHeader);

            if(!(idmClientService.verifyIsAdministrator(token) || idmClientService.verifyIsPatientAndHasIdUser(token, oldPatientDTO.getIdUser()) )){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            PatientDTO p = patientService.updatePatientByCnp(cnp, patientDTO);
            responseEntity = new ResponseEntity<>(p, HttpStatus.OK);

        } catch (ResponseStatusException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                patientDTO.setCnp(cnp);
                PatientDTO p = patientService.createPatient(patientDTO);
                responseEntity = new ResponseEntity<>(p, HttpStatus.CREATED);
            } else {
                throw e;
            }
        }
        return responseEntity;
    }

    //EL SAU ADMIN
    @DeleteMapping(value = "/{cnp}")
    public ResponseEntity<?> deletePatient(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull  String cnp) {
        String token = Utils.extractToken(authorizationHeader);
        PatientDTO patientDTO = patientService.getPatientByCnp(cnp);

        if(!(idmClientService.verifyIsAdministrator(token) || idmClientService.verifyIsPatientAndHasIdUser(token, patientDTO.getIdUser()) )){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        patientService.deletePatient(token, cnp);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
