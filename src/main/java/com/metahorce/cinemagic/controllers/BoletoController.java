package com.metahorce.cinemagic.controllers;

import com.metahorce.cinemagic.entities.Boleto;
import com.metahorce.cinemagic.exceptions.InvalidDataException;
import com.metahorce.cinemagic.services.BoletoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cinemagic/boletos")
public class BoletoController {

    @Autowired
    BoletoService boletoService;

    @GetMapping
    public ResponseEntity<?> getBoletos() {
        return ResponseEntity.ok(boletoService.getBoletos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoletoById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(boletoService.getBoletoById(id));
    }

    @GetMapping("/ventaTotal")
    public ResponseEntity<?> getBoletoSales(){
        Double total = boletoService.totalSumPrices();
        Map<String, Object> response = new HashMap<>();
        response.put("VentaTotalDeBoletos", total);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createBoleto(@Valid @RequestBody Boleto boleto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new InvalidDataException("Error de validación", bindingResult);
        }
        return ResponseEntity.ok(boletoService.createBoleto(boleto));
    }

}
