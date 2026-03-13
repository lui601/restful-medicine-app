package com.example.medicaloffice.business.service;

import com.example.medicaloffice.business.dto.AppointmentDTO;
import com.example.medicaloffice.business.dto.ExaminationResponse;
import com.example.medicaloffice.business.dto.PatientDTO;
import com.example.medicaloffice.business.mapper.AppointmentMapper;
import com.example.medicaloffice.business.mapper.PatientMapper;
import com.example.medicaloffice.persistence.entity.AppointmentEntity;
import com.example.medicaloffice.persistence.entity.PatientEntity;
import com.example.medicaloffice.persistence.repository.AppointmentRepository;
import com.example.medicaloffice.persistence.repository.PatientRepository;
import com.example.medicaloffice.presentation.controller.PatientController;
import com.google.rpc.Code;
import com.google.rpc.Status;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private MedicalExaminationClientService medicalExaminationClientService;
    private final PatientMapper patientMapper = Mappers.getMapper(PatientMapper.class);
    private final AppointmentMapper appointmentMapper = Mappers.getMapper(AppointmentMapper.class);
    private final String NOT_FOUND_MESSAGE = "Patient does not exist";

    public Iterable<PatientDTO> getPatients(){
        return patientRepository.findAll().stream()
                .map(patientMapper::toDTO)
                .map(this::addLinks)
                .collect(Collectors.toList());
    }

    public PatientDTO createPatient(PatientDTO patientDTO){
        PatientEntity patientEntity = patientMapper.toEntity(patientDTO);
        Utils.validateDateOfBirth(patientEntity.getDateOfBirth());
        patientRepository.save(patientEntity);
        return addLinks(patientMapper.toDTO(patientEntity));
    }

    public PatientDTO getPatientByCnp(String cnp) {
        PatientEntity patientEntity = verifyPatientExists(cnp);
        return addLinks(patientMapper.toDTO(patientEntity));
    }

    public PatientDTO updatePatientByCnp(String cnp, PatientDTO patientDTO){
        verifyPatientExists(cnp);
        PatientEntity p = patientMapper.toEntity(patientDTO);
        Utils.validateDateOfBirth(p.getDateOfBirth());
        p.setCnp(cnp);
        patientRepository.save(p);
        return addLinks(patientMapper.toDTO(p));
    }

    public void deletePatient(String token, String cnp){
        PatientEntity patientEntity = verifyPatientExists(cnp);

        // stergem toate programarile pacientului
        for(AppointmentEntity appointment: appointmentRepository.findByIdPatient(cnp)) {
            List<ExaminationResponse> examinations = medicalExaminationClientService.getExaminationsByAppointment(token, appointmentMapper.toDTO(appointment));
            for(ExaminationResponse examination: examinations) {
                medicalExaminationClientService.deleteExamination(token, examination.getId());
            }

            appointmentRepository.delete(appointment);
        }

        patientRepository.delete(patientEntity);
    }

    public PatientEntity verifyPatientExists(String cnp) {
        Optional<PatientEntity> optionalPatientEntity = patientRepository.findById(cnp);

        if (optionalPatientEntity.isPresent()) {
            return optionalPatientEntity.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
        }
    }

    private PatientDTO addLinks(PatientDTO patientDTO) {
        Link selfLink = linkTo(methodOn(PatientController.class)
                .getPatientByCnp("", patientDTO.getCnp()))
                .withSelfRel();
        Link parentLink = linkTo(methodOn(PatientController.class)
                .getPatients("",null))
                .withRel("parent");
        patientDTO.add(selfLink);
        patientDTO.add(parentLink);

        return patientDTO;
    }

    public PatientDTO getByIdUser(Integer idUser) {
        Optional<PatientEntity> optionalPatientEntity = patientRepository.findByIdUser(idUser);
        if(optionalPatientEntity.isPresent()){
            return addLinks(patientMapper.toDTO(optionalPatientEntity.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
        }
    }
}
