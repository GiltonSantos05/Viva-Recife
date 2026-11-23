package com.github.gilton.VivaRecife.entity.enums;

public enum StatusSolicitacao {
    RECEBIDA("Recebida"),
    EM_ANALISE("Em análise"),
    ENCAMINHADA("Encaminhada"),
    CANCELADA("Cancelada"),
    RESOLVIDA("Resolvida");

    private final String descricao;

    StatusSolicitacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
