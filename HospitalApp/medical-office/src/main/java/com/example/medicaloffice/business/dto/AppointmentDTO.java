package com.example.medicaloffice.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO extends RepresentationModel<AppointmentDTO> {
    // folosim un id in loc sa cream un primary key compus din id pacient doctor si data
    private Integer id;
    @NotNull
    private String idPatient;
    @NotNull
    private Integer idDoctor;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    //@DateTimeFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateOfAppointment;
    private Status status;
}
