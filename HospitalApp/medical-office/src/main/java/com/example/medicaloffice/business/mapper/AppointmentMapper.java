package com.example.medicaloffice.business.mapper;

import com.example.medicaloffice.business.dto.AppointmentDTO;
import com.example.medicaloffice.persistence.entity.AppointmentEntity;
import org.mapstruct.Mapper;

@Mapper
public interface AppointmentMapper {
    AppointmentDTO toDTO(AppointmentEntity appointmentEntity);
    AppointmentEntity toEntity(AppointmentDTO appointmentDTO);
}
