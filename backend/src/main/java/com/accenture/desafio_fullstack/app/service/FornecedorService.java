package com.accenture.desafio_fullstack.app.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.desafio_fullstack.app.domain.mapper.FornecedorMapper;
import com.accenture.desafio_fullstack.app.dto.FornecedorRequestDTO;
import com.accenture.desafio_fullstack.app.dto.FornecedorResponseDto;
import com.accenture.desafio_fullstack.app.enums.TipoPessoa;
import com.accenture.desafio_fullstack.app.model.Fornecedor;
import com.accenture.desafio_fullstack.app.repository.FornecedorRepository;

@Service
public class FornecedorService {

	final private FornecedorRepository fornecedorRepository;

	public FornecedorService(FornecedorRepository fornecedorRepository) {

		this.fornecedorRepository = fornecedorRepository;
	}

	public Page<FornecedorResponseDto> listNome(String nome, Pageable pageable) {

		Page<Fornecedor> fornecedores;
		if (nome == null || nome.trim().isEmpty()) {
			throw new RuntimeException("incluir uma Exception melhor");
		}

		fornecedores = fornecedorRepository.findByNomeContainingAllIgnoreCase(nome, pageable);

		if (fornecedores.isEmpty()) {

			throw new RuntimeException("incluir uma Exception melhor");

		}

		return fornecedores.map(FornecedorMapper::toDTO);
	}

	public FornecedorResponseDto buscarCpfOrCnpj(String cpfOrCnpj) {

		if (cpfOrCnpj == null || cpfOrCnpj.trim().isEmpty()) {

			throw new RuntimeException("incluir uma Exception melhor");

		}

		Fornecedor fornecedor = fornecedorRepository.findByCnpjOuCpf(cpfOrCnpj)
				.orElseThrow(() -> new RuntimeException("incluir uma Exception melhor"));

		return FornecedorMapper.toDTO(fornecedor);

	}

	@Transactional
	public FornecedorResponseDto criarFornecedor(FornecedorRequestDTO fornecedorRequestDTO) {
		if (fornecedorRequestDTO.getTipoPessoa() == TipoPessoa.FISICA) {
			if (fornecedorRequestDTO.getDataNascimento() == null) {
				throw new RuntimeException("Fornecedor pessoa física deve ter a data de nascimento.");
			}
			if (isMenorDeIdade(fornecedorRequestDTO.getDataNascimento())) {
				throw new RuntimeException("Fornecedor do Paraná não pode ser menor de idade.");
			}
		}

		Fornecedor fornecedor = FornecedorMapper.toEntity(fornecedorRequestDTO);
		Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
		return FornecedorMapper.toDTO(fornecedorSalvo);

	}

	public FornecedorResponseDto buscarFornecedorPorId(Long id) {
		Fornecedor fornecedor = fornecedorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com ID: " + id));

		return  FornecedorMapper.toDTO(fornecedor);


	}

	@Transactional
	public FornecedorResponseDto atualizarFornecedor(Long id, FornecedorRequestDTO fornecedorRequestDTO) {
		if (fornecedorRequestDTO.getTipoPessoa() == TipoPessoa.FISICA) {
			if (isMenorDeIdade(fornecedorRequestDTO.getDataNascimento())) {
				throw new RuntimeException("Fornecedor do Paraná não pode ser menor de idade.");
			}
		}

		Fornecedor fornecedorExistente = fornecedorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Fornecedor não encontrado para atualização: " + id));

		 FornecedorMapper.updateToEntity(fornecedorRequestDTO,fornecedorExistente);

		Fornecedor fornecedorAtualizado = fornecedorRepository.save(fornecedorExistente);

		return FornecedorMapper.toDTO(fornecedorAtualizado);

		
	}

	@Transactional
	public void deletarFornecedor(Long id) {
		if (!fornecedorRepository.existsById(id)) {
			throw new RuntimeException("Fornecedor não encontrado para exclusão: " + id);
		}
		fornecedorRepository.deleteById(id);
	}

	private boolean isMenorDeIdade(LocalDate dataNascimento) {
		if (dataNascimento == null) {
			return false;
		}
		return Period.between(dataNascimento, LocalDate.now()).getYears() < 18;
	}

}
