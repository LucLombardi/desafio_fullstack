package com.accenture.desafio_fullstack.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FornecedorEmpresaRequestDto {
	
	@NotNull(message = "ID da empresa é obrigatório")
	 private Long fornecedorId;
	@NotNull(message = "ID do Fornecedor é obrigatório")
	 private Long empresaId;



}
