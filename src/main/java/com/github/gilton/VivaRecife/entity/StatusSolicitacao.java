package com.github.gilton.VivaRecife.entity;

public enum StatusSolicitacao {
    RECEBIDA("Recebida"),
    EM_ANALISE("Em análise"),
    EM_ANDAMENTO("Em andamento"),
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusSolicitacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
