package com.metahorce.cinemagic.repositories;

import com.metahorce.cinemagic.entities.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Integer> {

    @Query("SELECT SUM(b.precio) FROM Boleto b")
    Double totalSumPrices();

}
