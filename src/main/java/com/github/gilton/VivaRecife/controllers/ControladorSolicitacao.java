package com.github.gilton.VivaRecife.controllers;

import com.github.gilton.VivaRecife.dto.RespostaSolicitacao;
import com.github.gilton.VivaRecife.dto.request.RequisicaoSolicitacao;
import com.github.gilton.VivaRecife.services.ServicoSolicitacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<RespostaSolicitacao> criarSolicitacao(@RequestBody RequisicaoSolicitacao requisicao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoSolicitacao.criarSolicitacao(requisicao));
    }

    @GetMapping
    public ResponseEntity<List<RespostaSolicitacao>> buscarTodas() {
        return ResponseEntity.ok(servicoSolicitacao.buscarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaSolicitacao> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(servicoSolicitacao.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaSolicitacao> atualizar(@PathVariable UUID id, @RequestBody RequisicaoSolicitacao requisicao) {
        return ResponseEntity.ok(servicoSolicitacao.atualizar(id, requisicao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        servicoSolicitacao.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
