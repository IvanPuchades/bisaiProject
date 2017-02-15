package com.fujica.bisai.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.fujica.bisai.domain.enumeration.Permiso;

/**
 * A AdministradorEquipo.
 */
@Entity
@Table(name = "administrador_equipo")
public class AdministradorEquipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "hora")
    private ZonedDateTime hora;

    @Enumerated(EnumType.STRING)
    @Column(name = "permiso")
    private Permiso permiso;

    @ManyToOne
    private Equipo equipo;

    @ManyToOne
    private Jugador jugador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getHora() {
        return hora;
    }

    public AdministradorEquipo hora(ZonedDateTime hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(ZonedDateTime hora) {
        this.hora = hora;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public AdministradorEquipo permiso(Permiso permiso) {
        this.permiso = permiso;
        return this;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public AdministradorEquipo equipo(Equipo equipo) {
        this.equipo = equipo;
        return this;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public AdministradorEquipo jugador(Jugador jugador) {
        this.jugador = jugador;
        return this;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdministradorEquipo administradorEquipo = (AdministradorEquipo) o;
        if (administradorEquipo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, administradorEquipo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AdministradorEquipo{" +
            "id=" + id +
            ", hora='" + hora + "'" +
            ", permiso='" + permiso + "'" +
            '}';
    }
}
