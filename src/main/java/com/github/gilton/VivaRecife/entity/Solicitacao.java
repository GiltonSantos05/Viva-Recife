package com.github.gilton.VivaRecife.entity;

import com.github.gilton.VivaRecife.entity.enums.StatusSolicitacao;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "comunicacao_id", nullable = false, unique = true)
    private Comunicacao comunicacao;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status;

    private final LocalDateTime criadoEm = LocalDateTime.now();

    private LocalDateTime atualizadoEm = LocalDateTime.now();

    public Solicitacao() {
    }

    public Solicitacao(Comunicacao comunicacao, StatusSolicitacao status) {
        this.comunicacao = comunicacao;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Comunicacao getComunicacao() {
        return comunicacao;
    }

    public void setComunicacao(Comunicacao comunicacao) {
        this.comunicacao = comunicacao;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
        this.atualizadoEm = LocalDateTime.now();
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}
