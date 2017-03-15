package com.fujica.bisai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Partida.
 */
@Entity
@Table(name = "partida")
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fecha_inicio")
    private ZonedDateTime fechaInicio;

    @Column(name = "fecha_final")
    private ZonedDateTime fechaFinal;

    @Column(name = "resultado_equipo_1")
    private Integer resultadoEquipo1;

    @Column(name = "resultado_equipo_2")
    private Integer resultadoEquipo2;

    @Min(value = 0)
    @Column(name = "num_ronda")
    private Integer numRonda;

    @Min(value = 0)
    @Column(name = "num_partida_ronda")
    private Integer numPartidaRonda;

    @ManyToOne
    private Equipo equipo1;

    @ManyToOne
    private Equipo equipo2;

    @ManyToOne
    private Torneo torneo;

    @OneToMany(mappedBy = "partida")
    @JsonIgnore
    private Set<Porra> porras = new HashSet<>();

    @ManyToOne
    private Partida siguientePartida;

    @ManyToOne
    private Equipo equipoGanador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFechaInicio() {
        return fechaInicio;
    }

    public Partida fechaInicio(ZonedDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(ZonedDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public ZonedDateTime getFechaFinal() {
        return fechaFinal;
    }

    public Partida fechaFinal(ZonedDateTime fechaFinal) {
        this.fechaFinal = fechaFinal;
        return this;
    }

    public void setFechaFinal(ZonedDateTime fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Integer getResultadoEquipo1() {
        return resultadoEquipo1;
    }

    public Partida resultadoEquipo1(Integer resultadoEquipo1) {
        this.resultadoEquipo1 = resultadoEquipo1;
        return this;
    }

    public void setResultadoEquipo1(Integer resultadoEquipo1) {
        this.resultadoEquipo1 = resultadoEquipo1;
    }

    public Integer getResultadoEquipo2() {
        return resultadoEquipo2;
    }

    public Partida resultadoEquipo2(Integer resultadoEquipo2) {
        this.resultadoEquipo2 = resultadoEquipo2;
        return this;
    }

    public void setResultadoEquipo2(Integer resultadoEquipo2) {
        this.resultadoEquipo2 = resultadoEquipo2;
    }

    public Integer getNumRonda() {
        return numRonda;
    }

    public Partida numRonda(Integer numRonda) {
        this.numRonda = numRonda;
        return this;
    }

    public void setNumRonda(Integer numRonda) {
        this.numRonda = numRonda;
    }

    public Integer getNumPartidaRonda() {
        return numPartidaRonda;
    }

    public Partida numPartidaRonda(Integer numPartidaRonda) {
        this.numPartidaRonda = numPartidaRonda;
        return this;
    }

    public void setNumPartidaRonda(Integer numPartidaRonda) {
        this.numPartidaRonda = numPartidaRonda;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public Partida equipo1(Equipo equipo) {
        this.equipo1 = equipo;
        return this;
    }

    public void setEquipo1(Equipo equipo) {
        this.equipo1 = equipo;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public Partida equipo2(Equipo equipo) {
        this.equipo2 = equipo;
        return this;
    }

    public void setEquipo2(Equipo equipo) {
        this.equipo2 = equipo;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public Partida torneo(Torneo torneo) {
        this.torneo = torneo;
        return this;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Set<Porra> getPorras() {
        return porras;
    }

    public Partida porras(Set<Porra> porras) {
        this.porras = porras;
        return this;
    }

    public Partida addPorra(Porra porra) {
        porras.add(porra);
        porra.setPartida(this);
        return this;
    }

    public Partida removePorra(Porra porra) {
        porras.remove(porra);
        porra.setPartida(null);
        return this;
    }

    public void setPorras(Set<Porra> porras) {
        this.porras = porras;
    }

    public Partida getSiguientePartida() {
        return siguientePartida;
    }

    public Partida siguientePartida(Partida partida) {
        this.siguientePartida = partida;
        return this;
    }

    public void setSiguientePartida(Partida partida) {
        this.siguientePartida = partida;
    }

    public Equipo getEquipoGanador() {
        return equipoGanador;
    }

    public Partida equipoGanador(Equipo equipo) {
        this.equipoGanador = equipo;
        return this;
    }

    public void setEquipoGanador(Equipo equipo) {
        this.equipoGanador = equipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Partida partida = (Partida) o;
        if (partida.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, partida.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Partida{" +
            "id=" + id +
            ", fechaInicio='" + fechaInicio + "'" +
            ", fechaFinal='" + fechaFinal + "'" +
            ", resultadoEquipo1='" + resultadoEquipo1 + "'" +
            ", resultadoEquipo2='" + resultadoEquipo2 + "'" +
            ", numRonda='" + numRonda + "'" +
            ", numPartidaRonda='" + numPartidaRonda + "'" +
            '}';
    }
}
