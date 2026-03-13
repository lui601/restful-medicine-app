package com.example.medicaloffice.presentation.controller;

import com.example.medicaloffice.business.dto.GetPhysiciansDTO;
import com.example.medicaloffice.business.dto.PatientDTO;
import com.example.medicaloffice.business.dto.PhysicianDTO;
import com.example.medicaloffice.business.dto.Specialization;
import com.example.medicaloffice.business.service.IdmClientService;
import com.example.medicaloffice.business.service.PhysicianService;
import com.example.medicaloffice.business.service.Utils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Validated
@RestController
@RequestMapping(value="/api/medical-office/physicians")
public class PhysicianController {
    @Autowired
    private PhysicianService physicianService;
    @Autowired
    private IdmClientService idmClientService;

    // oricine e logat poate vedea
    @GetMapping
    public ResponseEntity<GetPhysiciansDTO> getPhysicians(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestParam(required = false) Specialization specialization,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Integer idUser,
            @RequestParam(defaultValue = "false") Boolean exactMatch,
            @RequestParam(required = false) Integer page,
            @RequestParam(defaultValue = "5") Integer itemsPerPage
            ) {
        String token = Utils.extractToken(authorizationHeader);

        idmClientService.verifyIsLoggedIn(token); // daca nu e logged in, se genereaza o exceptie in grpc server care va fi tradusa de advice in unauthorized

        return new ResponseEntity<>(physicianService.getPhysicians(specialization, lastName, idUser, exactMatch, page, itemsPerPage), HttpStatus.OK);
    }

    @GetMapping(value = "/{idDoctor}/patients")
    public ResponseEntity<List<PatientDTO>> getPatientsByIdDoctor(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable Integer idDoctor
    ) {
        String token = Utils.extractToken(authorizationHeader);

        PhysicianDTO physicianDTO = physicianService.getPhysicianById(idDoctor);
        if(!idmClientService.verifyIsPhysicianAndHasIdUser(token, physicianDTO.getIdUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(physicianService.getPatientsByIdDoctor(idDoctor), HttpStatus.OK);
    }

    // partea de unprocessable entity
    @PostMapping
    public ResponseEntity<PhysicianDTO> createPhysician(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                        @RequestBody @Valid PhysicianDTO physicianDTO) {
        String token = Utils.extractToken(authorizationHeader);

        if(!idmClientService.verifyIsAdministrator(token)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PhysicianDTO p = physicianService.createPhysician(physicianDTO);
        return new ResponseEntity<PhysicianDTO>(p, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PhysicianDTO> getPhysicianById(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull Integer id) {
        String token = Utils.extractToken(authorizationHeader);

        idmClientService.verifyIsLoggedIn(token);

        return new ResponseEntity<>(physicianService.getPhysicianById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PhysicianDTO> updatePhyisician(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody @Valid PhysicianDTO physicianDTO, @PathVariable Integer id) {

        String token = Utils.extractToken(authorizationHeader);

        if(!idmClientService.verifyIsAdministrator(token)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        ResponseEntity<PhysicianDTO> responseEntity;

        PhysicianDTO p = physicianService.updatePhysicianById(id, physicianDTO);
        responseEntity = new ResponseEntity<>(p, HttpStatus.OK);

        return responseEntity;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletePhysician(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull  Integer id) {
        String token = Utils.extractToken(authorizationHeader);

        if(!idmClientService.verifyIsAdministrator(token)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        physicianService.deletePhysician(token, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
