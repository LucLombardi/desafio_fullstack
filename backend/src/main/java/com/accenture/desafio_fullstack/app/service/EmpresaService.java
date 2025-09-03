package com.accenture.desafio_fullstack.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.desafio_fullstack.app.domain.mapper.EmpresaMapper;
import com.accenture.desafio_fullstack.app.dto.EmpresaRequestDTO;
import com.accenture.desafio_fullstack.app.dto.EmpresaResponseDto;
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
				.orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + id));

		return EmpresaMapper.toDTO(empresaExistente);
	}

	@Transactional
	public EmpresaResponseDto atualizarEmpresa(Long id, EmpresaRequestDTO empresaRequestDTO) {
		Empresa empresaExistente = empresaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Empresa não encontrada para atualização: " + id));

		 EmpresaMapper.updateToEntity(empresaRequestDTO, empresaExistente);

		Empresa empresaAtualizada = empresaRepository.save(empresaExistente);


		return EmpresaMapper.toDTO(empresaAtualizada);
	}

	@Transactional
	public void deletarEmpresa(Long id) {
		if (!empresaRepository.existsById(id)) {
			throw new RuntimeException("Empresa não encontrada para exclusão: " + id);
		}
		empresaRepository.deleteById(id);
	}
}