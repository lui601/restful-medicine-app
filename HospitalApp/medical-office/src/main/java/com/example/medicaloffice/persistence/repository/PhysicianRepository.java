package com.example.medicaloffice.persistence.repository;

import com.example.medicaloffice.persistence.entity.PhysicianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PhysicianRepository extends JpaRepository<PhysicianEntity, Integer> {
}
