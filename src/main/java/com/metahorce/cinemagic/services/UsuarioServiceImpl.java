package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Usuario;
import com.metahorce.cinemagic.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioById(Integer id){
        Usuario getUsuario = usuarioRepository.findById(id).orElse(null);
        if (getUsuario == null){
            return null;
        }
        return getUsuario;
    }

    @Override
    public Usuario createUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Integer id, Usuario usuario){
        Usuario updateUsuario = usuarioRepository.findById(id).orElse(null);
        if (updateUsuario == null){
            return null;
        }
        updateUsuario.setNombre(usuario.getNombre());
        updateUsuario.setCorreo(usuario.getCorreo());
        updateUsuario.setTipoUsuario(usuario.getTipoUsuario());
        return usuarioRepository.save(updateUsuario);
    }

    @Override
    public void deleteUsuario(Integer id){
        usuarioRepository.deleteById(id);
    }

}
