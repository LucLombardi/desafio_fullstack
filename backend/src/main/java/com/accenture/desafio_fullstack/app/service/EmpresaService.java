package com.accenture.desafio_fullstack.app.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.desafio_fullstack.app.domain.mapper.EmpresaMapper;
import com.accenture.desafio_fullstack.app.domain.mapper.FornecedorMapper;
import com.accenture.desafio_fullstack.app.dto.EmpresaRequestDTO;
import com.accenture.desafio_fullstack.app.dto.EmpresaResponseDto;
import com.accenture.desafio_fullstack.app.dto.FornecedorResponseDto;
import com.accenture.desafio_fullstack.app.exception.ConflitoException;
import com.accenture.desafio_fullstack.app.exception.RecursoNaoEncontradoException;
import com.accenture.desafio_fullstack.app.exception.RegraDeNegocioException;
import com.accenture.desafio_fullstack.app.model.Empresa;
import com.accenture.desafio_fullstack.app.model.Fornecedor;
import com.accenture.desafio_fullstack.app.repository.EmpresaRepository;

@Service
public class EmpresaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final EmpresaRepository empresaRepository;

	public EmpresaService(EmpresaRepository empresaRepository) {
		this.empresaRepository = empresaRepository;
	}

	@Transactional
	public EmpresaResponseDto criarEmpresa(EmpresaRequestDTO empresaRequestDTO) {

		Empresa empresa = EmpresaMapper.toEntity(empresaRequestDTO);

		Empresa empresaSalva = empresaRepository.save(empresa);


		return EmpresaMapper.toDTO(empresaSalva);
	}

	public Page<EmpresaResponseDto> listarEmpresas(Pageable pageable) {
		Page<Empresa> empresas = empresaRepository.findAll(pageable);

		return empresas.map(EmpresaMapper::toDTO);

	}

	public EmpresaResponseDto buscarEmpresaPorId(Long id) {

		Empresa empresaExistente = empresaRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Empresa",id));

		return EmpresaMapper.toDTO(empresaExistente);
	}

	@Transactional
	public EmpresaResponseDto atualizarEmpresa(Long id, EmpresaRequestDTO empresaRequestDTO) {
		Empresa empresa = empresaRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Empresa",id));

		 EmpresaMapper.updateToEntity(empresaRequestDTO, empresa);

		Empresa empresaAtualizada = empresaRepository.save(empresa);


		return EmpresaMapper.toDTO(empresaAtualizada);
	}

	@Transactional
	public void deletarEmpresa(Long id) {
		
		Empresa empresa = empresaRepository.findById(id)
				.orElseThrow(() ->  new RecursoNaoEncontradoException("Empresa", id));
		
		
		
		if (empresaRepository.contarFornecedores(empresa.getId()) >0 ) {
			
			throw new ConflitoException(String.format("A Empresa com id %d e nome: %s não pode ser excluída pois está "
			        + "atrelada a pelo menos um Fornecedor, desfazer relacionamento", 
			        empresa.getId(), empresa.getNomeFantasia()));		
		}
		
	
		empresaRepository.deleteById(id);
	}
	
	public Page<FornecedorResponseDto> listarFornecedoresDaEmpresa(Long empresaId, Pageable pageable) {
	    

	    if (!empresaRepository.existsById(empresaId)) {
	        throw new RecursoNaoEncontradoException("Empresa", empresaId);
	    }
	    

	    Page<Fornecedor> fornecedores = empresaRepository.findFornecedoresByEmpresaId(empresaId, pageable);
	    

	    if (fornecedores.isEmpty()) {
	        throw new RecursoNaoEncontradoException("Nenhum fornecedor encontrado para a empresa com ID: " + empresaId);
	    }
	    

	    return fornecedores.map(FornecedorMapper::toDTO);
	}
	

    public EmpresaResponseDto buscarEmpresaPorCnpj(String cnpj) {
        
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new RegraDeNegocioException("CNPJ deve ser preenchido");
        }
        

        String cnpjLimpo = cnpj.replaceAll("[^0-9]", "");
        
        if (cnpjLimpo.length() != 14) {
            throw new RegraDeNegocioException("CNPJ deve ter 14 dígitos");
        }
        
        Empresa empresa = empresaRepository.findByCnpj(cnpjLimpo)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa", "CNPJ: " + cnpj));
        
        return EmpresaMapper.toDTO(empresa);
    }
}