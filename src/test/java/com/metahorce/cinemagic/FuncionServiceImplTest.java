package com.metahorce.cinemagic;

import com.metahorce.cinemagic.entities.Funcion;
import com.metahorce.cinemagic.entities.Pelicula;
import com.metahorce.cinemagic.exceptions.InvalidUserException;
import com.metahorce.cinemagic.exceptions.ResourceNotFoundException;
import com.metahorce.cinemagic.repositories.FuncionRepository;
import com.metahorce.cinemagic.repositories.PeliculaRepository;
import com.metahorce.cinemagic.services.FuncionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FuncionServiceImplTest {

    @Mock
    FuncionRepository funcionRepository;

    @Mock
    PeliculaRepository peliculaRepository;

    @InjectMocks
    FuncionServiceImpl funcionService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    Funcion funcion = new Funcion();
    Pelicula pelicula = new Pelicula();

    @Test
    public void testGetFunciones(){
        when(funcionRepository.findAll()).thenReturn(List.of(new Funcion(), new Funcion()));
        List<Funcion> funcionesActuales = funcionService.getFunciones();
        assertEquals(2, funcionesActuales.size());
    }

    @Test
    public void testFuncionById(){
        Integer idFuncion = 1;
        funcion.setId(idFuncion);

        when(funcionRepository.findById(idFuncion)).thenReturn(Optional.of(funcion));

        Funcion funcionActual = funcionService.getFuncionById(idFuncion);

        assertNotNull(funcionActual);
        assertEquals(idFuncion, funcionActual.getId());
    }

    @Test
    public void testFuncionById_whenFuncionNotFound(){
        when(funcionRepository.findById(funcion.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            funcionService.getFuncionById(funcion.getId());
        });

        assertTrue(exception.getMessage().contains("No se encontro la función con el id: "));
    }

    @Test
    public void testCreateFuncion(){
        pelicula.setId(1);
        funcion.setId(1);
        funcion.setHora("07:40:00");
        funcion.setFecha("2025-07-31");
        funcion.setPelicula(pelicula);

        // MOCKS para evitar la exception ResourceNotFoundException
        when(peliculaRepository.findById(1)).thenReturn(Optional.of(pelicula));
        when(funcionRepository.save(funcion)).thenReturn(funcion);

        Funcion newFuncion = funcionService.createFuncion(funcion, "ADMINISTRADOR");

        assertNotNull(newFuncion);
        assertEquals(1, newFuncion.getId());
    }

    @Test
    public void testCreateFuncion_whenTipoUsuarioIsDifferent(){
        pelicula.setId(1);
        funcion.setId(1);
        funcion.setHora("07:40:00");
        funcion.setFecha("2025-07-31");
        funcion.setPelicula(pelicula);

        // MOCKS para evitar la exception ResourceNotFoundException
        when(peliculaRepository.findById(1)).thenReturn(Optional.of(pelicula));
        when(funcionRepository.save(funcion)).thenReturn(funcion);

        Exception exception = assertThrows(InvalidUserException.class, () -> {
            funcionService.createFuncion(funcion, "ESPECTADOR");
        });

        assertTrue(exception.getMessage().contains("El usuario no puede ejecutar esta petición"));
    }

    @Test
    public void testCreateFuncion_whenPeliculaNotFound(){
        funcion.setId(1);
        funcion.setHora("07:40:00");
        funcion.setFecha("2025-07-31");
        funcion.setPelicula(pelicula);

        // MOCKS para evitar la exception ResourceNotFoundException
        when(peliculaRepository.findById(funcion.getPelicula().getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            funcionService.createFuncion(funcion, "ADMINISTRADOR");
        });

        assertTrue(exception.getMessage().contains("No se encontro la pelicula con el id: "));
    }

    @Test
    public void testUpdateFuncion(){
        pelicula.setId(1);
        funcion.setPelicula(pelicula);

        when(peliculaRepository.findById(1)).thenReturn(Optional.of(pelicula));
        when(funcionRepository.findById(funcion.getId())).thenReturn(Optional.ofNullable(funcion));

        funcion.setHora("08:40:00");

        when(funcionRepository.save(funcion)).thenReturn(funcion);

        Funcion updateFuncion = funcionService.updateFuncion(funcion.getId(), funcion);

        assertEquals("08:40:00", updateFuncion.getHora());
    }

    @Test
    public void testUpdateFuncion_whenFuncionNotFound(){
        when(funcionRepository.findById(funcion.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            funcionService.updateFuncion(funcion.getId(), funcion);
        });

        assertTrue(exception.getMessage().contains("No se encontro la función con el id: "));
    }

    @Test
    public void testUpdateFuncion_whenPeliculaNotFound(){
        funcion.setPelicula(pelicula);
        when(peliculaRepository.findById(funcion.getPelicula().getId())).thenReturn(Optional.empty());

        when(funcionRepository.findById(funcion.getId())).thenReturn(Optional.ofNullable(funcion));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            funcionService.updateFuncion(funcion.getId(), funcion);
        });

        assertTrue(exception.getMessage().contains("No se encontro la pelicula con el id: "));
    }

    @Test
    public void testDeleteFuncion(){
        Integer idFuncion = 1;
        funcion.setId(idFuncion);

        when(funcionRepository.existsById(idFuncion)).thenReturn(true);

        funcionService.deleteFuncion(idFuncion);

        verify(funcionRepository, times(1)).deleteById(idFuncion);
    }

    @Test
    public void testDeleteFuncion_whenFuncionNotFound(){
        when(funcionRepository.existsById(funcion.getId())).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            funcionService.deleteFuncion(funcion.getId());
        });

        assertTrue(exception.getMessage().contains("No se encontro la función con el id: "));
    }

}
