package com.fujica.bisai.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Porra.
 */
@Entity
@Table(name = "porra")
public class Porra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cantidad")
    private Double cantidad;

    @Column(name = "eleccion")
    private Integer eleccion;

    @ManyToOne
    private Jugador jugador;

    @ManyToOne
    private Partida partida;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public Porra cantidad(Double cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getEleccion() {
        return eleccion;
    }

    public Porra eleccion(Integer eleccion) {
        this.eleccion = eleccion;
        return this;
    }

    public void setEleccion(Integer eleccion) {
        this.eleccion = eleccion;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Porra jugador(Jugador jugador) {
        this.jugador = jugador;
        return this;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Partida getPartida() {
        return partida;
    }

    public Porra partida(Partida partida) {
        this.partida = partida;
        return this;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Porra porra = (Porra) o;
        if (porra.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, porra.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Porra{" +
            "id=" + id +
            ", cantidad='" + cantidad + "'" +
            ", eleccion='" + eleccion + "'" +
            '}';
    }
}
