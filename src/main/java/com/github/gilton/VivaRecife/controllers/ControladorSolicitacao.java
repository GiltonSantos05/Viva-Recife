package com.github.gilton.VivaRecife.controllers;

import com.github.gilton.VivaRecife.dto.RespostaSolicitacao;
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

    public ControladorSolicitacao(ServicoSolicitacao servicoSolicitacao) {
        this.servicoSolicitacao = servicoSolicitacao;
    }

    @GetMapping
    public ResponseEntity<List<RespostaSolicitacao>> buscarTodas() {
        return ResponseEntity.ok(servicoSolicitacao.buscarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaSolicitacao> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(servicoSolicitacao.buscarPorId(id));
    }
}
