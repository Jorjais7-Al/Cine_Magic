package com.metahorce.cinemagic.controllers;

import com.metahorce.cinemagic.entities.Calificacion;
import com.metahorce.cinemagic.entities.Usuario;
import com.metahorce.cinemagic.services.CalificacionService;
import com.metahorce.cinemagic.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinemagic/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> getUsuarios(){
        return ResponseEntity.ok(usuarioService.getUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.createUsuario(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable("id") Integer id, @RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.updateUsuario(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable("id") Integer id){
        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok("El usuario con el id: "+ id + " se elimino correctamente");
    }

}
