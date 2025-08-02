package com.metahorce.cinemagic;

import com.metahorce.cinemagic.entities.TipoUsuario;
import com.metahorce.cinemagic.entities.Usuario;
import com.metahorce.cinemagic.exceptions.DuplicateDataException;
import com.metahorce.cinemagic.exceptions.InvalidUserException;
import com.metahorce.cinemagic.exceptions.ResourceNotFoundException;
import com.metahorce.cinemagic.repositories.UsuarioRepository;
import com.metahorce.cinemagic.services.UsuarioServiceImpl;
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

public class UsuarioServiceImplTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    Usuario usuario = new Usuario();

    @Test
    public void testGetUsuarios(){
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario(), new Usuario()));
        List<Usuario> usuariosActuales = usuarioService.getUsuarios();
        assertEquals(2, usuariosActuales.size());
    }

    @Test
    public void testUsuarioById(){
        Integer idUsuario = 1;
        usuario.setId(idUsuario);

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));

        Usuario usuarioActual = usuarioService.getUsuarioById(idUsuario);

        assertNotNull(usuarioActual);
        assertEquals(idUsuario, usuarioActual.getId());
    }

    @Test
    public void testUsuarioById_whenUsuarioNotFound(){
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.getUsuarioById(usuario.getId());
        });

        assertTrue(exception.getMessage().contains("No se encontro el usuario con el id: "));
    }

    @Test
    public void testCreateUsuario(){
        usuario.setId(1);
        usuario.setNombre("Jorge");
        usuario.setCorreo("magna231@gmail.com");
        usuario.setTipoUsuario(TipoUsuario.ADMINISTRADOR);

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario newUsuario = usuarioService.createUsuario(usuario);

        assertNotNull(newUsuario);
        assertEquals(1, newUsuario.getId());
    }

    @Test
    public void testCreateUsuario_whenCorreoExists(){
        usuario.setId(1);
        usuario.setNombre("Jorge");
        usuario.setCorreo("magna231@gmail.com");
        usuario.setTipoUsuario(TipoUsuario.ADMINISTRADOR);

        when(usuarioRepository.existsBycorreo(usuario.getCorreo())).thenReturn(true);

        Exception exception = assertThrows(DuplicateDataException.class, () -> {
            usuarioService.createUsuario(usuario);
        });

        assertTrue(exception.getMessage().contains("El correo electrónico ya está registrado"));
    }

    @Test
    public void testUpdateUsuario(){
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.ofNullable(usuario));

        usuario.setCorreo("magnaj726@gmail.com");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario updateUsuario = usuarioService.updateUsuario(usuario.getId(), usuario);

        assertEquals("magnaj726@gmail.com", updateUsuario.getCorreo());
    }

    @Test
    public void testUpdateUsuario_whenUsuarioNotFound(){
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.updateUsuario(usuario.getId(), usuario);
        });

        assertTrue(exception.getMessage().contains("No se encontro el usuario con el id: "));
    }

    @Test
    public void testDeleteUsuario(){
        Integer idUsuario = 1;
        usuario.setId(idUsuario);

        when(usuarioRepository.existsById(idUsuario)).thenReturn(true);

        usuarioService.deleteUsuario(idUsuario, "ADMINISTRADOR");

        verify(usuarioRepository, times(1)).deleteById(idUsuario);
    }

    @Test
    public void testDeleteUsuario_whenTipoUsuarioIsDifferent(){
        Integer idUsuario = 1;
        usuario.setId(idUsuario);

        when(usuarioRepository.existsById(idUsuario)).thenReturn(true);

        Exception exception = assertThrows(InvalidUserException.class, () -> {
            usuarioService.deleteUsuario(usuario.getId(), "ESPECTADOR");
        });

        assertTrue(exception.getMessage().contains("El usuario no puede ejecutar esta petición"));
    }

    @Test
    public void testDeleteUsuario_whenUsuarioNotFound(){
        when(usuarioRepository.existsById(usuario.getId())).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.deleteUsuario(usuario.getId(), "ADMINISTRADOR");
        });

        assertTrue(exception.getMessage().contains("No se encontro el usuario con el id: "));
    }

}
