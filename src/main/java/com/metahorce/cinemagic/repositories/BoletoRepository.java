package com.metahorce.cinemagic.repositories;

import com.metahorce.cinemagic.entities.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Integer> {

    @Query("SELECT SUM(b.precio) FROM Boleto b")
    Double totalSumPrices();

    @Query("SELECT p.titulo AS titulo, COUNT(b.id) AS boletosVendidos, SUM(b.precio) AS ventaTotal " +
            "FROM Boleto b " +
            "JOIN b.funcion f " +
            "JOIN f.pelicula p " +
            "GROUP BY p.titulo")
    List<Object[]> getSalesReport();

}
