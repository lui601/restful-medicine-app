package com.example.medicalexamination.business.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Link implements Serializable {
   private String rel;
   private String href;
}