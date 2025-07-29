package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Usuario;

import java.util.List;

public interface UsuarioService {

    List<Usuario> getUsuarios();

    Usuario getUsuarioById(Integer id);

    Usuario createUsuario(Usuario usuario);

    Usuario updateUsuario(Integer id, Usuario usuario);

    void deleteUsuario(Integer id);
}
