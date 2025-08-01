package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Funcion;

import java.util.List;

public interface FuncionService {

    List<Funcion> getFunciones();

    Funcion getFuncionById(Integer id);

    Funcion createFuncion(Funcion funcion, String user);

    Funcion updateFuncion(Integer id, Funcion funcion, String user);

    void deleteFuncion(Integer id, String user);

}
