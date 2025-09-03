package com.accenture.desafio_fullstack.app.dto;

import java.time.LocalDate;

import com.accenture.desafio_fullstack.app.enums.TipoPessoa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FornecedorRequestDTO {
	
	@NotNull
    private TipoPessoa tipoPessoa;

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    private String cnpjOuCpf;

    private String rg;

    private LocalDate dataNascimento;
    
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
