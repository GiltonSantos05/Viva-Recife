package com.github.gilton.VivaRecife.controllers;

import com.github.gilton.VivaRecife.dto.RespostaSolicitacao;
import com.github.gilton.VivaRecife.infra.VerificadorAcesso;
import com.github.gilton.VivaRecife.services.ServicoSolicitacao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("solicitacoes")
public class ControladorSolicitacao {

    private final ServicoSolicitacao servicoSolicitacao;
    private final VerificadorAcesso verificadorAcesso;

    public ControladorSolicitacao(ServicoSolicitacao servicoSolicitacao, VerificadorAcesso verificadorAcesso) {
        this.servicoSolicitacao = servicoSolicitacao;
        this.verificadorAcesso = verificadorAcesso;
    }

    @GetMapping
    public ResponseEntity<List<RespostaSolicitacao>> buscarTodas() {
        verificadorAcesso.verificarAcessoUsuario();
        return ResponseEntity.ok(servicoSolicitacao.buscarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaSolicitacao> buscarPorId(@PathVariable UUID id) {
        verificadorAcesso.verificarAcessoUsuario();
        return ResponseEntity.ok(servicoSolicitacao.buscarPorId(id));
    }
}
