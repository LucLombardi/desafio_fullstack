package com.accenture.desafio_fullstack.app.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FornecedorEmpresaResponseDto {

	private Long fornecedorId;
	private String fornecedorCpfCnpj;
	private String fornecedorNome;
	private Long empresaId;
	private String empresaCnpj;
	private String empresaNomeFantasia;

}
