package com.example.medicaloffice.presentation.advice;

import io.grpc.Status;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class Advice {
    @ResponseBody
    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<String> translate(PropertyValueException ex) {
        return new ResponseEntity<>(ex.getMessage(), UNPROCESSABLE_ENTITY); //de afisat doar mesaj + lista campuri nedefinite
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> translate(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ex.getMessage(), UNPROCESSABLE_ENTITY);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> translate(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(ex.getMessage(), UNPROCESSABLE_ENTITY);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> translate(ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getMessage(), UNPROCESSABLE_ENTITY);
    }

    @ResponseBody
    @ExceptionHandler(io.grpc.StatusRuntimeException.class)
    public ResponseEntity<String> translate(io.grpc.StatusRuntimeException ex) {
        Status.Code code =  ex.getStatus().getCode();
        String message = ex.getMessage();

        if (code.equals(Status.Code.UNAUTHENTICATED)) {
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        } else if (code.equals(Status.Code.PERMISSION_DENIED)) {
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
