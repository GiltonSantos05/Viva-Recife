package com.github.gilton.VivaRecife.dto;

import com.github.gilton.VivaRecife.entity.enums.StatusSolicitacao;
import com.github.gilton.VivaRecife.entity.enums.TipoProblema;

import java.time.LocalDateTime;
import java.util.UUID;

public record RespostaSolicitacao(
        UUID id,
        UUID comunicacaoId,
        String titulo,
        String localizacao,
        Double latitude,
        Double longitude,
        String detalhamentoProblema,
        TipoProblema tipoProblema,
        String caminhoFoto,
        StatusSolicitacao status,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
