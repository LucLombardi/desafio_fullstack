package com.accenture.desafio_fullstack.app.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

import com.accenture.desafio_fullstack.app.dto.FornecedorEmpresaRequestDto;
import com.accenture.desafio_fullstack.app.dto.FornecedorEmpresaResponseDto;
import com.accenture.desafio_fullstack.app.enums.TipoPessoa;
import com.accenture.desafio_fullstack.app.exception.RecursoNaoEncontradoException;
import com.accenture.desafio_fullstack.app.exception.RegraDeNegocioException;
import com.accenture.desafio_fullstack.app.model.Empresa;
import com.accenture.desafio_fullstack.app.model.Fornecedor;
import com.accenture.desafio_fullstack.app.repository.EmpresaRepository;
import com.accenture.desafio_fullstack.app.repository.FornecedorRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FornecedorEmpresaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final EmpresaRepository empresaRepository;
	private final FornecedorRepository fornecedorRepository;

	public FornecedorEmpresaService(EmpresaRepository empresaRepository, FornecedorRepository fornecedorRepository) {

		this.empresaRepository = empresaRepository;
		this.fornecedorRepository = fornecedorRepository;
	}

	@Transactional
	public FornecedorEmpresaResponseDto vincular(FornecedorEmpresaRequestDto dto) {

		Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new RecursoNaoEncontradoException("Empresa", dto.getEmpresaId()));

		Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
				.orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor", dto.getFornecedorId()));

		if (fornecedor.temEmpresa(empresa) == true) {
			throw new RegraDeNegocioException(String.format("Fornecedor '%s' já está vinculado à empresa '%s'",
					fornecedor.getNome(), empresa.getNomeFantasia()));
		}

		validarRegraParana(empresa, fornecedor);

		empresa.getFornecedores().add(fornecedor);
		fornecedor.getEmpresas().add(empresa);

		fornecedorRepository.save(fornecedor);

		FornecedorEmpresaResponseDto fornecedorEmpresaResponseDto = new FornecedorEmpresaResponseDto();

		fornecedorEmpresaResponseDto.setEmpresaCnpj(empresa.getCnpj());
		fornecedorEmpresaResponseDto.setEmpresaId(empresa.getId());
		fornecedorEmpresaResponseDto.setEmpresaNomeFantasia(empresa.getNomeFantasia());
		fornecedorEmpresaResponseDto.setFornecedorCpfCnpj(fornecedor.getCnpjOuCpf());
		fornecedorEmpresaResponseDto.setFornecedorId(fornecedor.getId());
		fornecedorEmpresaResponseDto.setFornecedorNome(fornecedor.getNome());

		return fornecedorEmpresaResponseDto;

	}

	@Transactional
	public void desvincular(Long fornecedorId, Long empresaId) {

		Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor", fornecedorId));

		Empresa empresa = empresaRepository.findById(empresaId)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Empresa", empresaId));

		if (fornecedor.temEmpresa(empresa) == false) {
			throw new RegraDeNegocioException(String.format("Fornecedor '%s' não está vinculado a Empresa %s",
					fornecedor.getNome(), empresa.getNomeFantasia()));
		}

		fornecedor.removeEmpresa(empresa);
		empresa.getFornecedores().remove(fornecedor);
		fornecedorRepository.save(fornecedor);
	}

	private boolean isMenorDeIdade(LocalDate dataNascimento) {
		if (dataNascimento == null) {
			throw new RegraDeNegocioException("Informar o a data de Nacimento.");
		}

		if (dataNascimento.isAfter(LocalDate.now())) {
			throw new RegraDeNegocioException("Data de nascimento não pode ser futura");
		}
		return Period.between(dataNascimento, LocalDate.now()).getYears() < 18;
	}

	private void validarRegraParana(Empresa empresa, Fornecedor fornecedor) {

		if (fornecedor.getTipoPessoa() == TipoPessoa.FISICA) {

			if (empresa.getEndereco() != null && "PR".equals(empresa.getEndereco().getUf())
					&& isMenorDeIdade(fornecedor.getDataNascimento())) {

				throw new RegraDeNegocioException(
						"Empresas do Paraná só podem se associar a fornecedores maiores de idade");
			}
		}
	}

}
