package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Calificacion;
import com.metahorce.cinemagic.repositories.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionServiceImpl implements CalificacionService {

    @Autowired
    CalificacionRepository calificacionRepository;

    @Override
    public List<Calificacion> getCalificaciones(){
        return calificacionRepository.findAll();
    }

    @Override
    public Calificacion getCalificacionById(Integer id){
        Calificacion obtenerCalificacion = calificacionRepository.findById(id).orElse(null);
        if (obtenerCalificacion == null){
            return null;
        }
        return obtenerCalificacion;
    }

    @Override
    public Calificacion createCalificacion(Calificacion calificacion){
        return calificacionRepository.save(calificacion);
    }

    @Override
    public Calificacion updateCalificacion(Integer id, Calificacion calificacion){
        Calificacion calificacionUpdate = calificacionRepository.findById(id).orElse(null);
        if (calificacionUpdate == null){
            return null;
        }
        calificacionUpdate.setCalificacion(calificacion.getCalificacion());
        calificacionUpdate.setResenia(calificacion.getResenia());
        return calificacionRepository.save(calificacionUpdate);
    }

    @Override
    public void deleteCalificacion(Integer id){
        calificacionRepository.deleteById(id);
    }


}
