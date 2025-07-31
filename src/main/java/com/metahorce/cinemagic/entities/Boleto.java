package com.metahorce.cinemagic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "boletos")
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boleto")
    private Integer id;

    @NotNull(message = "El asiento del boleto no debe ser nulo")
    @NotBlank(message = "El asiento del boleto no debe estar vacio")
    private String asiento;

    @Min(value = 1, message = "El precio del boleto debe ser mayor a 0")
    private double precio;

    @NotNull(message = "La función no debe ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_funcion", nullable = false)
    private Funcion funcion;

    @NotNull(message = "El usuario no debe ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public Boleto(Integer id, String asiento, double precio, Funcion funcion, Usuario usuario) {
        this.id = id;
        this.asiento = asiento;
        this.precio = precio;
        this.funcion = funcion;
        this.usuario = usuario;
    }

    public Boleto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
