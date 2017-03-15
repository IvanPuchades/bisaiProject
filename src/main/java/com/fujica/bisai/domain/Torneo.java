package com.fujica.bisai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Torneo.
 */
@Entity
@Table(name = "torneo")
public class Torneo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "numero_participantes", nullable = false)
    private Integer numeroParticipantes;

    @ManyToOne
    private Juego juego;

    @ManyToMany
    @JoinTable(name = "torneo_administrador",
               joinColumns = @JoinColumn(name="torneos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="administradors_id", referencedColumnName="ID"))
    private Set<Jugador> administradors = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "torneo_locale",
               joinColumns = @JoinColumn(name="torneos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="locales_id", referencedColumnName="ID"))
    private Set<Local> locales = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "torneo_equipo",
               joinColumns = @JoinColumn(name="torneos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="equipos_id", referencedColumnName="ID"))


    private Set<Equipo> equipos = new HashSet<>();




    @OneToMany(mappedBy = "torneo")
    @JsonIgnore
    private Set<Clasificacion> clasificacions = new HashSet<>();

    @OneToMany(mappedBy = "torneo")
    @JsonIgnore
    private Set<Partida> partidas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Torneo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumeroParticipantes() {
        return numeroParticipantes;
    }

    public Torneo numeroParticipantes(Integer numeroParticipantes) {
        this.numeroParticipantes = numeroParticipantes;
        return this;
    }

    public void setNumeroParticipantes(Integer numeroParticipantes) {
        this.numeroParticipantes = numeroParticipantes;
    }

    public Juego getJuego() {
        return juego;
    }

    public Torneo juego(Juego juego) {
        this.juego = juego;
        return this;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public Set<Jugador> getAdministradors() {
        return administradors;
    }

    public Torneo administradors(Set<Jugador> jugadors) {
        this.administradors = jugadors;
        return this;
    }

    public Torneo addAdministrador(Jugador jugador) {
        administradors.add(jugador);
        jugador.getTorneos().add(this);
        return this;
    }

    public Torneo removeAdministrador(Jugador jugador) {
        administradors.remove(jugador);
        jugador.getTorneos().remove(this);
        return this;
    }

    public void setAdministradors(Set<Jugador> jugadors) {
        this.administradors = jugadors;
    }

    public Set<Local> getLocales() {
        return locales;
    }

    public Torneo locales(Set<Local> locals) {
        this.locales = locals;
        return this;
    }

    public Torneo addLocale(Local local) {
        locales.add(local);
        local.getTorneos().add(this);
        return this;
    }

    public Torneo removeLocale(Local local) {
        locales.remove(local);
        local.getTorneos().remove(this);
        return this;
    }

    public void setLocales(Set<Local> locals) {
        this.locales = locals;
    }

    public Set<Equipo> getEquipos() {
        return equipos;
    }

    public Torneo equipos(Set<Equipo> equipos) {
        this.equipos = equipos;
        return this;
    }

    public Torneo addEquipo(Equipo equipo) {
        equipos.add(equipo);
        equipo.getTorneos().add(this);
        return this;
    }

    public Torneo removeEquipo(Equipo equipo) {
        equipos.remove(equipo);
        equipo.getTorneos().remove(this);
        return this;
    }

    public void setEquipos(Set<Equipo> equipos) {
        this.equipos = equipos;
    }

    public Set<Clasificacion> getClasificacions() {
        return clasificacions;
    }

    public Torneo clasificacions(Set<Clasificacion> clasificacions) {
        this.clasificacions = clasificacions;
        return this;
    }

    public Torneo addClasificacion(Clasificacion clasificacion) {
        clasificacions.add(clasificacion);
        clasificacion.setTorneo(this);
        return this;
    }

    public Torneo removeClasificacion(Clasificacion clasificacion) {
        clasificacions.remove(clasificacion);
        clasificacion.setTorneo(null);
        return this;
    }

    public void setClasificacions(Set<Clasificacion> clasificacions) {
        this.clasificacions = clasificacions;
    }

    public Set<Partida> getPartidas() {
        return partidas;
    }

    public Torneo partidas(Set<Partida> partidas) {
        this.partidas = partidas;
        return this;
    }

    public Torneo addPartida(Partida partida) {
        partidas.add(partida);
        partida.setTorneo(this);
        return this;
    }

    public Torneo removePartida(Partida partida) {
        partidas.remove(partida);
        partida.setTorneo(null);
        return this;
    }

    public void setPartidas(Set<Partida> partidas) {
        this.partidas = partidas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Torneo torneo = (Torneo) o;
        if (torneo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, torneo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Torneo{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", numeroParticipantes='" + numeroParticipantes + "'" +
            '}';
    }
}
