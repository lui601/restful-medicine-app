package com.example.medicaloffice.persistence.repository;

import com.example.medicaloffice.business.dto.Status;
import com.example.medicaloffice.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Integer> {
    List<AppointmentEntity> findByIdDoctor(Integer idDoctor);
    List<AppointmentEntity> findByIdPatient(String idPatient);
    @Query("select a from AppointmentEntity a where a.idPatient = ?1 and DATE_FORMAT(a.dateOfAppointment, '%d-%m-%Y %T') = ?2 and (a.status = ?3 or ?3 = null) and (a.idDoctor = ?4 or ?4 = null)")
    List<AppointmentEntity> getByIdPatientAndDateOfAppointmentAndStatusAndIdDoctor(String cnp, String dateOfAppointment, Status status, Integer idDoctor);
    @Query("select a from AppointmentEntity a where a.idPatient = ?1 and day(a.dateOfAppointment) = ?2 and (a.status = ?3 or ?3 = null) and (a.idDoctor = ?4 or ?4 = null)")
    List<AppointmentEntity> getByIdPatientAndDayAndStatusAndIdDoctor(String cnp, int day, Status status, Integer idDoctor);
    @Query("select a from AppointmentEntity a where a.idPatient = ?1 and month(a.dateOfAppointment) = ?2 and (a.status = ?3 or ?3 = null) and (a.idDoctor = ?4 or ?4 = null)")
    List<AppointmentEntity> getByIdPatientAndMonthAndStatusAndIdDoctor(String cnp, int month, Status status, Integer idDoctor);
    @Query("select a from AppointmentEntity a where a.idPatient = ?1 and (a.status = ?2 or ?2 = null) and (a.idDoctor = ?3 or ?3 = null)")
    List<AppointmentEntity> getByIdPatientAndStatusAndIdDoctor(String cnp, Status status, Integer idDoctor);
    @Query("select a from AppointmentEntity a where a.idPatient = ?1 or a.idDoctor = ?2")
    List<AppointmentEntity> getByIdPatientOrIdDoctor(String cnp, Integer idDoctor);
    @Query("select a from AppointmentEntity a where a.idPatient = ?1 and a.idDoctor = ?2")
    List<AppointmentEntity> getByIdPatientAndIdDoctor(String cnp, Integer idDoctor);

}
