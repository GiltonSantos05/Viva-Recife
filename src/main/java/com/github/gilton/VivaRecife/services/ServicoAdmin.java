package com.github.gilton.VivaRecife.services;

import com.github.gilton.VivaRecife.dto.RespostaSolicitacao;
import com.github.gilton.VivaRecife.dto.ResumoStatusDTO;
import com.github.gilton.VivaRecife.entity.Solicitacao;
import com.github.gilton.VivaRecife.entity.enums.StatusSolicitacao;
import com.github.gilton.VivaRecife.entity.enums.TipoProblema;
import com.github.gilton.VivaRecife.exceptions.ExcecaoSetorInvalido;
import com.github.gilton.VivaRecife.exceptions.ExcecaoSolicitacaoNaoEncontrada;
import com.github.gilton.VivaRecife.exceptions.ExcecaoStatusInvalido;
import com.github.gilton.VivaRecife.repositories.RepositorioSolicitacao;
import com.github.gilton.VivaRecife.utils.RespostaSolicitacaoMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServicoAdmin {

    private static final List<TipoProblema> SETORES_PERMITIDOS = List.of(
            TipoProblema.ILUMINACAO,
            TipoProblema.INFRAESTRUTURA,
            TipoProblema.SINALIZACAO,
            TipoProblema.SEGURANCA,
            TipoProblema.POLICIAMENTO
    );

    private final RepositorioSolicitacao repositorioSolicitacao;

    public ServicoAdmin(RepositorioSolicitacao repositorioSolicitacao) {
        this.repositorioSolicitacao = repositorioSolicitacao;
    }

    public List<RespostaSolicitacao> listarTodas() {
        return repositorioSolicitacao.findAll(Sort.by(Sort.Direction.DESC, "criadoEm"))
                .stream()
                .map(RespostaSolicitacaoMapper::paraResposta)
                .toList();
    }

    public ResumoStatusDTO resumo() {
        Map<StatusSolicitacao, Long> contagens = repositorioSolicitacao.contarPorStatus().stream()
                .collect(Collectors.toMap(
                        linha -> (StatusSolicitacao) linha[0],
                        linha -> ((Number) linha[1]).longValue()
                ));

        return new ResumoStatusDTO(
                repositorioSolicitacao.count(),
                contagens.getOrDefault(StatusSolicitacao.RECEBIDA, 0L),
                contagens.getOrDefault(StatusSolicitacao.EM_ANALISE, 0L),
                contagens.getOrDefault(StatusSolicitacao.ENCAMINHADA, 0L),
                contagens.getOrDefault(StatusSolicitacao.CANCELADA, 0L),
                contagens.getOrDefault(StatusSolicitacao.RESOLVIDA, 0L)
        );
    }

    @Transactional
    public RespostaSolicitacao atualizarStatus(UUID id, String status, String setorResponsavel) {
        Solicitacao solicitacao = repositorioSolicitacao.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoNaoEncontrada(id));

        StatusSolicitacao novoStatus = converterStatus(status);
        TipoProblema setor = converterSetor(setorResponsavel);

        solicitacao.setStatus(novoStatus);
        solicitacao.setSetorResponsavel(setor);

        return RespostaSolicitacaoMapper.paraResposta(solicitacao);
    }

    private StatusSolicitacao converterStatus(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new ExcecaoStatusInvalido("O status é obrigatório.");
        }
        try {
            return StatusSolicitacao.valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            String aceitos = String.join(", ", Arrays.stream(StatusSolicitacao.values()).map(Enum::name).toList());
            throw new ExcecaoStatusInvalido("Status inválido. Valores aceitos: " + aceitos + ".");
        }
    }

    private TipoProblema converterSetor(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new ExcecaoSetorInvalido("O setor responsável é obrigatório.");
        }

        TipoProblema setor;
        try {
            setor = TipoProblema.valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ExcecaoSetorInvalido("Setor inválido. Valores aceitos: " + valoresAceitosSetores() + ".");
        }

        if (!SETORES_PERMITIDOS.contains(setor)) {
            throw new ExcecaoSetorInvalido("Setor inválido. Valores aceitos: " + valoresAceitosSetores() + ".");
        }
        return setor;
    }

    private String valoresAceitosSetores() {
        return String.join(", ", SETORES_PERMITIDOS.stream().map(Enum::name).toList());
    }
}
