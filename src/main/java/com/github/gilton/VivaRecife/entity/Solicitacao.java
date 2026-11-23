package com.github.gilton.VivaRecife.entity;

import com.github.gilton.VivaRecife.entity.enums.StatusSolicitacao;
import com.github.gilton.VivaRecife.entity.enums.TipoProblema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    private Person solicitante;

    // Snapshot do nome no momento da criação: evidência legível no banco e histórico auditável.
    private String nomeSolicitante;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status;

    @Enumerated(EnumType.STRING)
    private TipoProblema setorResponsavel;

    private final LocalDateTime criadoEm = LocalDateTime.now();

    private LocalDateTime atualizadoEm = LocalDateTime.now();

    public Solicitacao() {
    }

    public Solicitacao(Comunicacao comunicacao, StatusSolicitacao status) {
        this.comunicacao = comunicacao;
        this.status = status;
    }

    public Solicitacao(Comunicacao comunicacao, StatusSolicitacao status, Person solicitante) {
        this.comunicacao = comunicacao;
        this.status = status;
        this.solicitante = solicitante;
        this.nomeSolicitante = solicitante != null ? solicitante.getNome() : null;
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

    public Person getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Person solicitante) {
        this.solicitante = solicitante;
        this.nomeSolicitante = solicitante != null ? solicitante.getNome() : null;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
        this.atualizadoEm = LocalDateTime.now();
    }

    public TipoProblema getSetorResponsavel() {
        return setorResponsavel;
    }

    public void setSetorResponsavel(TipoProblema setorResponsavel) {
        this.setorResponsavel = setorResponsavel;
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
