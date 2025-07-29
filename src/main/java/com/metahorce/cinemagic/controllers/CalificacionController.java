package com.metahorce.cinemagic.controllers;

import com.metahorce.cinemagic.entities.Calificacion;
import com.metahorce.cinemagic.services.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinemagic/calificaciones")
public class CalificacionController {

    @Autowired
    CalificacionService calificacionService;

    @GetMapping
    public ResponseEntity<?> getCalificaciones(){
        return ResponseEntity.ok(calificacionService.getCalificaciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCalificacionById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(calificacionService.getCalificacionById(id));
    }

    @PostMapping
    public ResponseEntity<?> createCalificacion(@RequestBody Calificacion calificacion){
        return ResponseEntity.ok(calificacionService.createCalificacion(calificacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCalificacion(@PathVariable("id") Integer id, @RequestBody Calificacion calificacion){
        return ResponseEntity.ok(calificacionService.updateCalificacion(id, calificacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCalificacion(@PathVariable("id") Integer id){
        calificacionService.deleteCalificacion(id);
        return ResponseEntity.ok("La Calificación con el id: "+ id + " se elimino correctamente");
    }

}
