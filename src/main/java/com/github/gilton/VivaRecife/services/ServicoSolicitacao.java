package com.github.gilton.VivaRecife.services;

import com.github.gilton.VivaRecife.dto.RespostaSolicitacao;
import com.github.gilton.VivaRecife.entity.Comunicacao;
import com.github.gilton.VivaRecife.entity.Solicitacao;
import com.github.gilton.VivaRecife.exceptions.ExcecaoSolicitacaoNaoEncontrada;
import com.github.gilton.VivaRecife.repositories.RepositorioSolicitacao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServicoSolicitacao {

    private final RepositorioSolicitacao repositorioSolicitacao;

    public ServicoSolicitacao(RepositorioSolicitacao repositorioSolicitacao) {
        this.repositorioSolicitacao = repositorioSolicitacao;
    }

    public List<RespostaSolicitacao> buscarTodas() {
        return repositorioSolicitacao.findAll()
                .stream()
                .map(this::paraResposta)
                .toList();
    }

    public RespostaSolicitacao buscarPorId(UUID id) {
        Solicitacao solicitacao = repositorioSolicitacao.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoNaoEncontrada(id));
        return paraResposta(solicitacao);
    }

    private RespostaSolicitacao paraResposta(Solicitacao solicitacao) {
        Comunicacao comunicacao = solicitacao.getComunicacao();
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
                solicitacao.getCriadoEm(),
                solicitacao.getAtualizadoEm()
        );
    }
}
