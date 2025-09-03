package com.accenture.desafio_fullstack.app.dto;

import lombok.Data;


@Data
public class CepResponseDto {

    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
    private String estado;
    private String regiao;

}