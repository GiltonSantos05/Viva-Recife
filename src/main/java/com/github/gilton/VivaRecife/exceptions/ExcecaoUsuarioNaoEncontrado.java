package com.github.gilton.VivaRecife.exceptions;

public class ExcecaoUsuarioNaoEncontrado extends RuntimeException {

    public ExcecaoUsuarioNaoEncontrado(String email) {
        super("Usuário não encontrado com o e-mail: " + email);
    }
}
