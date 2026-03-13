package com.example.medicalexamination.persistence.document;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Investigation {

    @NotNull
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Integer processingTime;
    @NotNull
    private String result;
}
