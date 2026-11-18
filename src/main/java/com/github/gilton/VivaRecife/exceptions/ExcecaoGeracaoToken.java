package com.github.gilton.VivaRecife.exceptions;

public class ExcecaoGeracaoToken extends RuntimeException {

    public ExcecaoGeracaoToken() {
        super("Não foi possível gerar o token de acesso. Tente novamente mais tarde.");
    }
}
