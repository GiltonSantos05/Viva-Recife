package com.github.gilton.VivaRecife.services;

import com.github.gilton.VivaRecife.dto.RespostaSolicitacao;
import com.github.gilton.VivaRecife.entity.Comunicacao;
import com.github.gilton.VivaRecife.entity.Solicitacao;
import com.github.gilton.VivaRecife.entity.StatusSolicitacao;
import com.github.gilton.VivaRecife.entity.TipoProblema;
import com.github.gilton.VivaRecife.repositories.RepositorioComunicacao;
import com.github.gilton.VivaRecife.repositories.RepositorioSolicitacao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Service
public class ServicoComunicacao {

    private final RepositorioComunicacao repositorioComunicacao;
    private final RepositorioSolicitacao repositorioSolicitacao;
    private final ServicoArmazenamentoFoto servicoArmazenamentoFoto;

    public ServicoComunicacao(RepositorioComunicacao repositorioComunicacao,
                              RepositorioSolicitacao repositorioSolicitacao,
                              ServicoArmazenamentoFoto servicoArmazenamentoFoto) {
        this.repositorioComunicacao = repositorioComunicacao;
        this.repositorioSolicitacao = repositorioSolicitacao;
        this.servicoArmazenamentoFoto = servicoArmazenamentoFoto;
    }

    @Transactional
    public RespostaSolicitacao criar(String titulo, String localizacao, String latitude, String longitude, String detalhamentoProblema, String tipoProblema, MultipartFile foto) {
        validarCamposObrigatorios(titulo, localizacao, detalhamentoProblema);
        Double lat = converterCoordenada(latitude, "latitude", -90.0, 90.0);
        Double lng = converterCoordenada(longitude, "longitude", -180.0, 180.0);
        TipoProblema tipo = converterTipoProblema(tipoProblema);

        String caminhoFoto = servicoArmazenamentoFoto.salvarFoto(foto);
        Comunicacao comunicacao = new Comunicacao(titulo, localizacao, lat, lng, detalhamentoProblema, tipo, caminhoFoto);
        Comunicacao comunicacaoSalva = repositorioComunicacao.save(comunicacao);

        Solicitacao solicitacao = new Solicitacao(comunicacaoSalva, StatusSolicitacao.RECEBIDA);
        Solicitacao solicitacaoSalva = repositorioSolicitacao.save(solicitacao);

        return paraResposta(solicitacaoSalva);
    }

    private void validarCamposObrigatorios(String titulo, String localizacao, String detalhamentoProblema) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título é obrigatório.");
        }
        if (localizacao == null || localizacao.trim().isEmpty()) {
            throw new IllegalArgumentException("A localização é obrigatória.");
        }
        if (detalhamentoProblema == null || detalhamentoProblema.trim().isEmpty()) {
            throw new IllegalArgumentException("O detalhamento do problema é obrigatório.");
        }
    }

    private Double converterCoordenada(String valor, String nomeCampo, double minimo, double maximo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("A " + nomeCampo + " é obrigatória.");
        }
        try {
            double coordenada = Double.parseDouble(valor.trim().replace(",", "."));
            if (coordenada < minimo || coordenada > maximo) {
                throw new IllegalArgumentException("A " + nomeCampo + " deve estar entre " + minimo + " e " + maximo + ".");
            }
            return coordenada;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("A " + nomeCampo + " informada é inválida.");
        }
    }

    private TipoProblema converterTipoProblema(String tipoProblema) {
        if (tipoProblema == null || tipoProblema.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo do problema é obrigatório.");
        }
        try {
            return TipoProblema.valueOf(tipoProblema.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            String valoresAceitos = String.join(", ", Arrays.stream(TipoProblema.values()).map(Enum::name).toList());
            throw new IllegalArgumentException("Tipo do problema inválido. Valores aceitos: " + valoresAceitos + ".");
        }
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
