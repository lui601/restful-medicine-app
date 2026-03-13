package com.example.medicaloffice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AppointmentId implements Serializable {
    private String idPatient;
    private Integer idDoctor;
    private Date dateOfAppointment;
}
