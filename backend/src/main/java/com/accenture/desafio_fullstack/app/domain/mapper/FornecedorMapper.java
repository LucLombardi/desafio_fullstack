package com.accenture.desafio_fullstack.app.domain.mapper;

import com.accenture.desafio_fullstack.app.dto.FornecedorRequestDTO;
import com.accenture.desafio_fullstack.app.dto.FornecedorResponseDto;
import com.accenture.desafio_fullstack.app.model.Endereco;
import com.accenture.desafio_fullstack.app.model.Fornecedor;

public class FornecedorMapper {
	
	 public static Fornecedor toEntity(FornecedorRequestDTO dto) {
	        Fornecedor fornecedor = new Fornecedor();
	        fornecedor.setTipoPessoa(dto.getTipoPessoa());
	        fornecedor.setNome(dto.getNome());
	        fornecedor.setEmail(dto.getEmail());
	        fornecedor.setCnpjOuCpf(dto.getCnpjOuCpf());
	        fornecedor.setRg(dto.getRg());
	        fornecedor.setDataNascimento(dto.getDataNascimento());

	        Endereco endereco = new Endereco();
	        endereco.setCep(dto.getCep());
	        endereco.setLogradouro(dto.getLogradouro());
	        endereco.setNumero(dto.getNumero());
	        endereco.setComplemento(dto.getComplemento());
	        endereco.setBairro(dto.getBairro());
	        endereco.setLocalidade(dto.getLocalidade());
	        endereco.setUf(dto.getUf());
	        
	        fornecedor.setEndereco(endereco);
	        return fornecedor;
	    }
	 
	 public static void updateToEntity(FornecedorRequestDTO dto, Fornecedor fornecedor) {
	        
	        fornecedor.setTipoPessoa(dto.getTipoPessoa());
	        fornecedor.setNome(dto.getNome());
	        fornecedor.setEmail(dto.getEmail());
	        fornecedor.setCnpjOuCpf(dto.getCnpjOuCpf());
	        fornecedor.setRg(dto.getRg());
	        fornecedor.setDataNascimento(dto.getDataNascimento());

	    
	        fornecedor.getEndereco().setCep(dto.getCep());
	        fornecedor.getEndereco().setLogradouro(dto.getLogradouro());
	        fornecedor.getEndereco().setNumero(dto.getNumero());
	        fornecedor.getEndereco().setComplemento(dto.getComplemento());
	        fornecedor.getEndereco().setBairro(dto.getBairro());
	        fornecedor.getEndereco().setLocalidade(dto.getLocalidade());
	        fornecedor.getEndereco().setUf(dto.getUf());

	    }

	    public static FornecedorResponseDto toDTO(Fornecedor fornecedor) {
	        FornecedorResponseDto dto = new FornecedorResponseDto();
	        dto.setId(fornecedor.getId());
	        dto.setNome(fornecedor.getNome());
	        dto.setEmail(fornecedor.getEmail());
	        dto.setCnpjOuCpf(fornecedor.getCnpjOuCpf());
	        dto.setRg(fornecedor.getRg());
	        dto.setDataNascimento(fornecedor.getDataNascimento());
	        dto.setEndereco(fornecedor.getEndereco());
	        return dto;
	    }

}
