package com.github.gilton.VivaRecife.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Comunicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String titulo;

    private String localizacao;

    private Double latitude;

    private Double longitude;

    private String detalhamentoProblema;

    @Enumerated(EnumType.STRING)
    private TipoProblema tipoProblema;

    private String caminhoFoto;

    private final LocalDateTime criadoEm = LocalDateTime.now();

    public Comunicacao() {
    }

    public Comunicacao(String titulo, String localizacao, Double latitude, Double longitude, String detalhamentoProblema, TipoProblema tipoProblema, String caminhoFoto) {
        this.titulo = titulo;
        this.localizacao = localizacao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.detalhamentoProblema = detalhamentoProblema;
        this.tipoProblema = tipoProblema;
        this.caminhoFoto = caminhoFoto;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDetalhamentoProblema() {
        return detalhamentoProblema;
    }

    public void setDetalhamentoProblema(String detalhamentoProblema) {
        this.detalhamentoProblema = detalhamentoProblema;
    }

    public TipoProblema getTipoProblema() {
        return tipoProblema;
    }

    public void setTipoProblema(TipoProblema tipoProblema) {
        this.tipoProblema = tipoProblema;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
