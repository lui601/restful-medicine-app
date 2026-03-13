package com.example.medicaloffice.business.service;

import com.example.medicaloffice.business.dto.AppointmentDTO;
import com.example.medicaloffice.business.dto.DateType;
import com.example.medicaloffice.business.dto.ExaminationResponse;
import com.example.medicaloffice.business.dto.Status;
import com.example.medicaloffice.business.mapper.AppointmentMapper;
import com.example.medicaloffice.persistence.entity.AppointmentEntity;
import com.example.medicaloffice.persistence.repository.AppointmentRepository;
import com.example.medicaloffice.presentation.controller.AppointmentController;
import org.mapstruct.factory.Mappers;
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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PhysicianService physicianService;
    @Autowired
    private MedicalExaminationClientService medicalExaminationClientService;
    private final AppointmentMapper appointmentMapper = Mappers.getMapper(AppointmentMapper.class);
    private final String NOT_FOUND_MESSAGE = "Appointment does not exist";
    private final String CONFLICT_MESSAGE_OVERLAP = "Appointments cannot overlap";
    private final String CONFLICT_MESSAGE_TOO_MANY = "No more than one appointment in the same day with the same doctor";
    private final String CONFLICT_MESSAGE_TIME_CONSTRAINT = "Appointments are booked with a minimum 15-minute advance notice";
    private static final String GET_MEDICAL_EXAMINATION_BY_ID_URL = "http://localhost:8081/api/medical-examination/examinations/%s";


    public AppointmentDTO createAppointment(String token, AppointmentDTO appointmentDTO){
        //tratare partea de foreign key-uri, sa nu pot primi un appointment cu idDoctor si idPatient inexistente
        patientService.verifyPatientExists(appointmentDTO.getIdPatient());
        physicianService.verifyPhysicianExists(appointmentDTO.getIdDoctor());

        AppointmentEntity appointmentEntity = appointmentMapper.toEntity(appointmentDTO);

        //validare suprapunere (o consultatie are 15 min)
        List<AppointmentEntity> existingAppointments = appointmentRepository.getByIdPatientOrIdDoctor(appointmentEntity.getIdPatient(), appointmentEntity.getIdDoctor());
        for(AppointmentEntity appointment: existingAppointments){
            long minutes = ChronoUnit.MINUTES.between(appointment.getDateOfAppointment(), appointmentEntity.getDateOfAppointment());
            if(Math.abs(minutes)<15){
                throw new ResponseStatusException(HttpStatus.CONFLICT, CONFLICT_MESSAGE_OVERLAP);
            }
        }

        //un pacient nu poate avea mai mult de o prog in aceeasi zi la acleasi medic
        existingAppointments = appointmentRepository.getByIdPatientAndIdDoctor(appointmentEntity.getIdPatient(), appointmentEntity.getIdDoctor());
        for(AppointmentEntity appointment: existingAppointments){
            long days = ChronoUnit.DAYS.between(appointment.getDateOfAppointment(), appointmentEntity.getDateOfAppointment());
            if(Math.abs(days)<1){
                throw new ResponseStatusException(HttpStatus.CONFLICT, CONFLICT_MESSAGE_TOO_MANY);
            }
        }

        //validare 15 min (programari cu cel putin 15 min in viitor)
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(now, appointmentEntity.getDateOfAppointment());
        if(minutes<15){
            throw new ResponseStatusException(HttpStatus.CONFLICT, CONFLICT_MESSAGE_TIME_CONSTRAINT);
        }

        appointmentRepository.save(appointmentEntity);
        return addLinks(token, appointmentMapper.toDTO(appointmentEntity));
    }

    public AppointmentDTO getAppointmentById(String token, Integer id) {
        AppointmentEntity appointmentEntity = verifyAppointmentExists(id);
        return addLinks(token, appointmentMapper.toDTO(appointmentEntity));
    }

    public AppointmentDTO updateAppointmentById(String token, Integer id, AppointmentDTO appointmentDTO){
        verifyAppointmentExists(id);
        AppointmentEntity a = appointmentMapper.toEntity(appointmentDTO);
        // setam  id-ul pentru a nu se crea un appointment cu id nou
        a.setId(id);
        appointmentRepository.save(a);
        return addLinks(token, appointmentMapper.toDTO(a));
    }

    public void deleteAppointment(String token, Integer id){
        AppointmentEntity appointmentEntity = verifyAppointmentExists(id);

        List<ExaminationResponse> examinations = medicalExaminationClientService.getExaminationsByAppointment(token, appointmentMapper.toDTO(appointmentEntity));
        for(ExaminationResponse examination: examinations) {
            medicalExaminationClientService.deleteExamination(token, examination.getId());
        }

        appointmentRepository.delete(appointmentEntity);
    }

    public Iterable<AppointmentDTO> getAppointmentsByPhysicianId(String token, Integer id){
        return appointmentRepository.findByIdDoctor(id).stream()
                .map(appointmentMapper::toDTO)
                .map(appointmentDTO -> addLinks(token, appointmentDTO))
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByPatientId(
            String token,
            String cnp,
            String dateOfAppointment,
            DateType type,
            Status status,
            Integer idDoctor) throws ParseException {

        if(dateOfAppointment != null){
            switch(type){
                case day: {
                    return appointmentRepository.getByIdPatientAndDayAndStatusAndIdDoctor(cnp, Integer.parseInt(dateOfAppointment), status, idDoctor).stream()
                        .map(appointmentMapper::toDTO)
                            .map(appointmentDTO -> addLinks(token, appointmentDTO))
                        .collect(Collectors.toList());
                }
                case month: {
                    return appointmentRepository.getByIdPatientAndMonthAndStatusAndIdDoctor(cnp, Integer.parseInt(dateOfAppointment), status, idDoctor).stream()
                            .map(appointmentMapper::toDTO)
                            .map(appointmentDTO -> addLinks(token, appointmentDTO))
                            .collect(Collectors.toList());
                }
                default: {
                    //System.out.println(dateOfAppointment);

                    //////// asta rezolva problema cu get appointment by date in examination
                    String newDate = dateOfAppointment.replace("%20", " ");

                    return appointmentRepository.getByIdPatientAndDateOfAppointmentAndStatusAndIdDoctor(cnp, newDate, status, idDoctor).stream()
                            .map(appointmentMapper::toDTO)
                            .map(appointmentDTO -> addLinks(token, appointmentDTO))
                            .collect(Collectors.toList());
                }
            }
        }
        return appointmentRepository.getByIdPatientAndStatusAndIdDoctor(cnp, status, idDoctor).stream()
                .map(appointmentMapper::toDTO)
                .map(appointmentDTO -> addLinks(token, appointmentDTO))
                .collect(Collectors.toList());
    }



    public AppointmentEntity verifyAppointmentExists(Integer id) {
        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findById(id);

        if (optionalAppointmentEntity.isPresent()) {
            return optionalAppointmentEntity.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
        }
    }

    private AppointmentDTO addLinks(String token, AppointmentDTO appointmentDTO) {
        Link selfLink = linkTo(methodOn(AppointmentController.class)
                .getAppointmentById("", appointmentDTO.getId()))
                .withSelfRel();
        Link parentLink = linkTo(methodOn(AppointmentController.class)
                .getAppointmentsByPhysicianId("",appointmentDTO.getIdDoctor()))
                .withRel("parent");

        appointmentDTO.add(selfLink);
        appointmentDTO.add(parentLink);

        List<ExaminationResponse> examinationResponses = medicalExaminationClientService.getExaminationsByAppointment(token, appointmentDTO);

        for (ExaminationResponse examinationResponse : examinationResponses) {
            Link examinationLink = Link.of(
                    String.format(GET_MEDICAL_EXAMINATION_BY_ID_URL, examinationResponse.getId()),
                    "examination"
            );
            appointmentDTO.add(examinationLink);
        }
        return appointmentDTO;
    }


}
