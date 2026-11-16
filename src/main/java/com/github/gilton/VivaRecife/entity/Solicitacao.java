package com.github.gilton.VivaRecife.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Solicitacao {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public Solicitacao() {
    }

    public Solicitacao(String tituloSolicitacao, String tituloProblema, String descricao, String localizacao) {
        this.tituloSolicitacao = tituloSolicitacao;
        this.tituloProblema = tituloProblema;
        this.descricao = descricao;
        this.localizacao = localizacao;
    }

    private String tituloSolicitacao;
    private String tituloProblema;
    private String descricao;
    private String localizacao;

    private final LocalDateTime criadoEm = LocalDateTime.now();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTituloSolicitacao() {
        return tituloSolicitacao;
    }

    public void setTituloSolicitacao(String tituloSolicitacao) {
        this.tituloSolicitacao = tituloSolicitacao;
    }

    public String getTituloProblema() {
        return tituloProblema;
    }

    public void setTituloProblema(String tituloProblema) {
        this.tituloProblema = tituloProblema;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
