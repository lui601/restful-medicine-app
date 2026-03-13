package com.example.medicalexamination.business.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;

public class Utils {
    public static String extractToken(String authorizationHeader){
        String bearer = authorizationHeader.split(" ")[0];

        if(!bearer.equals("Bearer")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); // bad request pt ca headerul e gresit
        }

        String token = authorizationHeader.split(" ")[1];

        return token;
    }
}
