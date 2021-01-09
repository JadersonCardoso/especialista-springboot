package com.algaworks.algafood.api.controller.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTIDADE_NAO_ECONTRADA("/entidade-nao-encontrada", "Entidade não encontrada");


    private String title;
    private String uri;

    ProblemType(String path, String title ) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
