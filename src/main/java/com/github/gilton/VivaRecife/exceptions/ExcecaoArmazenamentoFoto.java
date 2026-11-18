package com.github.gilton.VivaRecife.exceptions;

public class ExcecaoArmazenamentoFoto extends RuntimeException {

    public ExcecaoArmazenamentoFoto(Throwable causa) {
        super("Não foi possível salvar a foto enviada. Tente novamente mais tarde.", causa);
    }
}
