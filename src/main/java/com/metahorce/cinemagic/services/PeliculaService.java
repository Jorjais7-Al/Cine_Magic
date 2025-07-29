package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Pelicula;

import java.util.List;

public interface PeliculaService {

    List<Pelicula> getPeliculas();

    Pelicula getPeliculaById(Integer id);

    Pelicula createPelicula(Pelicula pelicula);

    Pelicula updatePelicula(Integer id, Pelicula pelicula);

    void deletePelicula(Integer id);

}
