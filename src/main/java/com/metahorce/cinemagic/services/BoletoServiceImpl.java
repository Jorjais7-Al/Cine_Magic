package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Boleto;
import com.metahorce.cinemagic.entities.Funcion;
import com.metahorce.cinemagic.entities.Usuario;
import com.metahorce.cinemagic.exceptions.InvalidDataException;
import com.metahorce.cinemagic.exceptions.ResourceNotFoundException;
import com.metahorce.cinemagic.repositories.BoletoRepository;
import com.metahorce.cinemagic.repositories.FuncionRepository;
import com.metahorce.cinemagic.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class BoletoServiceImpl implements BoletoService {

    @Autowired
    BoletoRepository boletoRepository;

    @Autowired
    FuncionRepository funcionRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Boleto> getBoletos(){
        return boletoRepository.findAll();
    }

    @Override
    public Boleto getBoletoById(Integer id){
        Boleto getBoleto = boletoRepository.findById(id).orElse(null);
        if (getBoleto == null) {
            throw new ResourceNotFoundException("No se encontro el boleto con el id: "+ id);
        }
        return getBoleto;
    }

    @Override
    public Boleto createBoleto(Boleto boleto){
        Integer idFuncion = boleto.getFuncion().getId();
        Funcion funcion = funcionRepository.findById(idFuncion).orElse(null);
        if (funcion == null){
            throw new ResourceNotFoundException("No se encontró la función con el id: "+ idFuncion);
        }

        Integer idUsuario = boleto.getUsuario().getId();
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null){
            throw new ResourceNotFoundException("No se encontró el usuario con el id: " + idUsuario);
        }

        boleto.setFuncion(funcion);
        boleto.setUsuario(usuario);
        return boletoRepository.save(boleto);
    }

}
