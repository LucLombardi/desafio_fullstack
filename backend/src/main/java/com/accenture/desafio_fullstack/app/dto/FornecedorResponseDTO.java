package com.accenture.desafio_fullstack.app.dto;

import java.time.LocalDate;

import com.accenture.desafio_fullstack.app.model.Endereco;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FornecedorResponseDTO {

	private Long id;
	private String nome;
	private String email;
	private String cnpjOuCpf;
	private String rg;
	private LocalDate dataNascimento;
	private Endereco endereco;

}
