package com.metahorce.cinemagic.controllers;

import com.metahorce.cinemagic.excepcions.InvalidUserExcepcion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(value = InvalidUserExcepcion.class)
    public ResponseEntity<?> invalidUserExcepcion(InvalidUserExcepcion invalidUserExcepcion){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(invalidUserExcepcion.getMessage());
    }

}
