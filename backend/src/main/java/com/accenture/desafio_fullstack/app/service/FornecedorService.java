package com.accenture.desafio_fullstack.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.desafio_fullstack.app.model.Fornecedor;
import com.accenture.desafio_fullstack.app.repository.FornecedorRepository;

@Service
public class FornecedorService {

	final private FornecedorRepository fornecedorRepository;

	public FornecedorService(FornecedorRepository fornecedorRepository) {

		this.fornecedorRepository = fornecedorRepository;
	}

	public List<Fornecedor> listNome(String nome) {

		List<Fornecedor> fornecedores = null;
		if (nome != null) {

			fornecedores = fornecedorRepository.findByNomeContainingAllIgnoreCase(nome);

			if (fornecedores == null || fornecedores.isEmpty()) {

				throw new RuntimeException("incluir uma Exception melhor");

			}
		}

		return fornecedores;
	}

	public Fornecedor listCpfOrCnpj(String cpfOrCnpj) {

		if (cpfOrCnpj == null) {

			throw new RuntimeException("incluir uma Exception melhor");

		}

		return fornecedorRepository.findByCnpjOuCpf(cpfOrCnpj)
				.orElseThrow(() -> new RuntimeException("incluir uma Exception melhor"));

	}

	@Transactional
	public Fornecedor create(Fornecedor fornecedor) {

		if (fornecedor == null) {
			throw new RuntimeException("incluir uma Exception melhor");

		}

		return fornecedorRepository.save(fornecedor);
	}

}
