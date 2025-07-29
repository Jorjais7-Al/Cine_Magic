package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Boleto;
import com.metahorce.cinemagic.repositories.BoletoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoletoServiceImpl implements BoletoService {

    @Autowired
    BoletoRepository boletoRepository;

    @Override
    public List<Boleto> getBoletos(){
        return boletoRepository.findAll();
    }

    @Override
    public Boleto getBoletoById(Integer id){
        Boleto getBoleto = boletoRepository.findById(id).orElse(null);
        if (getBoleto == null) {
            return null;
        }
        return getBoleto;
    }

    @Override
    public Boleto createBoleto(Boleto boleto){
        return boletoRepository.save(boleto);
    }

}
