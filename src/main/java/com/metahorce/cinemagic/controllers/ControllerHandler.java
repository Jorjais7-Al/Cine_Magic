package com.metahorce.cinemagic.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.metahorce.cinemagic.exceptions.DuplicateDataException;
import com.metahorce.cinemagic.exceptions.InvalidDataException;
import com.metahorce.cinemagic.exceptions.InvalidUserException;
import com.metahorce.cinemagic.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<?> validData(InvalidDataException invalidDataException){
        Map<String, String> errors = new HashMap<>();
        invalidDataException.getBindingResult().getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(value = InvalidUserException.class)
    public ResponseEntity<?> invalidUserException(InvalidUserException invalidUserException){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(invalidUserException.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFound(ResourceNotFoundException resourceNotFoundException){
        Map<String, String> errors = new HashMap<>();
        errors.put("error", resourceNotFoundException.getMessage());
        return ResponseEntity.status(404).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> invalidJson(HttpMessageNotReadableException httpMessageNotReadableException) {
        Map<String, String> error = new HashMap<>();
        if (httpMessageNotReadableException.getCause() instanceof InvalidFormatException cause) {
            if (cause.getTargetType().isEnum()) {
                error.put("error", "El valor proporcionado no es válido. Se esperaba uno de: "
                        + Arrays.toString(cause.getTargetType().getEnumConstants()));
            } else {
                error.put("error", "Uno o más campos tienen un tipo de dato incorrecto");
            }
        } else {
            error.put("error", "El cuerpo de la solicitud tiene un formato JSON inválido o mal estructurado");
        }
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<?> duplicateDataException(DuplicateDataException duplicateDataException){
        Map<String, String> error = new HashMap<>();
        error.put("error", "El correo electrónico ya está registrado");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}
