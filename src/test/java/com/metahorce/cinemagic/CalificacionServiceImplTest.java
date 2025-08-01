package com.metahorce.cinemagic;

import com.metahorce.cinemagic.entities.Calificacion;
import com.metahorce.cinemagic.entities.Pelicula;
import com.metahorce.cinemagic.entities.Usuario;
import com.metahorce.cinemagic.exceptions.ResourceNotFoundException;
import com.metahorce.cinemagic.repositories.CalificacionRepository;
import com.metahorce.cinemagic.repositories.PeliculaRepository;
import com.metahorce.cinemagic.repositories.UsuarioRepository;
import com.metahorce.cinemagic.services.CalificacionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CalificacionServiceImplTest {

    @Mock
    CalificacionRepository calificacionRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    PeliculaRepository peliculaRepository;

    @InjectMocks
    CalificacionServiceImpl calificacionService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    Calificacion calificacion = new Calificacion();
    Usuario usuario = new Usuario();
    Pelicula pelicula = new Pelicula();

    @Test
    public void testGetCalificaciones(){
        when(calificacionRepository.findAll()).thenReturn(List.of(new Calificacion(), new Calificacion()));
        List<Calificacion> calificacionesActuales = calificacionService.getCalificaciones();
        assertEquals(2, calificacionesActuales.size());
    }

    @Test
    public void testCalificacionById(){
        Integer idCalificacion = 1;
        calificacion.setId(idCalificacion);

        when(calificacionRepository.findById(idCalificacion)).thenReturn(Optional.of(calificacion));

        Calificacion calificacionActual = calificacionService.getCalificacionById(idCalificacion);

        assertNotNull(calificacionActual);
        assertEquals(idCalificacion, calificacionActual.getId());
    }

    @Test
    public void testCalificacionById_whenCalificacionNotFound(){
        when(calificacionRepository.findById(calificacion.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            calificacionService.getCalificacionById(calificacion.getId());
        });

        assertTrue(exception.getMessage().contains("No se encontro la calificación con el id: "));
    }

    @Test
    public void testCreateCalificacion(){
        usuario.setId(1);
        pelicula.setId(1);
        calificacion.setId(1);
        calificacion.setCalificacion(5);
        calificacion.setResenia("Estuvo buena la pelicula");
        calificacion.setPelicula(pelicula);
        calificacion.setUsuario(usuario);

        // MOCKS para evitar la exception ResourceNotFoundException
        when(peliculaRepository.findById(1)).thenReturn(Optional.of(pelicula));
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(calificacionRepository.save(calificacion)).thenReturn(calificacion);

        Calificacion newCalificacion = calificacionService.createCalificacion(calificacion);

        assertNotNull(newCalificacion);
        assertEquals(1, newCalificacion.getId());
    }

    @Test
    public void testCreateCalificacion_whenUsuarioNotFound(){
        calificacion.setId(1);
        calificacion.setCalificacion(5);
        calificacion.setResenia("Estuvo buena la pelicula");
        calificacion.setUsuario(usuario);

        // MOCKS para evitar la exception ResourceNotFoundException
        when(usuarioRepository.findById(calificacion.getUsuario().getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            calificacionService.createCalificacion(calificacion);
        });

        assertTrue(exception.getMessage().contains("No se encontro el usuario con el id: "));
    }

    @Test
    public void testCreateCalificacion_whenPeliculaNotFound(){
        usuario.setId(1);
        calificacion.setId(1);
        calificacion.setCalificacion(5);
        calificacion.setResenia("Estuvo buena la pelicula");
        calificacion.setPelicula(pelicula);
        calificacion.setUsuario(usuario);

        // MOCKS para evitar la exception ResourceNotFoundException
        when(peliculaRepository.findById(calificacion.getPelicula().getId())).thenReturn(Optional.empty());
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(calificacionRepository.save(calificacion)).thenReturn(calificacion);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            calificacionService.createCalificacion(calificacion);
        });

        assertTrue(exception.getMessage().contains("No se encontro la pelicula con el id: "));
    }

    @Test
    public void testUpdateCalificacion(){
        usuario.setId(1);
        pelicula.setId(1);
        calificacion.setPelicula(pelicula);
        calificacion.setUsuario(usuario);

        when(peliculaRepository.findById(1)).thenReturn(Optional.of(pelicula));
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(calificacionRepository.findById(calificacion.getId())).thenReturn(Optional.ofNullable(calificacion));

        calificacion.setCalificacion(4);

        when(calificacionRepository.save(calificacion)).thenReturn(calificacion);

        Calificacion updateCalificacion = calificacionService.updateCalificacion(calificacion.getId(), calificacion);

        assertEquals(4, updateCalificacion.getCalificacion());
    }

    @Test
    public void testUpdateCalificacion_whenCalificacionNotFound(){
        usuario.setId(1);
        pelicula.setId(1);
        calificacion.setPelicula(pelicula);
        calificacion.setUsuario(usuario);

        when(peliculaRepository.findById(1)).thenReturn(Optional.of(pelicula));
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(calificacionRepository.findById(calificacion.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            calificacionService.updateCalificacion(calificacion.getId(), calificacion);
        });

        assertTrue(exception.getMessage().contains("No se encontro la calificación con el id: "));
    }

    @Test
    public void testUpdateCalificacion_whenUsuarioNotFound(){
        usuario.setId(1);
        calificacion.setId(1);
        calificacion.setUsuario(usuario);

        when(peliculaRepository.findById(calificacion.getUsuario().getId())).thenReturn(Optional.empty());
        when(calificacionRepository.findById(calificacion.getId())).thenReturn(Optional.ofNullable(calificacion));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            calificacionService.updateCalificacion(calificacion.getId(), calificacion);
        });

        assertTrue(exception.getMessage().contains("No se encontro el usuario con el id: "));
    }

    @Test
    public void testUpdateCalificacion_whenPeliculaNotFound(){
        calificacion.setId(1);
        usuario.setId(1);
        calificacion.setUsuario(usuario);
        calificacion.setPelicula(pelicula);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(peliculaRepository.findById(calificacion.getPelicula().getId())).thenReturn(Optional.empty());
        when(calificacionRepository.findById(calificacion.getId())).thenReturn(Optional.ofNullable(calificacion));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            calificacionService.updateCalificacion(calificacion.getId(), calificacion);
        });

        assertTrue(exception.getMessage().contains("No se encontro la pelicula con el id: "));
    }

    @Test
    public void testDeleteCalificacion(){
        Integer idCalificacion = 1;
        calificacion.setId(idCalificacion);

        when(calificacionRepository.existsById(idCalificacion)).thenReturn(true);

        calificacionService.deleteCalificacion(idCalificacion);

        verify(calificacionRepository, times(1)).deleteById(idCalificacion);
    }

    @Test
    public void testDeleteCalificacion_whenCalificacionNotFound(){
        when(calificacionRepository.existsById(calificacion.getId())).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            calificacionService.deleteCalificacion(calificacion.getId());
        });

        assertTrue(exception.getMessage().contains("No se encontro la calificación con el id: "));
    }

}
