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
import com.accenture.desafio_fullstack.app.exception.ConflitoException;
import com.accenture.desafio_fullstack.app.exception.RecursoNaoEncontradoException;
import com.accenture.desafio_fullstack.app.exception.RegraDeNegocioException;
import com.accenture.desafio_fullstack.app.model.Fornecedor;
import com.accenture.desafio_fullstack.app.repository.FornecedorRepository;

@Service
public class FornecedorService {

	final private FornecedorRepository fornecedorRepository;

	public FornecedorService(FornecedorRepository fornecedorRepository) {

		this.fornecedorRepository = fornecedorRepository;
	}

	public Page<FornecedorResponseDto> ListarTodosOsFornecedores(Pageable pageable) {

		Page<Fornecedor> fornecedores;

		fornecedores = fornecedorRepository.findAll(pageable);

		if (fornecedores.isEmpty()) {

			throw new RecursoNaoEncontradoException("Não foi encontrado nenhum fornecedor");

		}

		return fornecedores.map(FornecedorMapper::toDTO);
	}

	public Page<FornecedorResponseDto> listNome(String nome, Pageable pageable) {

		Page<Fornecedor> fornecedores;
		if (nome == null || nome.trim().isEmpty()) {
			throw new RegraDeNegocioException("Nome deve ser preenchido");
		}

		fornecedores = fornecedorRepository.findByNomeContainingIgnoreCase(nome.trim(), pageable);

		if (fornecedores.isEmpty()) {

			throw new RecursoNaoEncontradoException("Fornecedor", nome);

		}

		return fornecedores.map(FornecedorMapper::toDTO);
	}

	public FornecedorResponseDto buscarCpfOrCnpj(String cpfOrCnpj) {

		if (cpfOrCnpj == null || cpfOrCnpj.trim().isEmpty()) {

			throw new RegraDeNegocioException("cpf ou Cnpj deve ser preenchido");

		}

		Fornecedor fornecedor = fornecedorRepository.findByCnpjOuCpf(cpfOrCnpj)
				.orElseThrow(() -> new RecursoNaoEncontradoException(
						"Não foi ncontrado o Fornecedor com a informação " + cpfOrCnpj));

		return FornecedorMapper.toDTO(fornecedor);

	}

	@Transactional
	public FornecedorResponseDto criarFornecedor(FornecedorRequestDTO fornecedorRequestDTO) {
		validarDadosPessoaFisica(fornecedorRequestDTO);

		Fornecedor fornecedor = FornecedorMapper.toEntity(fornecedorRequestDTO);
		Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
		return FornecedorMapper.toDTO(fornecedorSalvo);

	}

	public FornecedorResponseDto buscarFornecedorPorId(Long id) {
		Fornecedor fornecedor = fornecedorRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor", id));

		return FornecedorMapper.toDTO(fornecedor);

	}

	@Transactional
	public FornecedorResponseDto atualizarFornecedor(Long id, FornecedorRequestDTO fornecedorRequestDTO) {

		validarDadosPessoaFisica(fornecedorRequestDTO);

		Fornecedor fornecedorExistente = fornecedorRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor", id));

		FornecedorMapper.updateToEntity(fornecedorRequestDTO, fornecedorExistente);

		Fornecedor fornecedorAtualizado = fornecedorRepository.save(fornecedorExistente);

		return FornecedorMapper.toDTO(fornecedorAtualizado);

	}

	@Transactional
	public void deletarFornecedor(Long id) {
		
		Fornecedor fornecedor = fornecedorRepository.findById(id)
				.orElseThrow(() ->  new RecursoNaoEncontradoException("Fornecedor", id));
		
		if (fornecedorRepository.contarEmpresas(fornecedor.getId()) > 0) {
			
			throw new ConflitoException(String.format("O fornecedor com id %d e nome: %s não pode ser excluída pois esta "
					+ "atrelado pelo menos uma empresa, desfazer relacionamento", fornecedor.getId(),fornecedor.getNome()));		
		}
		
	
		fornecedorRepository.deleteById(id);
	}

	private void validarDadosPessoaFisica(FornecedorRequestDTO dto) {
		if (dto.getTipoPessoa() != TipoPessoa.FISICA) {
			return;
		}

		if (dto.getRg() == null || dto.getRg().trim().isEmpty()) {
			throw new RegraDeNegocioException("Fornecedor pessoa física deve informar o RG");
		}

		if (dto.getDataNascimento() == null) {
			throw new RegraDeNegocioException("Fornecedor pessoa física deve informar a data de nascimento");
		}

	}

}
