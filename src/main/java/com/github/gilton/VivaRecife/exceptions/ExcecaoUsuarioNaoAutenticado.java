package com.github.gilton.VivaRecife.exceptions;

public class ExcecaoUsuarioNaoAutenticado extends RuntimeException {

    public ExcecaoUsuarioNaoAutenticado() {
        super("É necessário estar autenticado para acessar este recurso.");
    }
}
