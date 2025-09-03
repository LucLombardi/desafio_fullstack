package com.accenture.desafio_fullstack.app.enums;

public enum TipoPessoa {
	FISICA ("F"),
	JURIDICA ("J");

    private final String valor;

    private TipoPessoa(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }


}
