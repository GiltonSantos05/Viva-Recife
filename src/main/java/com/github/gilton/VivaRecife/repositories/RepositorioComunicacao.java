package com.github.gilton.VivaRecife.repositories;

import com.github.gilton.VivaRecife.entity.Comunicacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositorioComunicacao extends JpaRepository<Comunicacao, UUID> {
}
