package com.accenture.desafio_fullstack.app.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpresaRequestDTO {

	@CNPJ(message = "CNPJ Invalido")
	@NotNull
	private String cnpj;

	@NotBlank
	private String nomeFantasia;

	@NotBlank
	private String cep;

	@NotBlank
	private String logradouro;

	@NotBlank
	private String numero;

	private String complemento;

	@NotBlank
	private String bairro;

	@NotBlank
	private String localidade;

	@NotBlank
	private String uf;
}
