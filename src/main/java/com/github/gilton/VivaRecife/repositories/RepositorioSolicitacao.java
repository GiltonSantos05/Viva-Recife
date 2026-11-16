package com.github.gilton.VivaRecife.repositories;

import com.github.gilton.VivaRecife.entity.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepositorioSolicitacao extends JpaRepository<Solicitacao, UUID> {
    Optional<Solicitacao> findByComunicacaoId(UUID comunicacaoId);
}
