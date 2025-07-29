package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Pelicula;
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
            return null;
        }
        return getPelicula;
    }

    @Override
    public Pelicula createPelicula(Pelicula pelicula){
        return peliculaRepository.save(pelicula);
    }

    @Override
    public Pelicula updatePelicula(Integer id, Pelicula pelicula){
        Pelicula updatePelicula = peliculaRepository.findById(id).orElse(null);
        if (updatePelicula == null){
            return null;
        }
        updatePelicula.setTitulo(pelicula.getTitulo());
        updatePelicula.setDuracion(pelicula.getDuracion());
        return peliculaRepository.save(updatePelicula);
    }

    @Override
    public void deletePelicula(Integer id){
        peliculaRepository.deleteById(id);
    }

}
