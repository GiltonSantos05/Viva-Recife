package com.github.gilton.VivaRecife.exceptions;

public class ExcecaoSenhaIncorreta extends RuntimeException {

    public ExcecaoSenhaIncorreta() {
        super("Senha incorreta. Verifique suas credenciais e tente novamente.");
    }
}
