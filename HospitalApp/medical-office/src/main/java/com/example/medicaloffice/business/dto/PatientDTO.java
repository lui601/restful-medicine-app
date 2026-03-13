package com.example.medicaloffice.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.Date;
import org.springframework.hateoas.RepresentationModel;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO extends RepresentationModel<PatientDTO> {
    private String cnp;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
    @NotNull
    private Boolean isActive;
}


