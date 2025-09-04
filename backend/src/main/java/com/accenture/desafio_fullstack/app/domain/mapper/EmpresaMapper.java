package com.accenture.desafio_fullstack.app.domain.mapper;

import com.accenture.desafio_fullstack.app.dto.EmpresaRequestDTO;
import com.accenture.desafio_fullstack.app.dto.EmpresaResponseDto;
import com.accenture.desafio_fullstack.app.model.Empresa;
import com.accenture.desafio_fullstack.app.model.Endereco;

public class EmpresaMapper {

	public static Empresa toEntity(EmpresaRequestDTO dto) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(dto.getCnpj());
		empresa.setNomeFantasia(dto.getNomeFantasia());

		Endereco endereco = new Endereco();
		endereco.setCep(dto.getCep());
		endereco.setLogradouro(dto.getLogradouro());
		endereco.setNumero(dto.getNumero());
		endereco.setComplemento(dto.getComplemento());
		endereco.setBairro(dto.getBairro());
		endereco.setLocalidade(dto.getLocalidade());
		endereco.setUf(dto.getUf());

		empresa.setEndereco(endereco);
		return empresa;
	}

	public static void updateToEntity(EmpresaRequestDTO dto, Empresa empresa) {

		empresa.setCnpj(dto.getCnpj());
		empresa.setNomeFantasia(dto.getNomeFantasia());


		empresa.getEndereco().setCep(dto.getCep());
		empresa.getEndereco().setLogradouro(dto.getLogradouro());
		empresa.getEndereco().setNumero(dto.getNumero());
		empresa.getEndereco().setComplemento(dto.getComplemento());
		empresa.getEndereco().setBairro(dto.getBairro());
		empresa.getEndereco().setLocalidade(dto.getLocalidade());
		empresa.getEndereco().setUf(dto.getUf());


	}

	public static EmpresaResponseDto toDTO(Empresa empresa) {
		EmpresaResponseDto dto = new EmpresaResponseDto();
		dto.setId(empresa.getId());
		dto.setCnpj(empresa.getCnpj());
		dto.setNomeFantasia(empresa.getNomeFantasia());
		dto.setEndereco(empresa.getEndereco());
		return dto;
	}

}
