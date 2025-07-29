package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Calificacion;

import java.util.List;

public interface CalificacionService {

    List<Calificacion> getCalificaciones();

    Calificacion getCalificacionById(Integer id);

    Calificacion createCalificacion(Calificacion calificacion);

    Calificacion updateCalificacion(Integer id, Calificacion calificacion);

    void deleteCalificacion(Integer id);

}
