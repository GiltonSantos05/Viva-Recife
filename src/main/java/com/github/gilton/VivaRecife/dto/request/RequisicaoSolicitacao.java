package com.github.gilton.VivaRecife.dto.request;

public record RequisicaoSolicitacao(
        String tituloSolicitacao,
        String tituloProblema,
        String descricao,
        String localizacao
) {
}
