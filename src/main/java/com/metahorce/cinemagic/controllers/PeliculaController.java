package com.metahorce.cinemagic.controllers;

import com.metahorce.cinemagic.entities.Pelicula;
import com.metahorce.cinemagic.services.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createPelicula(@RequestBody Pelicula pelicula){
        return ResponseEntity.ok(peliculaService.createPelicula(pelicula));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePelicula(@PathVariable("id") Integer id, @RequestBody Pelicula pelicula){
        return ResponseEntity.ok(peliculaService.updatePelicula(id, pelicula));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePelicula(@PathVariable("id") Integer id){
        peliculaService.deletePelicula(id);
        return ResponseEntity.ok("La pelicula con el id: "+ id + " se elimino correctamente");
    }

}
