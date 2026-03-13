package com.example.medicalexamination.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExaminationDTO extends RepresentationModel<ExaminationDTO> {
    private String id;
    private String idPatient;
    private Integer idDoctor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateOfExamination;
    private Diagnostic diagnostic;
    private List<InvestigationDTO> investigations;
}
