package com.accenture.desafio_fullstack.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.desafio_fullstack.app.domain.mapper.EmpresaMapper;
import com.accenture.desafio_fullstack.app.dto.EmpresaRequestDTO;
import com.accenture.desafio_fullstack.app.dto.EmpresaResponseDto;
import com.accenture.desafio_fullstack.app.exception.RecursoNaoEncontradoException;
import com.accenture.desafio_fullstack.app.model.Empresa;
import com.accenture.desafio_fullstack.app.repository.EmpresaRepository;

@Service
public class EmpresaService {

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
		if (!empresaRepository.existsById(id)) {
			throw new RecursoNaoEncontradoException("Empresa",id);
		}
		empresaRepository.deleteById(id);
	}
}