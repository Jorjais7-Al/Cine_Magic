package com.metahorce.cinemagic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @NotNull(message = "El nombre del usuario no debe ser nulo")
    @NotBlank(message = "El nombre del usuario no debe estar vacio")
    private String nombre;

    @NotNull(message = "El correo del usuario no debe ser nulo")
    @NotBlank(message = "El correo del usuario no debe estar vacio")
    @Column(unique = true)
    private String correo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoUsuario tipoUsuario;

    public Usuario(Integer id, String nombre, String correo, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

}
