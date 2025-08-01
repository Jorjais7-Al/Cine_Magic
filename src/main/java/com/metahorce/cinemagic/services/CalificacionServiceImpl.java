package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Calificacion;
import com.metahorce.cinemagic.entities.Pelicula;
import com.metahorce.cinemagic.entities.Usuario;
import com.metahorce.cinemagic.exceptions.ResourceNotFoundException;
import com.metahorce.cinemagic.repositories.CalificacionRepository;
import com.metahorce.cinemagic.repositories.PeliculaRepository;
import com.metahorce.cinemagic.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionServiceImpl implements CalificacionService {

    @Autowired
    CalificacionRepository calificacionRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PeliculaRepository peliculaRepository;

    @Override
    public List<Calificacion> getCalificaciones(){
        return calificacionRepository.findAll();
    }

    @Override
    public Calificacion getCalificacionById(Integer id){
        Calificacion obtenerCalificacion = calificacionRepository.findById(id).orElse(null);
        if (obtenerCalificacion == null){
            throw new ResourceNotFoundException("No se encontro la calificación con el id: "+ id);
        }
        return obtenerCalificacion;
    }

    @Override
    public Calificacion createCalificacion(Calificacion calificacion){

        Integer idUsuario = calificacion.getUsuario().getId();
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null){
            throw new ResourceNotFoundException("No se encontro el usuario con el id: "+ idUsuario);
        }

        Integer idPelicula = calificacion.getPelicula().getId();
        Pelicula pelicula = peliculaRepository.findById(idPelicula).orElse(null);
        if (pelicula == null){
            throw new ResourceNotFoundException("No se encontro la pelicula con el id: "+ idPelicula);
        }

        calificacion.setUsuario(usuario);
        calificacion.setPelicula(pelicula);
        return calificacionRepository.save(calificacion);

    }

    @Override
    public Calificacion updateCalificacion(Integer id, Calificacion calificacion){
        Calificacion calificacionUpdate = calificacionRepository.findById(id).orElse(null);
        if (calificacionUpdate == null){
            throw new ResourceNotFoundException("No se encontro la calificación con el id: "+ id);
        }

        Integer idUsuario = calificacion.getUsuario().getId();
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null){
            throw new ResourceNotFoundException("No se encontro el usuario con el id: "+ idUsuario);
        }

        Integer idPelicula = calificacion.getPelicula().getId();
        Pelicula pelicula = peliculaRepository.findById(idPelicula).orElse(null);
        if (pelicula == null){
            throw new ResourceNotFoundException("No se encontro la pelicula con el id: "+ idPelicula);
        }

        calificacionUpdate.setPelicula(calificacion.getPelicula());
        calificacionUpdate.setUsuario(calificacion.getUsuario());
        calificacionUpdate.setCalificacion(calificacion.getCalificacion());
        calificacionUpdate.setResenia(calificacion.getResenia());
        return calificacionRepository.save(calificacionUpdate);
    }

    @Override
    public void deleteCalificacion(Integer id){
        if (!calificacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontro la calificación con el id: " + id);
        }
        calificacionRepository.deleteById(id);
    }


}
