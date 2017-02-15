package com.fujica.bisai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Juego.
 */
@Entity
@Table(name = "juego")
public class Juego implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Lob
    @Column(name = "qr")
    private byte[] qr;

    @Column(name = "qr_content_type")
    private String qrContentType;

    @OneToMany(mappedBy = "juego")
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

    public Juego nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public Juego url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Juego foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Juego fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public byte[] getQr() {
        return qr;
    }

    public Juego qr(byte[] qr) {
        this.qr = qr;
        return this;
    }

    public void setQr(byte[] qr) {
        this.qr = qr;
    }

    public String getQrContentType() {
        return qrContentType;
    }

    public Juego qrContentType(String qrContentType) {
        this.qrContentType = qrContentType;
        return this;
    }

    public void setQrContentType(String qrContentType) {
        this.qrContentType = qrContentType;
    }

    public Set<Torneo> getTorneos() {
        return torneos;
    }

    public Juego torneos(Set<Torneo> torneos) {
        this.torneos = torneos;
        return this;
    }

    public Juego addTorneo(Torneo torneo) {
        torneos.add(torneo);
        torneo.setJuego(this);
        return this;
    }

    public Juego removeTorneo(Torneo torneo) {
        torneos.remove(torneo);
        torneo.setJuego(null);
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
        Juego juego = (Juego) o;
        if (juego.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, juego.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Juego{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", url='" + url + "'" +
            ", foto='" + foto + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            ", qr='" + qr + "'" +
            ", qrContentType='" + qrContentType + "'" +
            '}';
    }
}
