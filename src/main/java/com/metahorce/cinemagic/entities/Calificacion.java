package com.metahorce.cinemagic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "calificaciones")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion")
    private Integer id;

    @Min(value = 1, message = "La calificación debe ser de 1 a 5")
    @Max(value = 5, message = "La calificación debe ser de 1 a 5")
    private int calificacion;

    @NotNull(message = "La reseña no debe ser nulo")
    @NotBlank(message = "La reseña no debe estar vacio")
    private String resenia;

    @NotNull(message = "El usuario no debe ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotNull(message = "La pelicula no debe ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    public Calificacion(Integer id, int calificacion, String resenia, Usuario usuario, Pelicula pelicula) {
        this.id = id;
        this.calificacion = calificacion;
        this.resenia = resenia;
        this.usuario = usuario;
        this.pelicula = pelicula;
    }

    public Calificacion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getResenia() {
        return resenia;
    }

    public void setResenia(String resenia) {
        this.resenia = resenia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

}
