package com.metahorce.cinemagic.controllers;

import com.metahorce.cinemagic.entities.Pelicula;
import com.metahorce.cinemagic.exceptions.InvalidDataException;
import com.metahorce.cinemagic.services.PeliculaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinemagic/peliculas")
public class PeliculaController {

    @Autowired
    PeliculaService peliculaService;

    @GetMapping
    public ResponseEntity<?> getPeliculas(){
        return ResponseEntity.ok(peliculaService.getPeliculas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPeliculaById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(peliculaService.getPeliculaById(id));
    }

    @PostMapping
    public ResponseEntity<?> createPelicula(@RequestParam String user, @Valid @RequestBody Pelicula pelicula, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException("Error de validación", bindingResult);
        }
        return ResponseEntity.ok(peliculaService.createPelicula(pelicula, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePelicula(@RequestParam String user, @PathVariable("id") Integer id,@Valid @RequestBody Pelicula pelicula, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InvalidDataException("Error de validación", bindingResult);
        }
        return ResponseEntity.ok(peliculaService.updatePelicula(id, pelicula, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePelicula(@RequestParam String user, @PathVariable("id") Integer id){
        peliculaService.deletePelicula(id, user);
        return ResponseEntity.ok("La pelicula con el id: "+ id + " se elimino correctamente");
    }

}
