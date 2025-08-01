package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Usuario;
import com.metahorce.cinemagic.exceptions.DuplicateDataException;
import com.metahorce.cinemagic.exceptions.ResourceNotFoundException;
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
            throw new ResourceNotFoundException("No se encontro el usuario con el id: "+ id);
        }
        return getUsuario;
    }

    @Override
    public Usuario createUsuario(Usuario usuario){
        if (usuarioRepository.existsBycorreo(usuario.getCorreo())) {
            throw new DuplicateDataException("El correo electrónico ya está registrado");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Integer id, Usuario usuario){
        Usuario updateUsuario = usuarioRepository.findById(id).orElse(null);
        if (updateUsuario == null){
            throw new ResourceNotFoundException("No se encontro el usuario con el id: "+ id);
        }

        updateUsuario.setNombre(usuario.getNombre());
        updateUsuario.setCorreo(usuario.getCorreo());
        updateUsuario.setTipoUsuario(usuario.getTipoUsuario());
        return usuarioRepository.save(updateUsuario);
    }

    @Override
    public void deleteUsuario(Integer id){
        if (!usuarioRepository.existsById(id)){
            throw new ResourceNotFoundException("No se encontro el usuario con el id: "+ id);
        }
        usuarioRepository.deleteById(id);
    }

}
