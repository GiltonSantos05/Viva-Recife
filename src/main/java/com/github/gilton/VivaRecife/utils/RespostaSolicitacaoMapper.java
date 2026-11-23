package com.github.gilton.VivaRecife.utils;

import com.github.gilton.VivaRecife.dto.RespostaSolicitacao;
import com.github.gilton.VivaRecife.entity.Comunicacao;
import com.github.gilton.VivaRecife.entity.Solicitacao;

import java.util.UUID;

public final class RespostaSolicitacaoMapper {

    private RespostaSolicitacaoMapper() {
    }

    public static RespostaSolicitacao paraResposta(Solicitacao solicitacao) {
        Comunicacao comunicacao = solicitacao.getComunicacao();

        // Fallback para registros antigos, criados antes de existir o vínculo com o solicitante.
        UUID solicitanteId = solicitacao.getSolicitante() != null
                ? solicitacao.getSolicitante().getId()
                : null;
        String nomeSolicitante = solicitacao.getNomeSolicitante() != null
                ? solicitacao.getNomeSolicitante()
                : (solicitacao.getSolicitante() != null ? solicitacao.getSolicitante().getNome() : "Não informado");

        return new RespostaSolicitacao(
                solicitacao.getId(),
                comunicacao.getId(),
                comunicacao.getTitulo(),
                comunicacao.getLocalizacao(),
                comunicacao.getLatitude(),
                comunicacao.getLongitude(),
                comunicacao.getDetalhamentoProblema(),
                comunicacao.getTipoProblema(),
                comunicacao.getCaminhoFoto(),
                solicitacao.getStatus(),
                solicitacao.getSetorResponsavel(),
                solicitacao.getCriadoEm(),
                solicitacao.getAtualizadoEm(),
                solicitanteId,
                nomeSolicitante
        );
    }
}
