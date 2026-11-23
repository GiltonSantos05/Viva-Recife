package com.github.gilton.VivaRecife.services;

import com.github.gilton.VivaRecife.dto.RespostaSolicitacao;
import com.github.gilton.VivaRecife.entity.Person;
import com.github.gilton.VivaRecife.entity.Solicitacao;
import com.github.gilton.VivaRecife.exceptions.ExcecaoPermissaoNegada;
import com.github.gilton.VivaRecife.exceptions.ExcecaoSolicitacaoNaoEncontrada;
import com.github.gilton.VivaRecife.repositories.RepositorioSolicitacao;
import com.github.gilton.VivaRecife.utils.AuthUtil;
import com.github.gilton.VivaRecife.utils.RespostaSolicitacaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServicoSolicitacao {

    private final RepositorioSolicitacao repositorioSolicitacao;
    private final AuthUtil authUtil;

    public ServicoSolicitacao(RepositorioSolicitacao repositorioSolicitacao, AuthUtil authUtil) {
        this.repositorioSolicitacao = repositorioSolicitacao;
        this.authUtil = authUtil;
    }

    public List<RespostaSolicitacao> buscarTodas() {
        Person usuarioLogado = authUtil.getUserLoggedIn();
        return repositorioSolicitacao.findBySolicitanteIdOrderByCriadoEmDesc(usuarioLogado.getId())
                .stream()
                .map(RespostaSolicitacaoMapper::paraResposta)
                .toList();
    }

    public RespostaSolicitacao buscarPorId(UUID id) {
        Person usuarioLogado = authUtil.getUserLoggedIn();
        Solicitacao solicitacao = repositorioSolicitacao.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoNaoEncontrada(id));

        if (solicitacao.getSolicitante() == null
                || !solicitacao.getSolicitante().getId().equals(usuarioLogado.getId())) {
            throw new ExcecaoPermissaoNegada("Você não tem permissão para visualizar esta solicitação.");
        }

        return RespostaSolicitacaoMapper.paraResposta(solicitacao);
    }
}
