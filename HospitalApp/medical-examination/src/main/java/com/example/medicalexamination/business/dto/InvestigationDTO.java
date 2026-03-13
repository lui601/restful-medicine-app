package com.example.medicalexamination.business.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class InvestigationDTO implements Serializable {
    private Integer id;
    private String name;
    private Integer processingTime;
    private String result;
}
