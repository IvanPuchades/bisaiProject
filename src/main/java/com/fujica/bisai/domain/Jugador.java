package com.fujica.bisai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Jugador.
 */
@Entity
@Table(name = "jugador")
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "jugadorValorado")
    @JsonIgnore
    private Set<ValoracionJugador> valoracionesRecibidas = new HashSet<>();

    @OneToMany(mappedBy = "jugadorValorador")
    @JsonIgnore
    private Set<ValoracionJugador> valoracionesEnviadas = new HashSet<>();

    @OneToMany(mappedBy = "jugador")
    @JsonIgnore
    private Set<ValoracionEquipo> valoracionesEquipos = new HashSet<>();

    @OneToMany(mappedBy = "jugador")
    @JsonIgnore
    private Set<AdministradorEquipo> administradorEquipos = new HashSet<>();

    @OneToMany(mappedBy = "jugador")
    @JsonIgnore
    private Set<Porra> porras = new HashSet<>();

    @ManyToMany(mappedBy = "jugadors")
    @JsonIgnore
    private Set<Equipo> equipos = new HashSet<>();

    @ManyToMany(mappedBy = "administradors")
    @JsonIgnore
    private Set<Torneo> torneos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public Jugador nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public User getUser() {
        return user;
    }

    public Jugador user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ValoracionJugador> getValoracionesRecibidas() {
        return valoracionesRecibidas;
    }

    public Jugador valoracionesRecibidas(Set<ValoracionJugador> valoracionJugadors) {
        this.valoracionesRecibidas = valoracionJugadors;
        return this;
    }

    public Jugador addValoracionesRecibida(ValoracionJugador valoracionJugador) {
        valoracionesRecibidas.add(valoracionJugador);
        valoracionJugador.setJugadorValorado(this);
        return this;
    }

    public Jugador removeValoracionesRecibida(ValoracionJugador valoracionJugador) {
        valoracionesRecibidas.remove(valoracionJugador);
        valoracionJugador.setJugadorValorado(null);
        return this;
    }

    public void setValoracionesRecibidas(Set<ValoracionJugador> valoracionJugadors) {
        this.valoracionesRecibidas = valoracionJugadors;
    }

    public Set<ValoracionJugador> getValoracionesEnviadas() {
        return valoracionesEnviadas;
    }

    public Jugador valoracionesEnviadas(Set<ValoracionJugador> valoracionJugadors) {
        this.valoracionesEnviadas = valoracionJugadors;
        return this;
    }

    public Jugador addValoracionesEnviada(ValoracionJugador valoracionJugador) {
        valoracionesEnviadas.add(valoracionJugador);
        valoracionJugador.setJugadorValorador(this);
        return this;
    }

    public Jugador removeValoracionesEnviada(ValoracionJugador valoracionJugador) {
        valoracionesEnviadas.remove(valoracionJugador);
        valoracionJugador.setJugadorValorador(null);
        return this;
    }

    public void setValoracionesEnviadas(Set<ValoracionJugador> valoracionJugadors) {
        this.valoracionesEnviadas = valoracionJugadors;
    }

    public Set<ValoracionEquipo> getValoracionesEquipos() {
        return valoracionesEquipos;
    }

    public Jugador valoracionesEquipos(Set<ValoracionEquipo> valoracionEquipos) {
        this.valoracionesEquipos = valoracionEquipos;
        return this;
    }

    public Jugador addValoracionesEquipo(ValoracionEquipo valoracionEquipo) {
        valoracionesEquipos.add(valoracionEquipo);
        valoracionEquipo.setJugador(this);
        return this;
    }

    public Jugador removeValoracionesEquipo(ValoracionEquipo valoracionEquipo) {
        valoracionesEquipos.remove(valoracionEquipo);
        valoracionEquipo.setJugador(null);
        return this;
    }

    public void setValoracionesEquipos(Set<ValoracionEquipo> valoracionEquipos) {
        this.valoracionesEquipos = valoracionEquipos;
    }

    public Set<AdministradorEquipo> getAdministradorEquipos() {
        return administradorEquipos;
    }

    public Jugador administradorEquipos(Set<AdministradorEquipo> administradorEquipos) {
        this.administradorEquipos = administradorEquipos;
        return this;
    }

    public Jugador addAdministradorEquipo(AdministradorEquipo administradorEquipo) {
        administradorEquipos.add(administradorEquipo);
        administradorEquipo.setJugador(this);
        return this;
    }

    public Jugador removeAdministradorEquipo(AdministradorEquipo administradorEquipo) {
        administradorEquipos.remove(administradorEquipo);
        administradorEquipo.setJugador(null);
        return this;
    }

    public void setAdministradorEquipos(Set<AdministradorEquipo> administradorEquipos) {
        this.administradorEquipos = administradorEquipos;
    }

    public Set<Porra> getPorras() {
        return porras;
    }

    public Jugador porras(Set<Porra> porras) {
        this.porras = porras;
        return this;
    }

    public Jugador addPorra(Porra porra) {
        porras.add(porra);
        porra.setJugador(this);
        return this;
    }

    public Jugador removePorra(Porra porra) {
        porras.remove(porra);
        porra.setJugador(null);
        return this;
    }

    public void setPorras(Set<Porra> porras) {
        this.porras = porras;
    }

    public Set<Equipo> getEquipos() {
        return equipos;
    }

    public Jugador equipos(Set<Equipo> equipos) {
        this.equipos = equipos;
        return this;
    }

    public Jugador addEquipo(Equipo equipo) {
        equipos.add(equipo);
        equipo.getJugadors().add(this);
        return this;
    }

    public Jugador removeEquipo(Equipo equipo) {
        equipos.remove(equipo);
        equipo.getJugadors().remove(this);
        return this;
    }

    public void setEquipos(Set<Equipo> equipos) {
        this.equipos = equipos;
    }

    public Set<Torneo> getTorneos() {
        return torneos;
    }

    public Jugador torneos(Set<Torneo> torneos) {
        this.torneos = torneos;
        return this;
    }

    public Jugador addTorneo(Torneo torneo) {
        torneos.add(torneo);
        torneo.getAdministradors().add(this);
        return this;
    }

    public Jugador removeTorneo(Torneo torneo) {
        torneos.remove(torneo);
        torneo.getAdministradors().remove(this);
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
        Jugador jugador = (Jugador) o;
        if (jugador.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, jugador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Jugador{" +
            "id=" + id +
            ", nickName='" + nickName + "'" +
            '}';
    }
}
