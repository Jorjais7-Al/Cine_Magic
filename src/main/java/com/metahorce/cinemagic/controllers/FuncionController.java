package com.metahorce.cinemagic.controllers;

import com.metahorce.cinemagic.entities.Funcion;
import com.metahorce.cinemagic.exceptions.InvalidDataException;
import com.metahorce.cinemagic.services.FuncionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinemagic/funciones")
public class FuncionController {

    @Autowired
    FuncionService funcionService;

    @GetMapping
    public ResponseEntity<?> getFunciones(){
        return ResponseEntity.ok(funcionService.getFunciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFuncionById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(funcionService.getFuncionById(id));
    }


    @PostMapping
    public ResponseEntity<?> createFuncion(@RequestParam String user, @Valid @RequestBody Funcion funcion, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InvalidDataException("Error de validación", bindingResult);
        }
        return ResponseEntity.ok(funcionService.createFuncion(funcion, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFuncion(@PathVariable("id") Integer id,@Valid @RequestBody Funcion funcion, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InvalidDataException("Error de validación", bindingResult);
        }
        return ResponseEntity.ok(funcionService.updateFuncion(id, funcion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFuncion(@PathVariable("id") Integer id){
        funcionService.deleteFuncion(id);
        return ResponseEntity.ok("La función con el id: "+ id + " se elimino correctamente");
    }

}
