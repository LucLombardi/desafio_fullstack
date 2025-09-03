package com.accenture.desafio_fullstack.app.dto;

import com.accenture.desafio_fullstack.app.model.Endereco;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpresaResponseDto {

	private Long id;
	private String cnpj;
	private String nomeFantasia;
	private Endereco endereco;

}
