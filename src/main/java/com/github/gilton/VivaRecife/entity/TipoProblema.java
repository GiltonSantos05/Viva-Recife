package com.github.gilton.VivaRecife.entity;

public enum TipoProblema {
    ILUMINACAO("Iluminação"),
    INFRAESTRUTURA("Infraestrutura"),
    SINALIZACAO("Sinalização"),
    SEGURANCA("Segurança"),
    POLICIAMENTO("Policiamento"),
    OUTROS("Outros");

    private final String descricao;

    TipoProblema(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
