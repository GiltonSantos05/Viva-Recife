package com.github.gilton.VivaRecife.exceptions;

public class ExcecaoSenhasDiferentes extends RuntimeException {

    public ExcecaoSenhasDiferentes() {
        super("As senhas informadas não coincidem.");
    }
}
