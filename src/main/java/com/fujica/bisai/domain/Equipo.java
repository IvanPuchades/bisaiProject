package com.fujica.bisai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Equipo.
 */
@Entity
@Table(name = "equipo")
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @ManyToMany
    @JoinTable(name = "equipo_jugador",
               joinColumns = @JoinColumn(name="equipos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="jugadors_id", referencedColumnName="ID"))
    private Set<Jugador> jugadors = new HashSet<>();

    @OneToMany(mappedBy = "equipo")
    @JsonIgnore
    private Set<Clasificacion> clasificacions = new HashSet<>();

    @OneToMany(mappedBy = "equipo")
    @JsonIgnore
    private Set<ValoracionEquipo> valoracionesJugadores = new HashSet<>();

    @OneToMany(mappedBy = "equipo")
    @JsonIgnore
    private Set<AdministradorEquipo> administradorEquipos = new HashSet<>();

    @OneToMany(mappedBy = "equipo1")
    @JsonIgnore
    private Set<Partida> equipo1Partidas = new HashSet<>();

    @OneToMany(mappedBy = "equipo2")
    @JsonIgnore
    private Set<Partida> equipo2Partidas = new HashSet<>();

    @ManyToMany(mappedBy = "equipos")
    @JsonIgnore
    private Set<Torneo> torneos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Equipo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Equipo fechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Set<Jugador> getJugadors() {
        return jugadors;
    }

    public Equipo jugadors(Set<Jugador> jugadors) {
        this.jugadors = jugadors;
        return this;
    }

    public Equipo addJugador(Jugador jugador) {
        jugadors.add(jugador);
        jugador.getEquipos().add(this);
        return this;
    }

    public Equipo removeJugador(Jugador jugador) {
        jugadors.remove(jugador);
        jugador.getEquipos().remove(this);
        return this;
    }

    public void setJugadors(Set<Jugador> jugadors) {
        this.jugadors = jugadors;
    }

    public Set<Clasificacion> getClasificacions() {
        return clasificacions;
    }

    public Equipo clasificacions(Set<Clasificacion> clasificacions) {
        this.clasificacions = clasificacions;
        return this;
    }

    public Equipo addClasificacion(Clasificacion clasificacion) {
        clasificacions.add(clasificacion);
        clasificacion.setEquipo(this);
        return this;
    }

    public Equipo removeClasificacion(Clasificacion clasificacion) {
        clasificacions.remove(clasificacion);
        clasificacion.setEquipo(null);
        return this;
    }

    public void setClasificacions(Set<Clasificacion> clasificacions) {
        this.clasificacions = clasificacions;
    }

    public Set<ValoracionEquipo> getValoracionesJugadores() {
        return valoracionesJugadores;
    }

    public Equipo valoracionesJugadores(Set<ValoracionEquipo> valoracionEquipos) {
        this.valoracionesJugadores = valoracionEquipos;
        return this;
    }

    public Equipo addValoracionesJugadore(ValoracionEquipo valoracionEquipo) {
        valoracionesJugadores.add(valoracionEquipo);
        valoracionEquipo.setEquipo(this);
        return this;
    }

    public Equipo removeValoracionesJugadore(ValoracionEquipo valoracionEquipo) {
        valoracionesJugadores.remove(valoracionEquipo);
        valoracionEquipo.setEquipo(null);
        return this;
    }

    public void setValoracionesJugadores(Set<ValoracionEquipo> valoracionEquipos) {
        this.valoracionesJugadores = valoracionEquipos;
    }

    public Set<AdministradorEquipo> getAdministradorEquipos() {
        return administradorEquipos;
    }

    public Equipo administradorEquipos(Set<AdministradorEquipo> administradorEquipos) {
        this.administradorEquipos = administradorEquipos;
        return this;
    }

    public Equipo addAdministradorEquipo(AdministradorEquipo administradorEquipo) {
        administradorEquipos.add(administradorEquipo);
        administradorEquipo.setEquipo(this);
        return this;
    }

    public Equipo removeAdministradorEquipo(AdministradorEquipo administradorEquipo) {
        administradorEquipos.remove(administradorEquipo);
        administradorEquipo.setEquipo(null);
        return this;
    }

    public void setAdministradorEquipos(Set<AdministradorEquipo> administradorEquipos) {
        this.administradorEquipos = administradorEquipos;
    }

    public Set<Partida> getEquipo1Partidas() {
        return equipo1Partidas;
    }

    public Equipo equipo1Partidas(Set<Partida> partidas) {
        this.equipo1Partidas = partidas;
        return this;
    }

    public Equipo addEquipo1Partida(Partida partida) {
        equipo1Partidas.add(partida);
        partida.setEquipo1(this);
        return this;
    }

    public Equipo removeEquipo1Partida(Partida partida) {
        equipo1Partidas.remove(partida);
        partida.setEquipo1(null);
        return this;
    }

    public void setEquipo1Partidas(Set<Partida> partidas) {
        this.equipo1Partidas = partidas;
    }

    public Set<Partida> getEquipo2Partidas() {
        return equipo2Partidas;
    }

    public Equipo equipo2Partidas(Set<Partida> partidas) {
        this.equipo2Partidas = partidas;
        return this;
    }

    public Equipo addEquipo2Partida(Partida partida) {
        equipo2Partidas.add(partida);
        partida.setEquipo2(this);
        return this;
    }

    public Equipo removeEquipo2Partida(Partida partida) {
        equipo2Partidas.remove(partida);
        partida.setEquipo2(null);
        return this;
    }

    public void setEquipo2Partidas(Set<Partida> partidas) {
        this.equipo2Partidas = partidas;
    }

    public Set<Torneo> getTorneos() {
        return torneos;
    }

    public Equipo torneos(Set<Torneo> torneos) {
        this.torneos = torneos;
        return this;
    }

    public Equipo addTorneo(Torneo torneo) {
        torneos.add(torneo);
        torneo.getEquipos().add(this);
        return this;
    }

    public Equipo removeTorneo(Torneo torneo) {
        torneos.remove(torneo);
        torneo.getEquipos().remove(this);
        return this;
    }

    public void setTorneos(Set<Torneo> torneos) {
        this.torneos = torneos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Equipo equipo = (Equipo) o;
        if (equipo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, equipo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Equipo{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", fechaCreacion='" + fechaCreacion + "'" +
            '}';
    }
}
