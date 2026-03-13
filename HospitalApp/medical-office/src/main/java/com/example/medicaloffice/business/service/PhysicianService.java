package com.example.medicaloffice.business.service;

import com.example.medicaloffice.business.dto.AppointmentDTO;
import com.example.medicaloffice.business.dto.ExaminationResponse;
import com.example.medicaloffice.business.dto.GetPhysiciansDTO;
import com.example.medicaloffice.business.dto.PatientDTO;
import com.example.medicaloffice.business.dto.PhysicianDTO;
import com.example.medicaloffice.business.dto.Specialization;
import com.example.medicaloffice.business.mapper.AppointmentMapper;
import com.example.medicaloffice.business.mapper.PhysicianMapper;
import com.example.medicaloffice.persistence.entity.AppointmentEntity;
import com.example.medicaloffice.persistence.entity.PatientEntity;
import com.example.medicaloffice.persistence.entity.PhysicianEntity;
import com.example.medicaloffice.persistence.repository.AppointmentRepository;
import com.example.medicaloffice.persistence.repository.PhysicianRepository;
import com.example.medicaloffice.presentation.controller.PhysicianController;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PhysicianService {

    @Autowired
    private PhysicianRepository physicianRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private MedicalExaminationClientService medicalExaminationClientService;
    private final AppointmentMapper appointmentMapper = Mappers.getMapper(AppointmentMapper.class);
    private final PhysicianMapper physicianMapper = Mappers.getMapper(PhysicianMapper.class);
    private final String NOT_FOUND_MESSAGE = "Physician does not exist";
    private static final String MEDICAL_EXAMINATION_URL = "http://localhost:8081/api/medical-examination/examinations";
    public GetPhysiciansDTO getPhysicians(
            Specialization specialization,
            String lastName,
            Integer idUser,
            Boolean exactMatch,
            Integer page,
            Integer itemsPerPage){

        Pageable pageable;
        PhysicianEntity example = new PhysicianEntity();
        Page<PhysicianEntity> physicianPage;

        example.setSpecialization(specialization);
        example.setLastName(lastName);
        example.setIdUser(idUser);

        if(page != null) {
            pageable = PageRequest.of(page, itemsPerPage);
        } else {
            pageable = PageRequest.of(0, itemsPerPage);
        }

        if (exactMatch) {
            physicianPage = physicianRepository.findAll(Example.of(example), pageable);
        } else {
            //potrivire partiala
            ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAll()
                    .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            physicianPage = physicianRepository.findAll(Example.of(example, customExampleMatcher), pageable);
        }

        return GetPhysiciansDTO.builder()
                .physicians(physicianPage
                        .stream()
                        .map(physicianMapper::toDTO)
                        .map(this::addLinks)
                        .collect(Collectors.toList()))
                .items((int) physicianPage.getTotalElements())
                .build();
    }

    public PhysicianDTO createPhysician(PhysicianDTO physicianDTO){
        PhysicianEntity physicianEntity = physicianMapper.toEntity(physicianDTO);
        physicianRepository.save(physicianEntity);
        return addLinks(physicianMapper.toDTO(physicianEntity));
    }

    public PhysicianDTO getPhysicianById(Integer idDoctor) {
        PhysicianEntity phyisicianEntity = verifyPhysicianExists(idDoctor);
        return addLinks(physicianMapper.toDTO(phyisicianEntity));
    }

    public PhysicianDTO updatePhysicianById(Integer idDoctor, PhysicianDTO physicianDTO){
        verifyPhysicianExists(idDoctor);
        PhysicianEntity p = physicianMapper.toEntity(physicianDTO);
        physicianRepository.save(p);
        return addLinks(physicianMapper.toDTO(p));
    }

    public void deletePhysician(String token, Integer idDoctor){
        PhysicianEntity phyisicianEntity = verifyPhysicianExists(idDoctor);

        // stergem toate programarile doctorului
        for(AppointmentEntity appointment: appointmentRepository.findByIdDoctor(idDoctor)) {
            List<ExaminationResponse> examinations = medicalExaminationClientService.getExaminationsByAppointment(token, appointmentMapper.toDTO(appointment));
            for(ExaminationResponse examination: examinations) {
                medicalExaminationClientService.deleteExamination(token, examination.getId());
            }

            appointmentRepository.delete(appointment);
        }

        physicianRepository.delete(phyisicianEntity);
    }
    
    public PhysicianEntity verifyPhysicianExists(Integer idDoctor) {
        Optional<PhysicianEntity> optionalPhysicianEntity = physicianRepository.findById(idDoctor);

        if (optionalPhysicianEntity.isPresent()) {
            return optionalPhysicianEntity.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
        }
    }

    private PhysicianDTO addLinks(PhysicianDTO physicianDTO) {
        Link selfLink = linkTo(methodOn(PhysicianController.class)
                .getPhysicianById("",physicianDTO.getIdDoctor()))
                .withSelfRel();
        Link parentLink = linkTo(methodOn(PhysicianController.class)
                .getPhysicians("", null, null, null, null, null, null))
                .withRel("parent");
        Link examinationLink = Link.of(MEDICAL_EXAMINATION_URL, "createExaminations");

        physicianDTO.add(selfLink);
        physicianDTO.add(parentLink);
        physicianDTO.add(examinationLink);

        return physicianDTO;
    }

    public List<PatientDTO> getPatientsByIdDoctor(Integer idDoctor) {
        return appointmentRepository.findByIdDoctor(idDoctor).stream()
                .map(AppointmentEntity::getIdPatient)
                .distinct()
                .map(cnp -> patientService.getPatientByCnp(cnp))
                .collect(Collectors.toList());
    }
}
