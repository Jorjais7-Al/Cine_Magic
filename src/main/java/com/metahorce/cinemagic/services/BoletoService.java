package com.metahorce.cinemagic.services;

import com.metahorce.cinemagic.entities.Boleto;

import java.util.List;

public interface BoletoService {

    List<Boleto> getBoletos();

    Boleto getBoletoById(Integer id);

    Boleto createBoleto(Boleto boleto);

    Double totalSumPrices(String user);

    List<Object[]> getSalesReport(String user);
}
