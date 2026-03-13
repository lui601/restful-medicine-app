package com.example.medicaloffice.persistence.entity;

import com.example.medicaloffice.business.dto.Specialization;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="physician")
public class PhysicianEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idDoctor;

    @Column(nullable = false)
    private Integer idUser;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    //DE VERIFICAT
    @Pattern(regexp="(^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$)")
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Pattern(regexp="(^(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}$)")
    private String phoneNumber;

    @Column(nullable = false)
    private Specialization specialization;
}
