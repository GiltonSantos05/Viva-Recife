package com.github.gilton.VivaRecife.controllers;

import com.github.gilton.VivaRecife.dto.RespostaSolicitacao;
import com.github.gilton.VivaRecife.dto.ResumoStatusDTO;
import com.github.gilton.VivaRecife.dto.request.AtualizarStatusRequestDTO;
import com.github.gilton.VivaRecife.services.ServicoAdmin;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class ControladorAdmin {

    private final ServicoAdmin servicoAdmin;

    public ControladorAdmin(ServicoAdmin servicoAdmin) {
        this.servicoAdmin = servicoAdmin;
    }

    @GetMapping("/solicitacoes")
    public ResponseEntity<List<RespostaSolicitacao>> listarTodas() {
        return ResponseEntity.ok(servicoAdmin.listarTodas());
    }

    @GetMapping("/solicitacoes/resumo")
    public ResponseEntity<ResumoStatusDTO> resumo() {
        return ResponseEntity.ok(servicoAdmin.resumo());
    }

    @PatchMapping("/solicitacoes/{id}/status")
    public ResponseEntity<RespostaSolicitacao> atualizarStatus(
            @PathVariable UUID id,
            @RequestBody AtualizarStatusRequestDTO atualizacao
    ) {
        return ResponseEntity.ok(servicoAdmin.atualizarStatus(id, atualizacao.status(), atualizacao.setorResponsavel()));
    }
}
