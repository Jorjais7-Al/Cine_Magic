package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Pelicula;
import com.metahorce.cinemagic.exceptions.InvalidUserException;
import com.metahorce.cinemagic.exceptions.ResourceNotFoundException;
import com.metahorce.cinemagic.repositories.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaServiceImpl implements PeliculaService{

    @Autowired
    PeliculaRepository peliculaRepository;

    @Override
    public List<Pelicula> getPeliculas(){
        return peliculaRepository.findAll();
    }

    @Override
    public Pelicula getPeliculaById(Integer id){
        Pelicula getPelicula = peliculaRepository.findById(id).orElse(null);
        if (getPelicula == null){
            throw new ResourceNotFoundException("No se encontro la pelicula con el id: "+ id);
        }
        return getPelicula;
    }

    @Override
    public Pelicula createPelicula(Pelicula pelicula, String user){
        if(!"ADMINISTRADOR".equals(user)) {
            throw new InvalidUserException("El usuario no puede ejecutar esta petición");
        }
        return peliculaRepository.save(pelicula);
    }

    @Override
    public Pelicula updatePelicula(Integer id, Pelicula pelicula, String user){
        if(!"ADMINISTRADOR".equals(user)) {
            throw new InvalidUserException("El usuario no puede ejecutar esta petición");
        }

        Pelicula updatePelicula = peliculaRepository.findById(id).orElse(null);
        if (updatePelicula == null){
            throw new ResourceNotFoundException("No se encontro la pelicula con el id: "+ id);
        }

        updatePelicula.setTitulo(pelicula.getTitulo());
        updatePelicula.setDuracion(pelicula.getDuracion());
        return peliculaRepository.save(updatePelicula);
    }

    @Override
    public void deletePelicula(Integer id, String user){
        if(!"ADMINISTRADOR".equals(user)) {
            throw new InvalidUserException("El usuario no puede ejecutar esta petición");
        }

        if (!peliculaRepository.existsById(id)){
            throw new ResourceNotFoundException("No se encontro la pelicula con el id: "+ id);
        }
        peliculaRepository.deleteById(id);
    }

}
