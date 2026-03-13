package com.example.medicalexamination.presentation.controller;

import com.example.medicalexamination.business.dto.ExaminationDTO;
import com.example.medicalexamination.business.dto.InvestigationDTO;
import com.example.medicalexamination.business.dto.PatientResponse;
import com.example.medicalexamination.business.dto.PhysicianResponse;
import com.example.medicalexamination.business.service.ExaminationService;
import com.example.medicalexamination.business.service.IdmClientService;
import com.example.medicalexamination.business.service.MedicalOfficeClientService;
import com.example.medicalexamination.business.service.Utils;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.List;

@Validated
@RestController
@RequestMapping(value="/api/medical-examination")
public class ExaminationController {
    @Autowired
    private ExaminationService examinationService;
    @Autowired
    private IdmClientService idmClientService;
    @Autowired
    private MedicalOfficeClientService medicalOfficeClientService;

    // doar doctorii care au idDoctor din examination
    @PostMapping(value = "/examinations")
    public ResponseEntity<ExaminationDTO> createExamination(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody ExaminationDTO examinationDTO) {
        String token = Utils.extractToken(authorizationHeader);

        // obtinem doctor din celalalt serviciu
        PhysicianResponse physicianResponse = medicalOfficeClientService.getPhysicianById(token, examinationDTO.getIdDoctor());
                                                                        
        if(!idmClientService.verifyIsPhysicianAndHasIdUser(token, physicianResponse.getIdUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<ExaminationDTO>(examinationService.createExamination(token, examinationDTO), HttpStatus.CREATED);
    }

    // doctor si pacient
    @GetMapping(value = "/examinations/{id}")
    public ResponseEntity<ExaminationDTO> getExaminationById(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull String id) {

        String token = Utils.extractToken(authorizationHeader);
        ExaminationDTO examinationDTO = examinationService.getExaminationById(id);

        // obtinem doctor si pacient din celalalt serviciu
        PatientResponse patientResponse = medicalOfficeClientService.getPatientByCnp(token, examinationDTO.getIdPatient());
        PhysicianResponse physicianResponse = medicalOfficeClientService.getPhysicianById(token, examinationDTO.getIdDoctor());

        if(!(idmClientService.verifyIsPhysicianAndHasIdUser(token, physicianResponse.getIdUser()) || idmClientService.verifyIsPatientAndHasIdUser(token, patientResponse.getIdUser()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(examinationDTO, HttpStatus.OK);
    }


    // vede doar pacientul ale lui
    // metoda pentru appointment (filtrare) si pentru validare:
    @GetMapping(value = "/patients/{idPatient}/examinations")
    public ResponseEntity<List<ExaminationDTO>> getByIdPatientAndIdDoctorAndDateOfAppointment(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull String idPatient,
            @RequestParam(required = false) Integer idDoctor,
            @RequestParam(required = false) String dateOfExamination
    ) throws ParseException {

        String token = Utils.extractToken(authorizationHeader);

        // obtinem pacient din celalalt serviciu
        // PatientResponse patientResponse = medicalOfficeClientService.getPatientByCnp(token, idPatient);

        //aici intai verificam verifyIsPAtientAndHasIdUser ca sa vada pacientul examinarile lui, doar ca daca eu ca pacient vreau sa vad appointmenturile unui doctor,
        // nu reusesc fiindca el are si cu alt pacienti si nu le poate adauga in addLinks
//        if(!(idmClientService.verifyIsPatient(token) || idmClientService.verifyIsPhysician(token))) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
//        }
        idmClientService.verifyIsLoggedIn(token);
        return new ResponseEntity<>(examinationService.getByIdPatientAndIdDoctorAndDateOfAppointment(idPatient, idDoctor, dateOfExamination), HttpStatus.OK);
    }

    // vede doar doctorul ale lui
    // metoda pentru appointment:
    @GetMapping(value = "/physicians/{idDoctor}/examinations")
    public ResponseEntity<List<ExaminationDTO>> getByIdDoctor(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull Integer idDoctor
    ) throws ParseException {

        String token = Utils.extractToken(authorizationHeader);

        // obtinem doctor din celalalt serviciu
        PhysicianResponse physicianResponse = medicalOfficeClientService.getPhysicianById(token, idDoctor);

        if(!idmClientService.verifyIsPhysicianAndHasIdUser(token, physicianResponse.getIdUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(examinationService.getByIdDoctor(idDoctor), HttpStatus.OK);
    }

    // doar doctorul
    @DeleteMapping(value = "/examinations/{id}")
    public ResponseEntity<?> deleteExaminationById(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull String id) {
        String token = Utils.extractToken(authorizationHeader);
        ExaminationDTO examinationDTO = examinationService.getExaminationById(id);

        // obtinem doctor din celalalt serviciu
        PhysicianResponse physicianResponse = medicalOfficeClientService.getPhysicianById(token, examinationDTO.getIdDoctor());

        if(!(idmClientService.verifyIsPhysicianAndHasIdUser(token, physicianResponse.getIdUser())) ||
                idmClientService.verifyIsAdministrator(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        examinationService.deleteExaminationById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Investigations: doar doctorul
    @PostMapping(value = "/examinations/{id}/investigations")
    public ResponseEntity<ExaminationDTO> addInvestigationToExamination(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull String id, @RequestBody InvestigationDTO investigationDTO) {
        String token = Utils.extractToken(authorizationHeader);
        ExaminationDTO examinationDTO = examinationService.getExaminationById(id);

        // obtinem doctor din celalalt serviciu
        PhysicianResponse physicianResponse = medicalOfficeClientService.getPhysicianById(token, examinationDTO.getIdDoctor());

        if(!idmClientService.verifyIsPhysicianAndHasIdUser(token, physicianResponse.getIdUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        ExaminationDTO e = examinationService.addInvestigation(id, investigationDTO);
        return new ResponseEntity<ExaminationDTO>(e, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/examinations/{id}/investigations/{idInvestigation}")
    public ResponseEntity<ExaminationDTO> removeInvestigationFromExamination(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull String id, @PathVariable Integer idInvestigation) {
        String token = Utils.extractToken(authorizationHeader);
        ExaminationDTO examinationDTO = examinationService.getExaminationById(id);

        // obtinem doctor din celalalt serviciu
        PhysicianResponse physicianResponse = medicalOfficeClientService.getPhysicianById(token, examinationDTO.getIdDoctor());

        if(!idmClientService.verifyIsPhysicianAndHasIdUser(token, physicianResponse.getIdUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        ExaminationDTO e = examinationService.removeInvestigation(id, idInvestigation);
        return new ResponseEntity<ExaminationDTO>(e, HttpStatus.NO_CONTENT);
    }
}
