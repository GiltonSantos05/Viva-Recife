package com.github.gilton.VivaRecife.exceptions;

import java.util.UUID;

public class ExcecaoSolicitacaoNaoEncontrada extends RuntimeException {
    public ExcecaoSolicitacaoNaoEncontrada(UUID id) {
        super("Solicitação não encontrada com o ID: " + id);
    }
}
