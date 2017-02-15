package com.fujica.bisai.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ValoracionEquipo.
 */
@Entity
@Table(name = "valoracion_equipo")
public class ValoracionEquipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "hora")
    private ZonedDateTime hora;

    @Column(name = "me_gusta")
    private Boolean meGusta;

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

    public ValoracionEquipo hora(ZonedDateTime hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(ZonedDateTime hora) {
        this.hora = hora;
    }

    public Boolean isMeGusta() {
        return meGusta;
    }

    public ValoracionEquipo meGusta(Boolean meGusta) {
        this.meGusta = meGusta;
        return this;
    }

    public void setMeGusta(Boolean meGusta) {
        this.meGusta = meGusta;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public ValoracionEquipo equipo(Equipo equipo) {
        this.equipo = equipo;
        return this;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public ValoracionEquipo jugador(Jugador jugador) {
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
        ValoracionEquipo valoracionEquipo = (ValoracionEquipo) o;
        if (valoracionEquipo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, valoracionEquipo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ValoracionEquipo{" +
            "id=" + id +
            ", hora='" + hora + "'" +
            ", meGusta='" + meGusta + "'" +
            '}';
    }
}
