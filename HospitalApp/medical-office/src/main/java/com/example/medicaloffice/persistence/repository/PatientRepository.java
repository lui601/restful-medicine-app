package com.example.medicaloffice.persistence.repository;

import com.example.medicaloffice.persistence.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientEntity, String> {
    // pentru validarile de la examinations
    Optional<PatientEntity> findByIdUser(Integer idUser);
}
