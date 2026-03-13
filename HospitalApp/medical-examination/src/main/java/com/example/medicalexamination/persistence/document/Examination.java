package com.example.medicalexamination.persistence.document;

import com.example.medicalexamination.business.dto.Diagnostic;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Getter
@Setter
@Builder
public class Examination {
    @Id
    private String id;
    private String idPatient;
    private Integer idDoctor;
    private LocalDateTime dateOfExamination;
    private Diagnostic diagnostic;
    private List<Investigation> investigations;
}
