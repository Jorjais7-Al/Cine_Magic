package com.metahorce.cinemagic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Integer id;

    @NotNull(message = "El titulo de la pelicula no debe ser nulo")
    @NotBlank(message = "El titulo de la pelicula no debe estar vacio")
    private String titulo;

    @Min(value = 1, message = "La duración de la pelicula debe ser mayor a 0 (en minutos)")
    private int duracion;

    public Pelicula(Integer id, String titulo, int duracion) {
        this.id = id;
        this.titulo = titulo;
        this.duracion = duracion;
    }

    public Pelicula() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

}
