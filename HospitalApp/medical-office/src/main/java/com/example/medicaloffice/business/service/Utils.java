package com.example.medicaloffice.business.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;
import static java.util.Calendar.DATE;

public class Utils {
    public static void validateDateOfBirth(Date dateOfBirth) {
        Date now = new Date();

        Calendar a = getCalendar(dateOfBirth);
        Calendar b = getCalendar(now);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }

        if(diff < 18){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid date of birth");
        }
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static String extractToken(String authorizationHeader){
        String bearer = authorizationHeader.split(" ")[0];

        if(!bearer.equals("Bearer")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong authorization header value"); // bad request pt ca headerul e gresit
        }

        String token = authorizationHeader.split(" ")[1];

        return token;
    }
}
