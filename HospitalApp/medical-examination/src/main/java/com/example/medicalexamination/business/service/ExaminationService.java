package com.example.medicalexamination.business.service;

import com.example.medicalexamination.business.dto.AppointmentResponse;
import com.example.medicalexamination.business.dto.ExaminationDTO;
import com.example.medicalexamination.business.dto.InvestigationDTO;
import com.example.medicalexamination.persistence.document.Examination;
import com.example.medicalexamination.persistence.document.Investigation;
import com.example.medicalexamination.persistence.repository.ExaminationRepository;
import com.example.medicalexamination.presentation.controller.ExaminationController;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ExaminationService {
    @Autowired
    private ExaminationRepository examinationRepository;
    private final String NOT_FOUND_MESSAGE = "Examination does not exist";

    public ExaminationDTO createExamination(String token, ExaminationDTO examinationDTO) {
        Examination examination = toEntity(examinationDTO);

        // obtinem appointment din celalalt serviciu
        RestTemplate restTemplate = new RestTemplate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy%20HH:mm:ss");
        String formattedDateOfExamination = examination.getDateOfExamination().format(formatter);
        String url = String.format("http://localhost:8080/api/medical-office/patients/%s/appointments?dateOfAppointment=%s", examination.getIdPatient(), formattedDateOfExamination);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<List<AppointmentResponse>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<AppointmentResponse>>() {
        });
        List<AppointmentResponse> appointmentResponses = response.getBody();

        System.out.println(url);
        System.out.println(response.getBody());

        //vf daca idPatient, idDoctor si data corespund cu vreun appointment
        for (AppointmentResponse appointmentResponse : appointmentResponses) {
            if (Objects.equals(appointmentResponse.getIdDoctor(), examination.getIdDoctor())) {
                return toDTO(examinationRepository.save(examination));
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
    }

    public ExaminationDTO getExaminationById(String id) {
        Examination examination = verifyExaminationExists(id);
        return addLinks(toDTO(examination));
    }

    public void deleteExaminationById(String id) {
        Examination examination = verifyExaminationExists(id);
        examinationRepository.delete(examination);
    }

    private ExaminationDTO toDTO(Examination examination) {
        return ExaminationDTO.builder()
                .id(examination.getId())
                .idPatient(examination.getIdPatient())
                .idDoctor(examination.getIdDoctor())
                .dateOfExamination(examination.getDateOfExamination())
                .diagnostic(examination.getDiagnostic())
                .investigations(examination.getInvestigations().stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private Examination toEntity(ExaminationDTO examinationDTO) {
        return Examination.builder()
                .idPatient(examinationDTO.getIdPatient())
                .idDoctor(examinationDTO.getIdDoctor())
                .dateOfExamination(examinationDTO.getDateOfExamination())
                .diagnostic(examinationDTO.getDiagnostic())
                .investigations(new ArrayList<>())
                .build();
    }

    private InvestigationDTO toDTO(Investigation investigation) {
        return InvestigationDTO.builder()
                .id(investigation.getId())
                .name(investigation.getName())
                .processingTime(investigation.getProcessingTime())
                .result(investigation.getResult())
                .build();
    }

    private Investigation toEntity(InvestigationDTO investigationDTO) {
        return Investigation.builder()
                .name(investigationDTO.getName())
                .processingTime(investigationDTO.getProcessingTime())
                .result(investigationDTO.getResult())
                .build();
    }

    @SneakyThrows
    private ExaminationDTO addLinks(ExaminationDTO examinationDTO) {
        Link selfLink = linkTo(methodOn(ExaminationController.class)
                .getExaminationById("",examinationDTO.getId()))
                .withSelfRel();
        Link parentLink = linkTo(methodOn(ExaminationController.class)
                .getByIdPatientAndIdDoctorAndDateOfAppointment("", examinationDTO.getIdPatient(), null, null))
                .withRel("parent");

        examinationDTO.add(selfLink);
        examinationDTO.add(parentLink);

        return examinationDTO;
    }

    public Examination verifyExaminationExists(String id) {
        Optional<Examination> optionalExamination = examinationRepository.findById(id);

        if (optionalExamination.isPresent()) {
            return optionalExamination.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
        }
    }

    // Investigations
    public ExaminationDTO addInvestigation(String id, InvestigationDTO investigationDTO) {
        Examination examination = verifyExaminationExists(id);
        Investigation investigation = toEntity(investigationDTO);
        List<Investigation> investigationList = examination.getInvestigations();
        Integer size = investigationList.size();

        if (size == 0) {
            investigation.setId(1);
        } else {
            investigation.setId(investigationList.get(size - 1).getId() + 1);
        }
        examination.getInvestigations().add(investigation);
        examinationRepository.save(examination);

        return addLinks(toDTO(examination));
    }

    public ExaminationDTO removeInvestigation(String id, Integer idInvestigation) {
        Examination examination = verifyExaminationExists(id);
        // examination.getInvestigations();
        boolean wasRemoved = examination.getInvestigations().removeIf(investigation -> Objects.equals(investigation.getId(), idInvestigation));
        if(!wasRemoved) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Investigation not found");
        }
        examinationRepository.save(examination);
        return addLinks(toDTO(examination));
    }

    /// metoda pentru in links
    public List<ExaminationDTO> getByIdPatientAndIdDoctorAndDateOfAppointment(
            String idPatient,
            Integer idDoctor,
            String dateOfExamination) throws ParseException {
        LocalDateTime dateTime = null;

        if(dateOfExamination != null) {
            // asta rezolva problema cu get appointment by date in examination
            String newDate = dateOfExamination.replace("%20", " ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            dateTime = LocalDateTime.parse(newDate, formatter);
        }
        System.out.println(dateTime);

        List<ExaminationDTO> res =  examinationRepository.getByIdPatientAndIdDoctorAndDateOfAppointment(idPatient, idDoctor, dateTime).stream()
                .map(this::toDTO)
                .map(this::addLinks)
                .collect(Collectors.toList());

        //System.out.println(res.size());
        return res;
    }

    public List<ExaminationDTO> getByIdDoctor(Integer idDoctor) {

        List<ExaminationDTO> res =  examinationRepository.getByIdDoctor(idDoctor).stream()
                .map(this::toDTO)
                .map(this::addLinks)
                .collect(Collectors.toList());

        System.out.println(res.size());
        return res;
    }
}
