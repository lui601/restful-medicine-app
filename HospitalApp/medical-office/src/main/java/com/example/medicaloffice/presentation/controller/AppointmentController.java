package com.example.medicaloffice.presentation.controller;

import com.example.medicaloffice.business.dto.*;
import com.example.medicaloffice.business.service.*;
import jakarta.validation.Valid;
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
@RequestMapping(value="/api/medical-office")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private IdmClientService idmClientService;
    @Autowired
    private PhysicianService physicianService;
    @Autowired
    private PatientService patientService;


    //Pot obtine programarile dupa physician id doar physician cu acel id + toti pacientii pt a se programa (TODO: pacientii sa nu vada toate info)
    @GetMapping(value = "/physicians/{id}/appointments")
    public ResponseEntity<Iterable<AppointmentDTO>> getAppointmentsByPhysicianId(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull Integer id) {

        String token = Utils.extractToken(authorizationHeader);

        PhysicianDTO physicianDTO = physicianService.getPhysicianById(id);

        if(!(idmClientService.verifyIsPatient(token) || idmClientService.verifyIsPhysicianAndHasIdUser(token, physicianDTO.getIdUser())))
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(appointmentService.getAppointmentsByPhysicianId(token, id), HttpStatus.OK);
    }

    @GetMapping(value = "/patients/{cnp}/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPatientId(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull String cnp,
            @RequestParam(required = false) String dateOfAppointment,
            @RequestParam(defaultValue = "date") DateType type,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Integer idDoctor) throws ParseException {
        String token = Utils.extractToken(authorizationHeader);

        PatientDTO patientDTO = patientService.getPatientByCnp(cnp);
        if(!(idmClientService.verifyIsPatientAndHasIdUser(token, patientDTO.getIdUser()) || idmClientService.verifyIsPhysician(token))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(appointmentService.getAppointmentsByPatientId(token, cnp, dateOfAppointment, type, status, idDoctor), HttpStatus.OK);
    }

    // PACIENT SAU DOCTOR CARE SUNT IN APPOINTMENT
    @GetMapping(value = "/appointments/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull Integer id) {

        String token = Utils.extractToken(authorizationHeader);

        AppointmentDTO appointmentDTO = appointmentService.getAppointmentById(token, id);
        PatientDTO patientDTO = patientService.getPatientByCnp(appointmentDTO.getIdPatient());
        PhysicianDTO physicianDTO = physicianService.getPhysicianById(appointmentDTO.getIdDoctor());

        if(!(idmClientService.verifyIsPatientAndHasIdUser(token, patientDTO.getIdUser())
                || idmClientService.verifyIsPhysicianAndHasIdUser(token, physicianDTO.getIdUser()))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        }
        return new ResponseEntity<>(appointmentDTO, HttpStatus.OK);
    }

    //PACIENT PT EL
    @PostMapping(value = "/appointments")
    public ResponseEntity<AppointmentDTO> createAppointment(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody @Valid AppointmentDTO appointmentDTO) {

        String token = Utils.extractToken(authorizationHeader);
        PatientDTO patientDTO = patientService.getPatientByCnp(appointmentDTO.getIdPatient());

        if(!idmClientService.verifyIsPatientAndHasIdUser(token, patientDTO.getIdUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        AppointmentDTO a = appointmentService.createAppointment(token, appointmentDTO);
        return new ResponseEntity<AppointmentDTO>(a, HttpStatus.CREATED);
    }

    //DOCTOR
    @PutMapping(value = "/appointments/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody @Valid AppointmentDTO appointmentDTO, @PathVariable Integer id) {
        String token = Utils.extractToken(authorizationHeader);

        PhysicianDTO physicianDTO = physicianService.getPhysicianById(appointmentDTO.getIdDoctor());

        if(!idmClientService.verifyIsPhysicianAndHasIdUser(token, physicianDTO.getIdUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        ResponseEntity<AppointmentDTO> responseEntity;

        AppointmentDTO a = appointmentService.updateAppointmentById(token, id, appointmentDTO);
        responseEntity = new ResponseEntity<>(a, HttpStatus.OK);

        return responseEntity;
    }

    //doctorul
    @DeleteMapping(value = "/appointments/{id}")
    public ResponseEntity<?> deleteAppointment(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable @NotNull Integer id) {
        String token = Utils.extractToken(authorizationHeader);

        AppointmentDTO appointmentDTO = appointmentService.getAppointmentById(token, id);
        PhysicianDTO physicianDTO = physicianService.getPhysicianById(appointmentDTO.getIdDoctor());

        if(!idmClientService.verifyIsPhysicianAndHasIdUser(token, physicianDTO.getIdUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        appointmentService.deleteAppointment(token, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
