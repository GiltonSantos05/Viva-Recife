package com.github.gilton.VivaRecife.repositories;

import com.github.gilton.VivaRecife.entity.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositorioSolicitacao extends JpaRepository<Solicitacao, UUID> {
    Optional<Solicitacao> findByComunicacaoId(UUID comunicacaoId);

    List<Solicitacao> findBySolicitanteIdOrderByCriadoEmDesc(UUID solicitanteId);

    @Query("SELECT s.status, COUNT(s) FROM Solicitacao s GROUP BY s.status")
    List<Object[]> contarPorStatus();
}
