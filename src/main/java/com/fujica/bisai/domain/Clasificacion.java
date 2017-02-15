package com.fujica.bisai.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "clasificacion")
public class Clasificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "url")
    private String url;

    @NotNull
    @Column(name = "resultado", nullable = false)
    private Integer resultado;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Column(name = "ranking")
    private Integer ranking;

    @ManyToOne
    private Torneo torneo;

    @ManyToOne
    private Equipo equipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public Clasificacion url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getResultado() {
        return resultado;
    }

    public Clasificacion resultado(Integer resultado) {
        this.resultado = resultado;
        return this;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Clasificacion foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Clasificacion fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Integer getRanking() {
        return ranking;
    }

    public Clasificacion ranking(Integer ranking) {
        this.ranking = ranking;
        return this;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public Clasificacion torneo(Torneo torneo) {
        this.torneo = torneo;
        return this;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public Clasificacion equipo(Equipo equipo) {
        this.equipo = equipo;
        return this;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Clasificacion clasificacion = (Clasificacion) o;
        if (clasificacion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, clasificacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Clasificacion{" +
            "id=" + id +
            ", url='" + url + "'" +
            ", resultado='" + resultado + "'" +
            ", foto='" + foto + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            ", ranking='" + ranking + "'" +
            '}';
    }
}
