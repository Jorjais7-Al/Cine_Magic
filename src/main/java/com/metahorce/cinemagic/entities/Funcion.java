package com.metahorce.cinemagic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "funciones")
public class Funcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcion")
    private Integer id;

    @NotNull(message = "La fecha no debe ser nulo, ingresa la fecha como este formato yyyy-mm-dd")
    @NotBlank(message = "La fecha no debe estar vacio, ingresa la fecha como este formato yyyy-mm-dd")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Formato de fecha inválido, ingresa la fecha como este formato yyyy-mm-dd")
    private String fecha;

    @NotNull(message = "La hora no debe ser nulo, ingresa el horario como este formato hh:mm:ss")
    @NotBlank(message = "La hora no debe estar vacio, , ingresa el horario como este formato hh:mm:ss")
    @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$", message = "Formato de hora inválido, ingresa el horario como este formato hh:mm:ss")
    private String hora;

    @NotNull(message = "La pelicula no debe ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    public Funcion(Integer id, String fecha, String hora, Pelicula pelicula) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.pelicula = pelicula;
    }

    public Funcion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

}
