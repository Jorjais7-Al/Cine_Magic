package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Funcion;
import com.metahorce.cinemagic.entities.Pelicula;
import com.metahorce.cinemagic.exceptions.InvalidUserException;
import com.metahorce.cinemagic.exceptions.ResourceNotFoundException;
import com.metahorce.cinemagic.repositories.FuncionRepository;
import com.metahorce.cinemagic.repositories.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionServiceImpl implements FuncionService{

    @Autowired
    FuncionRepository funcionRepository;

    @Autowired
    PeliculaRepository peliculaRepository;

    @Override
    public List<Funcion> getFunciones(){
        return funcionRepository.findAll();
    }

    @Override
    public Funcion getFuncionById(Integer id){
        Funcion getFuncion = funcionRepository.findById(id).orElse(null);
        if (getFuncion == null){
            throw new ResourceNotFoundException("No se encontro la función con el id: "+ id);
        }
        return getFuncion;
    }

    @Override
    public Funcion createFuncion(Funcion funcion, String user){

        if(!"ADMINISTRADOR".equals(user)) {
            throw new InvalidUserException("El usuario no puede ejecutar esta petición");
        }

        Integer idPelicula = funcion.getPelicula().getId();
        Pelicula pelicula = peliculaRepository.findById(idPelicula).orElse(null);
        if (pelicula == null) {
            throw new ResourceNotFoundException("No se encontro la pelicula con el id: " + idPelicula);
        }

        funcion.setPelicula(pelicula);
        return funcionRepository.save(funcion);

    }

    @Override
    public Funcion updateFuncion(Integer id, Funcion funcion){
        Funcion updateFuncion = funcionRepository.findById(id).orElse(null);
        if (updateFuncion == null){
            throw new ResourceNotFoundException("No se encontro la función con el id: "+ id);
        }

        Integer idPelicula = funcion.getPelicula().getId();
        Pelicula pelicula = peliculaRepository.findById(idPelicula).orElse(null);
        if (pelicula == null) {
            throw new ResourceNotFoundException("No se encontro la pelicula con el id: " + idPelicula);
        }

        updateFuncion.setFecha(funcion.getFecha());
        updateFuncion.setHora(funcion.getHora());
        updateFuncion.setPelicula(funcion.getPelicula());
        return funcionRepository.save(updateFuncion);
    }

    @Override
    public void deleteFuncion(Integer id){
        funcionRepository.deleteById(id);
    }

}
