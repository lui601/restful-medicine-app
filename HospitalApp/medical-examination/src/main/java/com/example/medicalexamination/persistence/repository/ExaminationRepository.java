package com.example.medicalexamination.persistence.repository;

import com.example.medicalexamination.persistence.document.Examination;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExaminationRepository extends MongoRepository<Examination, String> {
    // @Query("select a from Examination a where a.idPatient = ?1 and a.idDoctor = ?2 and DATE_FORMAT(a.dateOfAppointment, '%d-%m-%Y %T') = ?3")
    @Query("{ 'idPatient': ?0, 'idDoctor': ?1, 'dateOfExamination': ?2 }")
    List<Examination> getByIdPatientAndIdDoctorAndDateOfAppointment(String cnp, Integer idDoctor, LocalDateTime dateOfExamination);

    @Query("{ 'idDoctor': ?0 }")
    List<Examination> getByIdDoctor(Integer idDoctor);

    @Query("{ '_id': ?0 }")
    Optional<Examination> getById(String id);

}
