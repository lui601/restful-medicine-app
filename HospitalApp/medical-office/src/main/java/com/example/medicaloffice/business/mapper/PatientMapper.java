package com.example.medicaloffice.business.mapper;

import com.example.medicaloffice.business.dto.PatientDTO;
import com.example.medicaloffice.persistence.entity.PatientEntity;
import org.mapstruct.Mapper;

@Mapper
public interface PatientMapper {
    PatientDTO toDTO(PatientEntity patientEntity);
    PatientEntity toEntity(PatientDTO patientDTO);
}
