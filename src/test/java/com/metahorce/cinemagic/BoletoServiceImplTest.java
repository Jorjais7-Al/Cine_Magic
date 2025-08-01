package com.metahorce.cinemagic;

import com.metahorce.cinemagic.entities.Boleto;
import com.metahorce.cinemagic.entities.Funcion;
import com.metahorce.cinemagic.entities.Usuario;
import com.metahorce.cinemagic.exceptions.ResourceNotFoundException;
import com.metahorce.cinemagic.repositories.BoletoRepository;
import com.metahorce.cinemagic.repositories.FuncionRepository;
import com.metahorce.cinemagic.repositories.UsuarioRepository;
import com.metahorce.cinemagic.services.BoletoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BoletoServiceImplTest {

    @Mock
    BoletoRepository boletoRepository;

    @Mock
    FuncionRepository funcionRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    BoletoServiceImpl boletoService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    Boleto boleto = new Boleto();
    Funcion funcion = new Funcion();
    Usuario usuario = new Usuario();

    @Test
    public void testGetBoletos(){
        when(boletoRepository.findAll()).thenReturn(List.of(new Boleto(), new Boleto()));
        List<Boleto> boletosActuales = boletoService.getBoletos();
        assertEquals(2, boletosActuales.size());
    }

    @Test
    public void testGetBoletoById(){
        Integer idBoleto = 1;
        boleto.setId(idBoleto);

        when(boletoRepository.findById(idBoleto)).thenReturn(Optional.of(boleto));

        Boleto boletoActual = boletoService.getBoletoById(idBoleto);

        assertNotNull(boletoActual);
        assertEquals(idBoleto, boletoActual.getId());
    }

    @Test
    public void testGetBoletoById_whenBoletoNotFound(){
        when(boletoRepository.findById(boleto.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            boletoService.getBoletoById(boleto.getId());
        });

        assertTrue(exception.getMessage().contains("No se encontro el boleto con el id: "));
    }

    @Test
    public void testCreateBoleto(){
        usuario.setId(1);
        funcion.setId(1);
        boleto.setId(1);
        boleto.setAsiento("A12");
        boleto.setPrecio(72.5);
        boleto.setFuncion(funcion);
        boleto.setUsuario(usuario);

        // MOCKS para evitar la exception ResourceNotFoundException
        when(funcionRepository.findById(1)).thenReturn(Optional.of(funcion));
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(boletoRepository.save(boleto)).thenReturn(boleto);

        Boleto newBoleto = boletoService.createBoleto(boleto);

        assertNotNull(newBoleto);
        assertEquals(1, newBoleto.getId());
    }

    @Test
    public void testCreateBoleto_whenFuncionNotFound() {
        boleto.setId(1);
        boleto.setAsiento("A12");
        boleto.setPrecio(72.5);
        boleto.setFuncion(funcion);

        when(funcionRepository.findById(boleto.getFuncion().getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            boletoService.createBoleto(boleto);
        });

        assertTrue(exception.getMessage().contains("No se encontró la función con el id: "));
    }

    @Test
    public void testCreateBoleto_whenUsuarioNotFound() {
        boleto.setId(1);
        boleto.setAsiento("A12");
        boleto.setPrecio(72.5);
        boleto.setUsuario(usuario);
        funcion.setId(1);
        boleto.setFuncion(funcion);

        when(funcionRepository.findById(funcion.getId())).thenReturn(Optional.of(funcion));
        when(usuarioRepository.findById(boleto.getUsuario().getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            boletoService.createBoleto(boleto);
        });

        assertTrue(exception.getMessage().contains("No se encontró el usuario con el id: "));
    }

    @Test
    public void testTotalSumPrices(){
        Double precio = 355.12;
        when(boletoRepository.totalSumPrices()).thenReturn(precio);
        Double precioActual = boletoService.totalSumPrices("ADMINISTRADOR");
        assertEquals(355.12, precioActual);
    }

}
