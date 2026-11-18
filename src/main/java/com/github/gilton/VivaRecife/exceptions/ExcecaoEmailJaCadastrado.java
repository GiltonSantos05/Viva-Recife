package com.github.gilton.VivaRecife.exceptions;

public class ExcecaoEmailJaCadastrado extends RuntimeException {

    public ExcecaoEmailJaCadastrado(String email) {
        super("Já existe um usuário cadastrado com o e-mail: " + email);
    }
}
