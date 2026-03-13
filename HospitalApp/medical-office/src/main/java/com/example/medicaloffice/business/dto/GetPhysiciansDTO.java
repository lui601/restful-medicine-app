package com.example.medicaloffice.business.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPhysiciansDTO {
    private Iterable<PhysicianDTO> physicians;
    private Integer items;
}

