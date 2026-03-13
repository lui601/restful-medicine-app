package com.example.medicalexamination.business.service;

import com.example.medicalexamination.business.dto.PatientResponse;
import com.example.medicalexamination.business.dto.PhysicianResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class MedicalOfficeClientService {
    private RestTemplate restTemplate;
    private String baseUrl = "http://localhost:8080/api/medical-office";

    MedicalOfficeClientService(){
        restTemplate = new RestTemplate();
    }

    public PatientResponse getPatientByCnp(String token, String cnp){
        String url = String.format("%s/patients/%s", baseUrl, cnp);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<PatientResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<PatientResponse>() {});
        PatientResponse patientResponse = response.getBody();

        return patientResponse;
    }

    public PhysicianResponse getPhysicianById(String token, Integer idDoctor){
        String url = String.format("%s/physicians/%s", baseUrl, idDoctor);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<PhysicianResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<PhysicianResponse>() {});
        PhysicianResponse physicianResponse = response.getBody();

        return physicianResponse;
    }
}
