package com.example.medicaloffice.business.mapper;

import com.example.medicaloffice.business.dto.PhysicianDTO;
import com.example.medicaloffice.persistence.entity.PhysicianEntity;
import org.mapstruct.Mapper;

@Mapper
public interface PhysicianMapper {
    PhysicianDTO toDTO(PhysicianEntity physicianEntity);
    PhysicianEntity toEntity(PhysicianDTO physicianDTO);
}
