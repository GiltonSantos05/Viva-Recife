package com.github.gilton.VivaRecife.exceptions;

public class ExcecaoPermissaoNegada extends RuntimeException {

    public ExcecaoPermissaoNegada() {
        super("Acesso negado. É necessário ter o perfil USUARIO para acessar este recurso.");
    }

    public ExcecaoPermissaoNegada(String mensagem) {
        super(mensagem);
    }
}
