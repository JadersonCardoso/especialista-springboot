package com.algaworks.algafood.integracao;

public enum GeneroEnum {

    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTROS("Outros");

    private String descricao;

    private GeneroEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
