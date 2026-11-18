package com.github.gilton.VivaRecife.exceptions;

public class ExcecaoTokenInvalido extends RuntimeException {

    public ExcecaoTokenInvalido() {
        super("Token de acesso inválido ou expirado. Faça login novamente.");
    }
}
