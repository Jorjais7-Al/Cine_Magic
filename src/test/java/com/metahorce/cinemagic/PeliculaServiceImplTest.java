package com.metahorce.cinemagic;

import com.metahorce.cinemagic.entities.Pelicula;
import com.metahorce.cinemagic.exceptions.ResourceNotFoundException;
import com.metahorce.cinemagic.repositories.PeliculaRepository;
import com.metahorce.cinemagic.services.PeliculaServiceImpl;
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

public class PeliculaServiceImplTest {

    @Mock
    PeliculaRepository peliculaRepository;

    @InjectMocks
    PeliculaServiceImpl peliculaService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    Pelicula pelicula = new Pelicula();

    @Test
    public void testGetPeliculas(){
        when(peliculaRepository.findAll()).thenReturn(List.of(new Pelicula(), new Pelicula()));
        List<Pelicula> peliculasActuales = peliculaService.getPeliculas();
        assertEquals(2, peliculasActuales.size());
    }

    @Test
    public void testPeliculaById(){
        Integer idPelicula = 1;
        pelicula.setId(idPelicula);

        when(peliculaRepository.findById(idPelicula)).thenReturn(Optional.of(pelicula));

        Pelicula peliculaActual = peliculaService.getPeliculaById(idPelicula);

        assertNotNull(peliculaActual);
        assertEquals(idPelicula, peliculaActual.getId());
    }

    @Test
    public void testPeliculaById_whenPeliculaNotFound(){
        when(peliculaRepository.findById(pelicula.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            peliculaService.getPeliculaById(pelicula.getId());
        });

        assertTrue(exception.getMessage().contains("No se encontro la pelicula con el id: "));
    }

    @Test
    public void testCreatePelicula(){
        pelicula.setId(1);
        pelicula.setTitulo("Hunter x Hunter");
        pelicula.setDuracion(240);

        when(peliculaRepository.save(pelicula)).thenReturn(pelicula);

        Pelicula newPelicula = peliculaService.createPelicula(pelicula);

        assertNotNull(newPelicula);
        assertEquals(1, newPelicula.getId());
    }

    @Test
    public void testUpdatePelicula(){
        when(peliculaRepository.findById(pelicula.getId())).thenReturn(Optional.ofNullable(pelicula));

        pelicula.setDuracion(300);

        when(peliculaRepository.save(pelicula)).thenReturn(pelicula);

        Pelicula updatePelicula = peliculaService.updatePelicula(pelicula.getId(), pelicula);

        assertEquals(300, updatePelicula.getDuracion());
    }

    @Test
    public void testUpdatePelicula_whenPeliculaNotFound(){
        when(peliculaRepository.findById(pelicula.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            peliculaService.updatePelicula(pelicula.getId(), pelicula);
        });

        assertTrue(exception.getMessage().contains("No se encontro la pelicula con el id: "));
    }

    @Test
    public void testDeletePelicula(){
        Integer idPelicula = 1;
        pelicula.setId(idPelicula);

        when(peliculaRepository.existsById(idPelicula)).thenReturn(true);

        peliculaService.deletePelicula(idPelicula);

        verify(peliculaRepository, times(1)).deleteById(idPelicula);
    }

    @Test
    public void testDeletePelicula_whenPeliculaNotFound(){
        when(peliculaRepository.existsById(pelicula.getId())).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            peliculaService.deletePelicula(pelicula.getId());
        });

        assertTrue(exception.getMessage().contains("No se encontro la pelicula con el id: "));
    }

}
