package com.github.gilton.VivaRecife.dto;

import java.util.UUID;

public record RespostaSolicitacao(
        UUID id,
        String tituloSolicitacao,
        String tituloProblema,
        String descricao,
        String localizacao
) {
}
