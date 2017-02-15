package com.fujica.bisai.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ValoracionJugador.
 */
@Entity
@Table(name = "valoracion_jugador")
public class ValoracionJugador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "hora")
    private ZonedDateTime hora;

    @Column(name = "me_gusta")
    private Boolean meGusta;

    @ManyToOne
    private Jugador jugadorValorado;

    @ManyToOne
    private Jugador jugadorValorador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getHora() {
        return hora;
    }

    public ValoracionJugador hora(ZonedDateTime hora) {
        this.hora = hora;
        return this;
    }

    public void setHora(ZonedDateTime hora) {
        this.hora = hora;
    }

    public Boolean isMeGusta() {
        return meGusta;
    }

    public ValoracionJugador meGusta(Boolean meGusta) {
        this.meGusta = meGusta;
        return this;
    }

    public void setMeGusta(Boolean meGusta) {
        this.meGusta = meGusta;
    }

    public Jugador getJugadorValorado() {
        return jugadorValorado;
    }

    public ValoracionJugador jugadorValorado(Jugador jugador) {
        this.jugadorValorado = jugador;
        return this;
    }

    public void setJugadorValorado(Jugador jugador) {
        this.jugadorValorado = jugador;
    }

    public Jugador getJugadorValorador() {
        return jugadorValorador;
    }

    public ValoracionJugador jugadorValorador(Jugador jugador) {
        this.jugadorValorador = jugador;
        return this;
    }

    public void setJugadorValorador(Jugador jugador) {
        this.jugadorValorador = jugador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValoracionJugador valoracionJugador = (ValoracionJugador) o;
        if (valoracionJugador.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, valoracionJugador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ValoracionJugador{" +
            "id=" + id +
            ", hora='" + hora + "'" +
            ", meGusta='" + meGusta + "'" +
            '}';
    }
}
