package com.example.medicaloffice.business.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysicianDTO extends RepresentationModel<PhysicianDTO> {
    private Integer idDoctor;
    @NotNull
    private Integer idUser;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;
    @NotNull
    private Specialization specialization;
}

