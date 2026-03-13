package com.example.medicaloffice.persistence.entity;

import com.example.medicaloffice.business.dto.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="appointment")
public class AppointmentEntity {

    //https://www.baeldung.com/hibernate-one-to-many

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //@Id
    @Column(nullable = false)
    private String idPatient;

    //@Id
    @Column(nullable = false)
    private Integer idDoctor;

    //@Id
    @Column(nullable = false)
    private LocalDateTime dateOfAppointment;

    private Status status;
}
