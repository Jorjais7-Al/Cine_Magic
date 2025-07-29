package com.metahorce.cinemagic.repositories;

import com.metahorce.cinemagic.entities.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionRepository extends JpaRepository<Funcion, Integer> {

}
