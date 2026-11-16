package com.github.gilton.VivaRecife.services;

import com.github.gilton.VivaRecife.dto.RespostaSolicitacao;
import com.github.gilton.VivaRecife.dto.request.RequisicaoSolicitacao;
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

    public RespostaSolicitacao criarSolicitacao(RequisicaoSolicitacao requisicao) {
        Solicitacao solicitacao = new Solicitacao(
                requisicao.tituloSolicitacao(),
                requisicao.tituloProblema(),
                requisicao.descricao(),
                requisicao.localizacao());

        Solicitacao solicitacaoSalva = repositorioSolicitacao.save(solicitacao);
        return paraResposta(solicitacaoSalva);
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

    public RespostaSolicitacao atualizar(UUID id, RequisicaoSolicitacao requisicao) {
        Solicitacao solicitacao = repositorioSolicitacao.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoNaoEncontrada(id));

        solicitacao.setTituloSolicitacao(requisicao.tituloSolicitacao());
        solicitacao.setTituloProblema(requisicao.tituloProblema());
        solicitacao.setDescricao(requisicao.descricao());
        solicitacao.setLocalizacao(requisicao.localizacao());

        Solicitacao solicitacaoAtualizada = repositorioSolicitacao.save(solicitacao);
        return paraResposta(solicitacaoAtualizada);
    }

    public void excluir(UUID id) {
        Solicitacao solicitacao = repositorioSolicitacao.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoNaoEncontrada(id));
        repositorioSolicitacao.delete(solicitacao);
    }

    private RespostaSolicitacao paraResposta(Solicitacao solicitacao) {
        return new RespostaSolicitacao(
                solicitacao.getId(),
                solicitacao.getTituloSolicitacao(),
                solicitacao.getTituloProblema(),
                solicitacao.getDescricao(),
                solicitacao.getLocalizacao());
    }
}
