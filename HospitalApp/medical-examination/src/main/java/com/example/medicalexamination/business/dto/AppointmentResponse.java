package com.example.medicalexamination.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentResponse implements Serializable {
    private Integer id;
    private String idPatient;
    private Integer idDoctor;
    private String dateOfAppointment;
    private String status;
    private List<Link> links;
}
