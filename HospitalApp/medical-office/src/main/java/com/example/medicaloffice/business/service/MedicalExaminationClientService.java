package com.example.medicaloffice.business.service;

import com.example.medicaloffice.business.dto.AppointmentDTO;
import com.example.medicaloffice.business.dto.ExaminationResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MedicalExaminationClientService {

    private static final String DELETE_MEDICAL_EXAMINATION_URL = "http://localhost:8081/api/medical-examination/examinations/%s";
    private static final String GET_MEDICAL_EXAMINATION_URL = "http://localhost:8081/api/medical-examination/patients/%s/examinations?idDoctor=%s&dateOfExamination=%s";


    public List<ExaminationResponse> getExaminationsByAppointment(String token, AppointmentDTO appointmentDTO) {
        // obtinem appointment din celalalt serviciu
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy%20HH:mm:ss");
        String formattedDateOfExamination = appointmentDTO.getDateOfAppointment().format(formatter);
        // System.out.println(formattedDateOfExamination);
        String url = String.format(
                GET_MEDICAL_EXAMINATION_URL,
                appointmentDTO.getIdPatient(), appointmentDTO.getIdDoctor(), formattedDateOfExamination);
        ResponseEntity<List<ExaminationResponse>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<ExaminationResponse>>(){});
        return response.getBody();
    }

    public void deleteExamination(String token, String examinationId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        String url = String.format(DELETE_MEDICAL_EXAMINATION_URL, examinationId);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, new ParameterizedTypeReference<>() {});
    }
}
